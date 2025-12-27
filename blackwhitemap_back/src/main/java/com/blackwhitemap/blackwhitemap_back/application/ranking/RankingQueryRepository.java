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
}
