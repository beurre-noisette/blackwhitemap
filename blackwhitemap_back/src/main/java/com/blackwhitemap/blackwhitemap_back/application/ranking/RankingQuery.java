package com.blackwhitemap.blackwhitemap_back.application.ranking;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RankingQuery {

    private static final int DAILY_FETCH_LIMIT = 30;
    private static final int DAILY_RESULT_LIMIT = 5;

    private final RankingQueryRepository rankingQueryRepository;

    /**
     * 주간 Best Chef 조회 (캐싱 적용)
     * - 현재 주차의 TOP N Chef 조회
     * - 캐시 TTL: 5분
     * - 캐시 키: 주차시작일_limit (e.g. "2025-12-23_5")
     *
     * @param limit 조회할 랭킹 개수
     * @return 주간 Best Chef 정보 리스트
     */
    @Cacheable(value = "weeklyBestChefs", key = "#root.target.getCacheKey(#limit)")
    public List<RankingResult.WeeklyBestChef> getWeeklyBestChefs(int limit) {
        LocalDate currentWeekStart = getCurrentWeekStart();
        return rankingQueryRepository.findWeeklyBestChefs(currentWeekStart, limit);
    }

    /**
     * 일일 Best Chef 조회 (캐싱 적용)
     * - 오늘 TOP 5 Chef 조회
     * - 같은 셰프(nickname 또는 name 기준)가 여러 매장을 운영하는 경우, 가장 높은 rank 1명만 포함
     * - 캐시 TTL: 5분
     * - 캐시 키: 오늘 날짜
     *
     * @return 일일 Best Chef 정보 리스트
     */
    @Cacheable(value = "dailyBestChefs", key = "#root.target.getDailyCacheKey()")
    public List<RankingResult.DailyBestChef> getDailyBestChefs() {
        return rankingQueryRepository.findDailyBestChefs(
                LocalDate.now(),
                DAILY_FETCH_LIMIT,
                DAILY_RESULT_LIMIT
        );
    }

    /**
     * 일일 캐시 키 생성
     *
     * @return 오늘 날짜 문자열
     */
    public String getDailyCacheKey() {
        return LocalDate.now().toString();
    }

    /**
     * 캐시 키 생성
     * - 주차 시작일과 limit을 조합하여 고유한 캐시 키 생성
     *
     * @param limit 조회할 랭킹 개수
     * @return 캐시 키 (주차시작일_limit)
     */
    public String getCacheKey(int limit) {
        return getCurrentWeekStart().toString() + "_" + limit;
    }

    /**
     * 현재 주차의 시작일 계산 (매주 화요일)
     * - 흑백요리사 에피소드가 매주 화요일에 공개되므로 화요일 기준
     *
     * @return 현재 주차 시작일 (화요일)
     */
    private LocalDate getCurrentWeekStart() {
        return LocalDate.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.TUESDAY));
    }
}
