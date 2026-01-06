package com.blackwhitemap.blackwhitemap_back.domain.ranking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final ChefRankingRepository chefRankingRepository;

    /**
     * 일간 랭킹 저장 또는 갱신 (Upsert)
     */
    public ChefRanking saveOrUpdateDailyRanking(RankingCommand.CreateDailyRanking createCommand) {
        return chefRankingRepository
                .findByTypeAndPeriodStartAndChefId(
                        ChefRanking.Type.DAILY,
                        createCommand.periodStart(),
                        createCommand.chefId()
                )
                .map(existing -> {
                    RankingCommand.UpdateRanking updateCommand = new RankingCommand.UpdateRanking(
                            createCommand.rank(),
                            createCommand.score()
                    );
                    existing.updateRanking(updateCommand);
                    return chefRankingRepository.save(existing);
                })
                .orElseGet(() -> {
                    ChefRanking newRanking = ChefRanking.ofDaily(createCommand);
                    return chefRankingRepository.save(newRanking);
                });
    }

    /**
     * 특정 날짜의 일간 랭킹 전체 삭제
     */
    public void deleteDailyRankingByDate(LocalDate periodStart) {
        chefRankingRepository.deleteByTypeAndPeriodStart(
                ChefRanking.Type.DAILY,
                periodStart
        );
    }
}