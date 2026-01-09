package com.blackwhitemap.blackwhitemap_back.domain.ranking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChefRankingRepository {

    ChefRanking save(ChefRanking chefRanking);

    /**
     * 특정 기간/타입/셰프의 랭킹 조회
     */
    Optional<ChefRanking> findByTypeAndPeriodStartAndChefId(
            ChefRanking.Type type,
            LocalDate periodStart,
            Long chefId
    );

    /**
     * 특정 기간/타입의 모든 랭킹 조회
     * - 순위 재계산 시 사용
     */
    List<ChefRanking> findAllByTypeAndPeriodStart(
            ChefRanking.Type type,
            LocalDate periodStart
    );

    /**
     * 특정 기간/타입의 모든 랭킹 삭제
     * - 새로운 랭킹 데이터로 완전 교체할 때 사용
     */
    void deleteByTypeAndPeriodStart(ChefRanking.Type type, LocalDate periodStart);
}