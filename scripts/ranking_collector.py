#!/usr/bin/env python3
"""
흑백요리사2 일간 랭킹 수집 스크립트

데이터 플로우:
1. Backend API에서 셰프 목록 조회 (name, nickname)
2. 네이버 뉴스 API로 "흑백요리사2" 검색 (1회 호출)
3. 기사에서 셰프 이름/닉네임이 포함된 기사만 필터링
4. 필터링된 기사를 Gemini API로 감정 분석 (배치 5개씩)
5. 긍정 기사만 점수화하여 Backend API로 저장 (순위는 백엔드에서 계산)
"""
import json
import logging
import os
import re
import sys
import time
from dataclasses import dataclass
from datetime import date, datetime, timezone, timedelta
from pathlib import Path

# 한국 표준시 (KST = UTC+9)
KST = timezone(timedelta(hours=9))

import requests
from dotenv import load_dotenv
from google import genai

env_path = Path(__file__).parent / '.env'
load_dotenv(dotenv_path=env_path)

# ============================================================================
# 로깅 설정
# ============================================================================
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(message)s",
    datefmt="%Y-%m-%d %H:%M:%S"
)
logger = logging.getLogger(__name__)


# ============================================================================
# 환경변수 로드
# ============================================================================
@dataclass
class Config:
    """환경변수 기반 설정"""
    # Backend API
    backend_url: str
    internal_api_key: str

    # 네이버 API
    naver_client_id: str
    naver_client_secret: str

    # Gemini API
    gemini_api_key: str

    # 배치 설정
    gemini_batch_size: int = 100  # Gemini API 호출 시 한 번에 분석할 기사 수 (TPM 여유로 100개 한번에 처리)
    news_display_count: int = 100  # 한 번에 검색할 뉴스 개수 (최대 100)

    # 토큰/Rate Limit 관련 설정 (Gemini 무료 티어: RPM 5, TPM 250K, RPD 20)
    estimated_tokens_per_article: int = 400  # 기사당 예상 토큰 (제목+내용 한글 기준)
    max_tokens_per_request: int = 200000  # TPM 250K의 80% (안전 마진)
    min_batch_interval_seconds: int = 15  # RPM 5 대응 (60/5=12초, 안전 마진 포함)

    @classmethod
    def from_env(cls) -> "Config":
        """환경변수에서 설정 로드"""
        required_vars = [
            "BACKEND_URL",
            "INTERNAL_API_KEY",
            "NAVER_CLIENT_ID",
            "NAVER_CLIENT_SECRET",
            "GEMINI_API_KEY"
        ]

        missing = [var for var in required_vars if not os.getenv(var)]
        if missing:
            raise EnvironmentError(f"필수 환경변수 누락: {', '.join(missing)}")

        return cls(
            backend_url=os.getenv("BACKEND_URL", "").rstrip("/"),
            internal_api_key=os.getenv("INTERNAL_API_KEY", ""),
            naver_client_id=os.getenv("NAVER_CLIENT_ID", ""),
            naver_client_secret=os.getenv("NAVER_CLIENT_SECRET", ""),
            gemini_api_key=os.getenv("GEMINI_API_KEY", ""),
            gemini_batch_size=int(os.getenv("GEMINI_BATCH_SIZE", "100")),
            news_display_count=int(os.getenv("NEWS_DISPLAY_COUNT", "100")),
            estimated_tokens_per_article=int(os.getenv("ESTIMATED_TOKENS_PER_ARTICLE", "400")),
            max_tokens_per_request=int(os.getenv("MAX_TOKENS_PER_REQUEST", "200000")),
            min_batch_interval_seconds=int(os.getenv("MIN_BATCH_INTERVAL", "15"))
        )

# ============================================================================
# 데이터 클래스
# ============================================================================
@dataclass
class Chef:
    """셰프 정보"""
    id: int
    name: str
    nickname: str

    def get_identity_key(self) -> str:
        """동일인물 식별 키 (이름+닉네임)"""
        return f"{self.name}|{self.nickname}"

    def matches_text(self, text: str) -> bool:
        """텍스트에 셰프 이름 또는 닉네임이 포함되어 있는지 확인"""
        if not text:
            return False
        # 이름 매칭 (None이 아닌 경우)
        if self.name and self.name in text:
            return True
        # 닉네임 매칭 (None이 아닌 경우)
        if self.nickname and self.nickname in text:
            return True
        return False


