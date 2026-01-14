package com.blackwhitemap.blackwhitemap_back.infrastructure.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.ranking.ChefRanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChefRankingJpaRepository extends JpaRepository<ChefRanking, Long> {

    Optional<ChefRanking> findByTypeAndPeriodStartAndChefId(
            ChefRanking.Type type,
            LocalDate periodStart,
            Long chefId
    );

    List<ChefRanking> findAllByTypeAndPeriodStart(
            ChefRanking.Type type,
            LocalDate periodStart
    );

    void deleteByTypeAndPeriodStart(ChefRanking.Type type, LocalDate periodStart);

    @Query("SELECT cr FROM ChefRanking cr " +
           "WHERE cr.type = 'DAILY' " +
           "AND cr.periodStart >= :startDate " +
           "AND cr.periodStart <= :endDate")
    List<ChefRanking> findDailyRankingsByPeriodRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
