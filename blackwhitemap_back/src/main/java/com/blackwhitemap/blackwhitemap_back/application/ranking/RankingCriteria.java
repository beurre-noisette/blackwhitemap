package com.blackwhitemap.blackwhitemap_back.application.ranking;

import java.time.LocalDate;
import java.util.List;

public class RankingCriteria {

    public record RankingEntry(
            Long chefId,
            Integer rank,
            Long score
    ) {}

    public record UpdateDailyRanking(
            LocalDate periodStart,
            List<RankingEntry> rankings
    ) {}
}