@dataclass
class ChefGroup:
    """
    동일인물 셰프 그룹
    - 같은 이름+닉네임을 가진 셰프들을 하나의 그룹으로 묶음
    - 예: 손종원(가게1), 손종원(가게2) → 하나의 그룹
    """
    name: str
    nickname: str
    chef_ids: list[int]  # 그룹에 속한 모든 chefId

    def matches_text(self, text: str) -> bool:
        """텍스트에 셰프 이름 또는 닉네임이 포함되어 있는지 확인"""
        if not text:
            return False
        if self.name and self.name in text:
            return True
        if self.nickname and self.nickname in text:
            return True
        return False

    @staticmethod
    def group_chefs(chefs: list[Chef]) -> list["ChefGroup"]:
        """셰프 목록을 동일인물 그룹으로 묶음"""
        groups: dict[str, ChefGroup] = {}

        for chef in chefs:
            key = chef.get_identity_key()
            if key in groups:
                groups[key].chef_ids.append(chef.id)
            else:
                groups[key] = ChefGroup(
                    name=chef.name,
                    nickname=chef.nickname,
                    chef_ids=[chef.id]
                )

        return list(groups.values())


@dataclass
class NewsArticle:
    """뉴스 기사"""
    title: str
    description: str
    link: str
    pub_date: str

    def get_full_text(self) -> str:
        """제목 + 내용 결합"""
        return f"{self.title} {self.description}"


# ============================================================================
# Backend API 클라이언트
# ============================================================================
class BackendClient:
    """Backend API 클라이언트"""

    def __init__(self, config: Config):
        self.base_url = config.backend_url
        self.headers = {
            "X-Internal-Api-Key": config.internal_api_key,
            "Content-Type": "application/json"
        }

    def get_all_chefs(self) -> list[Chef]:
        """모든 셰프 목록 조회"""
        url = f"{self.base_url}/internal/chefs"

        try:
            response = requests.get(url, headers=self.headers, timeout=30)
            response.raise_for_status()

            data = response.json()
            if data.get("meta", {}).get("result") != "SUCCESS":
                raise Exception(f"API 응답 실패: {data}")

            chefs = []
            for chef_data in data.get("data", []):
                name = chef_data.get("name")
                nickname = chef_data.get("nickname")

                # name이 None인 셰프는 제외
                if not name:
                    logger.warning(f"셰프 ID {chef_data.get('id')}의 이름이 없어 제외합니다.")
                    continue

                chefs.append(Chef(
                    id=chef_data["id"],
                    name=name,
                    nickname=nickname or ""  # nickname이 None이면 빈 문자열
                ))

            logger.info(f"셰프 목록 조회 완료: {len(chefs)}명")
            return chefs

        except requests.RequestException as e:
            logger.error(f"셰프 목록 조회 실패: {e}")
            raise

    def save_daily_ranking(self, period_start: date, rankings: list[dict]) -> None:
        """일간 랭킹 저장"""
        url = f"{self.base_url}/internal/daily-ranking"

        payload = {
            "periodStart": period_start.isoformat(),
            "rankings": rankings
        }

        try:
            response = requests.post(
                url,
                headers=self.headers,
                json=payload,
                timeout=30
            )
            response.raise_for_status()

            data = response.json()
            if data.get("meta", {}).get("result") != "SUCCESS":
                raise Exception(f"API 응답 실패: {data}")

            logger.info(f"일간 랭킹 저장 완료: {len(rankings)}건")

        except requests.RequestException as e:
            logger.error(f"일간 랭킹 저장 실패: {e}")
            raise


