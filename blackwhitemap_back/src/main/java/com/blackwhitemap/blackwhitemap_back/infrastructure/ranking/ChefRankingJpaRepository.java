package com.blackwhitemap.blackwhitemap_back.infrastructure.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.ranking.ChefRanking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ChefRankingJpaRepository extends JpaRepository<ChefRanking, Long> {

    Optional<ChefRanking> findByTypeAndPeriodStartAndChefId(
            ChefRanking.Type type,
            LocalDate periodStart,
            Long chefId
    );

    void deleteByTypeAndPeriodStart(ChefRanking.Type type, LocalDate periodStart);
}
