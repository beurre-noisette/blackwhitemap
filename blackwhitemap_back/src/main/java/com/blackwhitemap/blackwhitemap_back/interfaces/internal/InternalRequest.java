package com.blackwhitemap.blackwhitemap_back.interfaces.internal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.List;

public class InternalRequest {

    public record UpdateDailyRanking(
            @NotNull(message = "랭킹 집계 날짜는 필수입니다")
            LocalDate periodStart,

            @NotEmpty(message = "랭킹 데이터는 최소 1개 이상이어야 합니다")
            @Valid
            List<RankingEntry> rankings
    ) {}

    public record RankingEntry(
            @NotNull(message = "셰프 ID는 필수입니다")
            @Positive(message = "셰프 ID는 양수여야 합니다")
            Long chefId,

            @NotNull(message = "순위는 필수입니다")
            @Positive(message = "순위는 양수여야 합니다")
            Integer rank,

            @NotNull(message = "점수는 필수입니다")
            @PositiveOrZero(message = "점수는 0 이상이어야 합니다")
            Long score
    ) {}
}