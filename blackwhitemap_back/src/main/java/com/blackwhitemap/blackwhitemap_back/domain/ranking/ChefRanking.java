package com.blackwhitemap.blackwhitemap_back.domain.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(
        name = "chef_ranking",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_ranking_period_chef",
                        columnNames = {"ranking_type", "period_start", "chef_id"}
                ),
                @UniqueConstraint(
                        name = "uk_ranking_period_rank",
                        columnNames = {"ranking_type", "period_start", "rank_position"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_ranking_period",
                        columnList = "ranking_type, period_start, rank_position"
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChefRanking extends BaseEntity {

    @Column(name = "chef_id", nullable = false)
    private Long chefId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ranking_type", nullable = false, length = 10)
    private Type type;

    @Getter
    @RequiredArgsConstructor
    public enum Type {
        WEEKLY("주간");

        private final String description;
    }

    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;

    @Column(name = "rank_position", nullable = false)
    private Integer rank;

    @Column(name = "score")
    private Long score;
}