# ============================================================================
# 네이버 뉴스 API 클라이언트
# ============================================================================
class NaverNewsClient:
    """네이버 뉴스 검색 API 클라이언트"""

    SEARCH_URL = "https://openapi.naver.com/v1/search/news.json"
    SEARCH_QUERY = "흑백요리사2"

    def __init__(self, config: Config):
        self.headers = {
            "X-Naver-Client-Id": config.naver_client_id,
            "X-Naver-Client-Secret": config.naver_client_secret
        }
        self.display_count = min(config.news_display_count, 100)  # 최대 100

    def search_news(self) -> list[NewsArticle]:
        """
        흑백요리사2 관련 뉴스 검색 (1회 호출)
        """
        params = {
            "query": self.SEARCH_QUERY,
            "display": self.display_count,
            "sort": "date"  # 최신순
        }

        try:
            response = requests.get(
                self.SEARCH_URL,
                headers=self.headers,
                params=params,
                timeout=10
            )
            response.raise_for_status()

            data = response.json()
            articles = [
                NewsArticle(
                    title=self._clean_html(item["title"]),
                    description=self._clean_html(item["description"]),
                    link=item["link"],
                    pub_date=item["pubDate"]
                )
                for item in data.get("items", [])
            ]

            logger.info(f"뉴스 검색 완료: {len(articles)}건")
            return articles

        except requests.RequestException as e:
            logger.error(f"뉴스 검색 실패: {e}")
            return []

    @staticmethod
    def _clean_html(text: str) -> str:
        """HTML 태그 제거"""
        clean = re.sub(r"<[^>]+>", "", text)
        clean = clean.replace("&quot;", '"')
        clean = clean.replace("&amp;", "&")
        clean = clean.replace("&lt;", "<")
        clean = clean.replace("&gt;", ">")
        return clean.strip()


# ============================================================================
# Gemini API 감정 분석기
# ============================================================================
class GeminiSentimentAnalyzer:
    """Gemini API를 사용한 감정 분석"""

    # 감정 분석 프롬프트
    ANALYSIS_PROMPT = """당신은 뉴스 기사의 감정을 분석하는 전문가입니다.

다음 뉴스 기사들을 분석하여 각 기사가 해당 셰프에 대해 긍정적인지, 부정적인지, 중립적인지 판단해주세요.

분석 기준:
- 긍정(POSITIVE): 셰프의 성과, 칭찬, 좋은 소식을 담은 기사
- 부정(NEGATIVE): 셰프에 대한 비판, 논란, 부정적 내용을 담은 기사
- 중립(NEUTRAL): 단순 정보 전달, 감정적 판단이 어려운 기사

응답은 반드시 아래 JSON 형식으로만 출력하세요. 다른 설명은 포함하지 마세요.

{
  "results": [
    {"index": 0, "sentiment": "POSITIVE"},
    {"index": 1, "sentiment": "NEGATIVE"},
    ...
  ]
}

분석할 기사들:
"""

    # 재시도 설정
    MAX_RETRIES = 3
    INITIAL_DELAY = 60  # 초 (30 → 60으로 증가, 더 보수적 접근)

    # 토큰 추정 상수
    BASE_PROMPT_TOKENS = 300  # 프롬프트 템플릿 토큰
    PER_ARTICLE_OVERHEAD = 50  # [기사 N] 헤더 등 오버헤드
    RESPONSE_TOKENS_PER_ARTICLE = 20  # 응답 토큰 (기사당)

    def __init__(self, config: Config):
        self.client = genai.Client(api_key=config.gemini_api_key)
        self.model_name = "gemini-2.5-flash"
        self.batch_size = config.gemini_batch_size
        self.config = config  # 설정 객체 저장 (토큰 한도 등 접근용)

    def _estimate_article_tokens(self, article: NewsArticle) -> int:
        """단일 기사의 예상 토큰 수 계산"""
        text_length = len(article.title) + len(article.description)
        # 한글 기준 약 0.6 토큰/글자 + 오버헤드
        return int(text_length * 0.6) + self.PER_ARTICLE_OVERHEAD

    def estimate_tokens(self, articles: list[tuple[str, NewsArticle]]) -> int:
        """기사 목록의 총 예상 토큰 수 계산"""
        if not articles:
            return 0

        total = self.BASE_PROMPT_TOKENS

        for _, article in articles:
            total += self._estimate_article_tokens(article)

        # 응답 토큰 예상
        total += len(articles) * self.RESPONSE_TOKENS_PER_ARTICLE

        return total

    def split_by_token_limit(
        self,
        articles: list[tuple[str, NewsArticle]],
        max_tokens: int
    ) -> list[list[tuple[str, NewsArticle]]]:
        """토큰 한도에 맞게 기사 목록을 분할 (한도 초과 시에만)"""
        if not articles:
            return []

        batches = []
        current_batch = []
        current_tokens = self.BASE_PROMPT_TOKENS

        for item in articles:
            article_tokens = self._estimate_article_tokens(item[1])

            # 현재 배치에 추가하면 한도 초과 시 새 배치 시작
            if current_tokens + article_tokens > max_tokens and current_batch:
                batches.append(current_batch)
                current_batch = []
                current_tokens = self.BASE_PROMPT_TOKENS

            current_batch.append(item)
            current_tokens += article_tokens

        if current_batch:
            batches.append(current_batch)

        return batches

    def analyze_batch(
        self,
        articles: list[tuple[int, NewsArticle]]  # (chef_id, article) 튜플 리스트
    ) -> dict[int, int]:
        """
        배치 단위로 감정 분석 수행 (재시도 로직 포함)

        Returns:
            dict[int, int]: {chef_id: positive_count} 매핑
        """
        if not articles:
            return {}

        # 프롬프트 생성
        prompt = self.ANALYSIS_PROMPT
        for idx, (chef_id, article) in enumerate(articles):
            prompt += f"\n[기사 {idx}]\n"
            prompt += f"제목: {article.title}\n"
            prompt += f"내용: {article.description}\n"

        # 재시도 로직
        for attempt in range(self.MAX_RETRIES):
            try:
                response = self.client.models.generate_content(
                    model=self.model_name,
                    contents=prompt
                )
                result_text = response.text.strip()

                # JSON 파싱 (코드 블록 제거)
                if result_text.startswith("```"):
                    result_text = result_text.split("```")[1]
                    if result_text.startswith("json"):
                        result_text = result_text[4:]

                result_json = json.loads(result_text)

                # 셰프별 긍정 기사 수 집계
                positive_counts: dict[int, int] = {}
                for item in result_json.get("results", []):
                    idx = item.get("index", -1)
                    sentiment = item.get("sentiment", "NEUTRAL")

                    if 0 <= idx < len(articles):
                        chef_id = articles[idx][0]
                        if sentiment == "POSITIVE":
                            positive_counts[chef_id] = positive_counts.get(chef_id, 0) + 1

                return positive_counts

            except json.JSONDecodeError as e:
                logger.warning(f"Gemini 응답 파싱 실패: {e}")
                return {}

            except Exception as e:
                error_str = str(e)
                # Rate limit 오류인 경우 재시도
                if "429" in error_str or "RESOURCE_EXHAUSTED" in error_str:
                    delay = self.INITIAL_DELAY * (2 ** attempt)  # Exponential backoff
                    logger.warning(f"Gemini API Rate Limit. {delay}초 후 재시도... ({attempt + 1}/{self.MAX_RETRIES})")
                    time.sleep(delay)
                else:
                    logger.warning(f"Gemini API 호출 실패: {e}")
                    return {}

        logger.error("Gemini API 최대 재시도 횟수 초과")
        return {}


