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

    private final RankingQueryRepository rankingQueryRepository;

    /**
     * 주간 Best Chef 조회 (캐싱 적용)
     * - 현재 주차의 TOP N Chef 조회
     * - 캐시 TTL: 1시간
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
