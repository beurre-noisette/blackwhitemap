package com.blackwhitemap.blackwhitemap_back.domain.ranking;

import java.time.LocalDate;

public class RankingCommand {

    public record CreateDailyRanking(
            Long chefId,
            LocalDate periodStart,
            Integer rank,
            Long score
    ) {}

    public record UpdateRanking(
            Integer rank,
            Long score
    ) {}
}