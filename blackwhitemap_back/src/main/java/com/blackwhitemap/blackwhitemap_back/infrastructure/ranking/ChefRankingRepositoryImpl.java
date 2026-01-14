package com.blackwhitemap.blackwhitemap_back.infrastructure.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.ranking.ChefRanking;
import com.blackwhitemap.blackwhitemap_back.domain.ranking.ChefRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChefRankingRepositoryImpl implements ChefRankingRepository {

    private final ChefRankingJpaRepository chefRankingJpaRepository;

    @Override
    public ChefRanking save(ChefRanking chefRanking) {
        return chefRankingJpaRepository.save(chefRanking);
    }

    @Override
    public Optional<ChefRanking> findByTypeAndPeriodStartAndChefId(
            ChefRanking.Type type,
            LocalDate periodStart,
            Long chefId
    ) {
        return chefRankingJpaRepository.findByTypeAndPeriodStartAndChefId(type, periodStart, chefId);
    }

    @Override
    public List<ChefRanking> findAllByTypeAndPeriodStart(
            ChefRanking.Type type,
            LocalDate periodStart
    ) {
        return chefRankingJpaRepository.findAllByTypeAndPeriodStart(type, periodStart);
    }

    @Override
    public void deleteByTypeAndPeriodStart(ChefRanking.Type type, LocalDate periodStart) {
        chefRankingJpaRepository.deleteByTypeAndPeriodStart(type, periodStart);
    }

    @Override
    public List<ChefRanking> findDailyRankingsByPeriodRange(LocalDate startDate, LocalDate endDate) {
        return chefRankingJpaRepository.findDailyRankingsByPeriodRange(startDate, endDate);
    }
}
