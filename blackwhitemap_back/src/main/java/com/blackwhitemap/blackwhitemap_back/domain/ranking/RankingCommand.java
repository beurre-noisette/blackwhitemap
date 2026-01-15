package com.blackwhitemap.blackwhitemap_back.domain.ranking;

import java.time.LocalDate;

public class RankingCommand {

    public record AddDailyScore(
            Long chefId,
            LocalDate periodStart,
            Long score
    ) {}

    public record UpdateRank(
            Integer rank
    ) {}

    public record CreateWeeklyRanking(
            Long chefId,
            LocalDate periodStart,
            Long score,
            Integer rank
    ) {}

    public record AggregateWeeklyRanking(
            LocalDate weekStart,
            int topN
    ) {}
}