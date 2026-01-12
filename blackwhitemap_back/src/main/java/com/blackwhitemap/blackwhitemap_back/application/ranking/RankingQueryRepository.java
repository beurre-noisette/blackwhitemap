package com.blackwhitemap.blackwhitemap_back.application.ranking;

import java.time.LocalDate;
import java.util.List;

/**
 * Ranking 도메인 조회 전용 Repository
 * - 구현체는 Infrastructure 계층에 위치 (RankingQueryRepositoryImpl)
 */
public interface RankingQueryRepository {

    /**
     * 주간 Best Chef 조회
     * - 특정 주차의 ChefRanking과 Chef를 JOIN하여 조회
     * - rank 순서로 정렬
     *
     * @param periodStart 주차 시작일
     * @param limit 조회할 랭킹 개수 (TOP 5, TOP 10 등)
     * @return 주간 Best Chef 정보 리스트
     */
    List<RankingResult.WeeklyBestChef> findWeeklyBestChefs(LocalDate periodStart, int limit);

    /**
     * 일일 Best Chef 조회
     * - 특정 날짜의 ChefRanking과 Chef를 JOIN하여 조회
     * - 같은 셰프(nickname 또는 name 기준)가 여러 매장을 운영하는 경우, 가장 높은 rank 1명만 포함
     * - rank 순서로 정렬
     *
     * @param periodStart 조회 날짜
     * @param fetchLimit  DB에서 조회할 개수 (중복 제거 전)
     * @param resultLimit 최종 반환할 개수
     * @return 일일 Best Chef 정보 리스트
     */
    List<RankingResult.DailyBestChef> findDailyBestChefs(
            LocalDate periodStart,
            int fetchLimit,
            int resultLimit
    );
}
