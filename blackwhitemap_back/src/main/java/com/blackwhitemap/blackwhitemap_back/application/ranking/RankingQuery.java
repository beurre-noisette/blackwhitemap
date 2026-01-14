package com.blackwhitemap.blackwhitemap_back.application.ranking;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RankingQuery {

    private static final int DAILY_RESULT_LIMIT = 5;

    private final RankingQueryRepository rankingQueryRepository;

    /**
     * 주간 Best Chef 조회 (캐싱 적용)
     * - 현재 주차의 TOP N Chef 조회
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
     * - 최근 3일(오늘/어제/그저께) 합산 점수 기준 TOP 5 Chef 조회
     * - 같은 셰프(nickname 또는 name 기준)가 여러 매장을 운영하는 경우, 가장 높은 rank 1명만 포함
     *
     * @return 일일 Best Chef 정보 리스트
     */
    @Cacheable(value = "dailyBestChefs", key = "#root.target.getDailyCacheKey()")
    public List<RankingResult.DailyBestChef> getDailyBestChefs() {
        // 최근 3일 계산 (오늘, 어제, 그저께)
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate yesterday = today.minusDays(1);
        LocalDate dayBeforeYesterday = today.minusDays(2);
        List<LocalDate> dateRange = List.of(
                today,
                yesterday,
                dayBeforeYesterday
        );

        return rankingQueryRepository.findDailyBestChefs(
                dateRange,
                DAILY_RESULT_LIMIT
        );
    }

    /**
     * 일일 캐시 키 생성
     * - 한국 시간대(Asia/Seoul) 기준으로 날짜 계산
     *
     * @return 오늘 날짜 문자열
     */
    public String getDailyCacheKey() {
        return LocalDate.now(ZoneId.of("Asia/Seoul")).toString();
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
     * - 한국 시간대(Asia/Seoul) 기준으로 계산
     *
     * @return 현재 주차 시작일 (화요일)
     */
    private LocalDate getCurrentWeekStart() {
        return LocalDate.now(ZoneId.of("Asia/Seoul"))
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.TUESDAY));
    }
}
