package com.blackwhitemap.blackwhitemap_back.interfaces.ranking;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class RankingRequest {

    public record AggregateWeekly(
            @Min(value = 1, message = "topN은 1 이상이어야 합니다.")
            @Max(value = 100, message = "topN은 100 이하여야 합니다.")
            int topN
    ) {}
}