# ============================================================================
# 랭킹 수집기
# ============================================================================
class RankingCollector:
    """일간 랭킹 수집 메인 클래스"""

    def __init__(self, config: Config):
        self.config = config
        self.backend = BackendClient(config)
        self.news_client = NaverNewsClient(config)
        self.analyzer = GeminiSentimentAnalyzer(config)

    def collect(self) -> None:
        """랭킹 수집 실행"""
        logger.info("=== 일간 랭킹 수집 시작 ===")

        # 1. 셰프 목록 조회
        chefs = self.backend.get_all_chefs()
        if not chefs:
            logger.warning("셰프 목록이 비어있습니다.")
            return

        # 2. 동일인물 그룹화 (이름+닉네임 기준)
        chef_groups = ChefGroup.group_chefs(chefs)
        logger.info(f"동일인물 그룹화 완료: {len(chefs)}명 → {len(chef_groups)}그룹")

        # 3. 흑백요리사2 뉴스 검색 (1회 호출)
        logger.info("뉴스 수집 시작...")
        all_news = self.news_client.search_news()
        if not all_news:
            logger.warning("수집된 뉴스가 없습니다.")
            self._save_empty_rankings(chefs)
            return

        # 4. 그룹별 기사 필터링 (동일인물은 하나의 그룹으로 처리)
        logger.info("그룹별 기사 필터링 중...")
        group_articles: dict[str, list[NewsArticle]] = {}

        for group in chef_groups:
            group_key = f"{group.name}|{group.nickname}"
            group_articles[group_key] = []

        for article in all_news:
            full_text = article.get_full_text()
            for group in chef_groups:
                if group.matches_text(full_text):
                    group_key = f"{group.name}|{group.nickname}"
                    # 동일 기사 중복 추가 방지
                    if article not in group_articles[group_key]:
                        group_articles[group_key].append(article)

        # 필터링 결과 로깅
        matched_count = sum(len(articles) for articles in group_articles.values())
        matched_groups = sum(1 for articles in group_articles.values() if articles)
        logger.info(f"필터링 완료: {matched_groups}개 그룹에 대해 {matched_count}건의 기사 매칭")

        # 5. 감정 분석용 데이터 준비 (그룹 키 기준)
        all_articles_for_analysis: list[tuple[str, NewsArticle]] = []  # (group_key, article)
        for group_key, articles in group_articles.items():
            for article in articles:
                all_articles_for_analysis.append((group_key, article))

        if not all_articles_for_analysis:
            logger.info("분석할 기사가 없습니다. 모든 셰프 점수를 0으로 설정합니다.")
            self._save_empty_rankings(chefs)
            return

        # 6. 토큰 추정 및 배치 결정
        estimated_tokens = self.analyzer.estimate_tokens(all_articles_for_analysis)
        max_tokens = self.config.max_tokens_per_request
        logger.info(f"예상 토큰 수: {estimated_tokens:,} (한도: {max_tokens:,})")

        # 7. 토큰 한도에 따라 배치 분할 여부 결정
        if estimated_tokens <= max_tokens:
            # 단일 배치로 처리 (최적 케이스 - 대부분 여기로 진입)
            batches = [all_articles_for_analysis]
            logger.info("단일 API 호출로 처리합니다.")
        else:
            # 토큰 한도 초과 시에만 분할
            batches = self.analyzer.split_by_token_limit(all_articles_for_analysis, max_tokens)
            logger.info(f"토큰 한도 초과로 {len(batches)}개 배치로 분할합니다.")

        # 8. 배치 처리 (대부분 1회)
        logger.info("감정 분석 시작...")
        group_positive_counts: dict[str, int] = {}  # {group_key: positive_count}
        total_batches = len(batches)

        for i, batch in enumerate(batches):
            # RPM 제한 대응: 첫 번째 배치가 아닌 경우 대기
            if i > 0:
                wait_time = self.config.min_batch_interval_seconds
                logger.info(f"RPM 제한 대응: {wait_time}초 대기...")
                time.sleep(wait_time)

            batch_results = self._analyze_batch_by_group(batch)

            for group_key, count in batch_results.items():
                group_positive_counts[group_key] = (
                    group_positive_counts.get(group_key, 0) + count
                )

            logger.info(f"  배치 {i + 1}/{total_batches} 완료 ({len(batch)}건 분석)")

        logger.info("감정 분석 완료")

        # API 사용량 요약 로깅
        logger.info("=== API 사용량 요약 ===")
        logger.info(f"Gemini API 호출 횟수: {total_batches}회 (일일 한도: 20회)")
        logger.info(f"처리된 기사 수: {len(all_articles_for_analysis)}건")
        logger.info(f"예상 사용 토큰: {estimated_tokens:,} (분당 한도: 250,000)")
        remaining_calls = max(0, 20 - total_batches)
        logger.info(f"남은 일일 호출 가능 횟수: 약 {remaining_calls}회 (이번 실행 기준)")

        # 9. 점수 계산 및 랭킹 생성 (그룹 → 개별 chefId로 확장)
        self._save_rankings_by_group(chefs, chef_groups, group_positive_counts)

    def _analyze_batch_by_group(
        self,
        articles: list[tuple[str, NewsArticle]]  # (group_key, article)
    ) -> dict[str, int]:
        """그룹 키 기준으로 감정 분석"""
        if not articles:
            return {}

        # 프롬프트 생성
        prompt = self.analyzer.ANALYSIS_PROMPT
        for idx, (group_key, article) in enumerate(articles):
            prompt += f"\n[기사 {idx}]\n"
            prompt += f"제목: {article.title}\n"
            prompt += f"내용: {article.description}\n"

        # 재시도 로직
        for attempt in range(self.analyzer.MAX_RETRIES):
            try:
                response = self.analyzer.client.models.generate_content(
                    model=self.analyzer.model_name,
                    contents=prompt
                )
                result_text = response.text.strip()

                # JSON 파싱 (코드 블록 제거)
                if result_text.startswith("```"):
                    result_text = result_text.split("```")[1]
                    if result_text.startswith("json"):
                        result_text = result_text[4:]

                result_json = json.loads(result_text)

                # 그룹별 긍정 기사 수 집계
                positive_counts: dict[str, int] = {}
                for item in result_json.get("results", []):
                    idx = item.get("index", -1)
                    sentiment = item.get("sentiment", "NEUTRAL")

                    if 0 <= idx < len(articles):
                        group_key = articles[idx][0]
                        if sentiment == "POSITIVE":
                            positive_counts[group_key] = positive_counts.get(group_key, 0) + 1

                return positive_counts

            except json.JSONDecodeError as e:
                logger.warning(f"Gemini 응답 파싱 실패: {e}")
                return {}

            except Exception as e:
                error_str = str(e)
                if "429" in error_str or "RESOURCE_EXHAUSTED" in error_str:
                    delay = self.analyzer.INITIAL_DELAY * (2 ** attempt)
                    logger.warning(f"Gemini API Rate Limit. {delay}초 후 재시도... ({attempt + 1}/{self.analyzer.MAX_RETRIES})")
                    time.sleep(delay)
                else:
                    logger.warning(f"Gemini API 호출 실패: {e}")
                    return {}

        logger.error("Gemini API 최대 재시도 횟수 초과")
        return {}

    def _save_empty_rankings(self, chefs: list[Chef]) -> None:
        """모든 셰프 점수를 0으로 저장 (순위는 백엔드에서 계산)"""
        rankings = [
            {"chefId": chef.id, "score": 0}
            for chef in chefs
        ]
        today_kst = datetime.now(KST).date()
        self.backend.save_daily_ranking(today_kst, rankings)
        logger.info("빈 랭킹 저장 완료 (모든 셰프 0점)")

    def _save_rankings_by_group(
        self,
        chefs: list[Chef],
        chef_groups: list[ChefGroup],
        group_positive_counts: dict[str, int]
    ) -> None:
        """
        그룹 단위 점수를 개별 chefId로 확장하여 저장
        - 동일인물(같은 그룹)은 같은 점수를 갖음
        - 순위는 백엔드에서 계산
        """
        # 1. 그룹별 점수 계산
        group_scores: list[tuple[ChefGroup, int]] = []
        for group in chef_groups:
            group_key = f"{group.name}|{group.nickname}"
            score = group_positive_counts.get(group_key, 0)
            group_scores.append((group, score))

        # 점수 기준 내림차순 정렬 (로깅용)
        group_scores.sort(key=lambda x: x[1], reverse=True)

        # 2. 그룹 → 개별 chefId로 확장 (점수만 전달, 순위는 백엔드에서 계산)
        rankings = []
        for group, score in group_scores:
            for chef_id in group.chef_ids:
                rankings.append({
                    "chefId": chef_id,
                    "score": score
                })

        logger.info(f"점수 데이터 생성 완료: {len(chef_groups)}그룹 → {len(rankings)}건")

        # Backend API로 저장 (순위는 백엔드에서 계산)
        today_kst = datetime.now(KST).date()
        self.backend.save_daily_ranking(today_kst, rankings)

        # 결과 요약 출력
        logger.info("=== 수집 결과 요약 ===")
        logger.info(f"수집 일자: {today_kst.isoformat()}")
        logger.info(f"전체 셰프: {len(chefs)}명")
        logger.info(f"그룹 수: {len(chef_groups)}개 (동일인물 그룹화)")

        # 상위 5그룹 출력 (점수 기준)
        logger.info("상위 5명 (점수 기준):")
        shown_count = 0
        for group, score in group_scores:
            if shown_count >= 5:
                break
            chef_count = len(group.chef_ids)
            suffix = f" (가게 {chef_count}개)" if chef_count > 1 else ""
            logger.info(f"  {group.name} ({score}점){suffix}")
            shown_count += 1

        logger.info("=== 일간 랭킹 수집 완료 ===")


# ============================================================================
# 메인 실행
# ============================================================================
def main():
    """메인 함수"""
    try:
        # 환경변수 로드
        config = Config.from_env()

        # 수집 실행
        collector = RankingCollector(config)
        collector.collect()

        sys.exit(0)

    except EnvironmentError as e:
        logger.error(f"환경변수 오류: {e}")
        sys.exit(1)
    except Exception as e:
        logger.exception(f"수집 중 오류 발생: {e}")
        sys.exit(1)


if __name__ == "__main__":
    main()
