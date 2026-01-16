# 흑백여지도 

> 흑백요리사2 출연 셰프들의 업장을 지도에 모아보고, 일일/주간 랭킹을 통해 인기 셰프를 알아보는 서비스입니다. 

+ **운영 중** [heukbaek.site](https://heukbaek.site) 
+ **개발 기간**: 2025.12 ~ 

---

## 기술 스택

| 분류 | 기술                                                                            |
|------|-------------------------------------------------------------------------------|
| **백엔드** | Java 21, Spring Boot 3.4, Caffeine, Spring Data JPA, QueryDSL, Testcontainers |
| **프론트엔드** | React 19, TypeScript 5.9, Vite 7.2, Tailwind CSS                              |
| **인프라** | Vercel, Render, Supabase, GitHub Actions                                      |

---

## 프로젝트 구조

![Configuration diagram](docs/images/Configuration_diagram.png)

```
blackwhitemap/
├── blackwhitemap_back/           # Spring Boot 백엔드
│   ├── interfaces/               # API 계층
│   ├── application/              # Application 계층
│   ├── domain/                   # Domain 계층
│   ├── infrastructure/           # Infrastructure 계층
│   └── config/                   # 설정 (Cache, JPA 등)
├── blackwhitemap_front/          # React 프론트엔드
│   └── src/
│       ├── components/           # 재사용 컴포넌트
│       ├── api/                  # API 클라이언트
│       └── types/                # TypeScript 타입
├── scripts/                      # Python 랭킹 수집 자동화
└── .github/workflows/            # CI/CD (일일/주간 랭킹 집계)
```

---

## 주요 도전과 해결 과정

### 1. Gemini API 429 에러 대응 - Rate Limit 최적화

**배경**: 네이버 뉴스 기사를 Gemini API로 감정 분석하여 일일 랭킹 생성 (하루 4회 자동 실행)

**문제**: Gemini 무료 티어 제약(RPM 5, RPD 20, TPM 250K)으로 **성공률 30-40%, 데이터 손실 60-70%**

**해결**: 요청 횟수 최소화, 토큰 수 최대화 전략으로 전환

```python
# scripts/ranking_collector.py:60-67
gemini_batch_size: int = 100              # 5개 → 100개로 증가
estimated_tokens_per_article: int = 400  # 기사당 토큰 수 측정
max_tokens_per_request: int = 200000     # TPM 250K의 80% (안전 마진)
min_batch_interval_seconds: int = 15     # RPM 5 대응 (60/5=12초 + 안전 마진)
```

**결과**: 처리 시간 **80% 단축**(7~10분 → 1~2분), 성공률 **100%**, 데이터 손실 **0%**

### 2. 캐싱 전략과 성능 최적화 - 제약 환경에서의 효율화

**배경**: 조회 위주 서비스(사용자 CUD 없음)를 무료 인프라로 운영

**제약 조건**:
- Render 프리티어: 512MB RAM, 0.1 CPU (싱가폴 리전)
- [Supabase 프리티어](https://supabase.com/docs/guides/platform/compute-and-disk): Direct 60 / Pooler 200 connections, 500MB DB (서울 리전)
- 리전 간 네트워크 레이턴시 (Render 싱가폴 ↔ Supabase 서울)

**문제**: 캐싱이 없는 초기 구현에서 API 평균 응답 시간 **400-500ms**, DB 커넥션 부담

**해결**: Redis 대신 Caffeine 로컬 캐시 선택

**의사결정 근거**:
1. Redis = 새 인프라 추가 = 비용 발생 (비용 최소화 목표에 부합하지 않음)
2. 단일 서버 환경으로 세션 공유 불필요
3. 데이터 특성 분석
    - 셰프 데이터: 108명 고정, 가게 정보 변경 거의 없음 → TTL 30분
    - 랭킹 데이터: 일 4회, 주 1회만 갱신 → TTL 10분
    - 전체 데이터 크기 작아 512MB RAM에 충분히 적재 가능
4. 로컬 캐시로 Render ↔ Supabase 간 네트워크 통신 최소화

```java
  // CacheConfig.java:38-63
  cacheManager.setCaches(List.of(
        buildCache("chefs", 30, TimeUnit.MINUTES, 10),           // 변경 빈도 낮음
        buildCache("chefClusters", 30, TimeUnit.MINUTES, 5),
        buildCache("dailyBestChefs", 10, TimeUnit.MINUTES, 5),   // 하루 4회 갱신
        buildCache("weeklyBestChefs", 10, TimeUnit.MINUTES, 10)  // 주 1회 갱신
  ));
```

**결과**:
  - API 응답 시간: 400-500ms → 100-200ms (약 60% 개선)
  - 캐시 히트율 68.75% → DB 쿼리 68% 감소 → Supabase 프리티어 커넥션 부담 완화
  - 메모리 사용 41%(212MB/512MB), CPU 5.88% → 안정적 운영

### 3. 제한된 인프라 활용 전략 - 비용 최소화

**전략**:
- Vercel (프론트): GitHub 연동 자동 배포
- Render (백엔드): 512MB RAM/0.1 CPU 프리티어
- Supabase (DB): PostgreSQL 프리티어
- GitHub Actions: 일일 랭킹 수집(1일 4회), 주간 랭킹 집계

**결과**: 월 비용 **거의 0원**, 안정적 운영 (API 평균 응답 시간 **200ms**)