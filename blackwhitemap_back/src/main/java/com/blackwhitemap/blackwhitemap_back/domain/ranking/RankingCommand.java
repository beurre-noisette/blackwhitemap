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
}