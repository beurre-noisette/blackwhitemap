package com.blackwhitemap.blackwhitemap_back.application.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.ranking.RankingCommand;
import com.blackwhitemap.blackwhitemap_back.domain.ranking.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RankingFacade {

    private final RankingService rankingService;

    /**
     * 일간 랭킹 일괄 갱신
     * - 기존 데이터가 있으면 갱신, 없으면 생성
     */
    @Transactional
    public void updateDailyRankings(RankingCriteria.UpdateDailyRanking updateCriteria) {
        for (RankingCriteria.RankingEntry entry : updateCriteria.rankings()) {
            RankingCommand.CreateDailyRanking createCommand = new RankingCommand.CreateDailyRanking(
                    entry.chefId(),
                    updateCriteria.periodStart(),
                    entry.rank(),
                    entry.score()
            );

            rankingService.saveOrUpdateDailyRanking(createCommand);
        }
    }
}