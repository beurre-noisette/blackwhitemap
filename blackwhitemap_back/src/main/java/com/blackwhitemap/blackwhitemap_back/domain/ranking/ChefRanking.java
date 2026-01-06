package com.blackwhitemap.blackwhitemap_back.domain.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.BaseEntity;
import com.blackwhitemap.blackwhitemap_back.support.error.CoreException;
import com.blackwhitemap.blackwhitemap_back.support.error.ErrorType;
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
        WEEKLY("주간"),
        DAILY("일간");

        private final String description;
    }

    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;

    @Column(name = "rank_position", nullable = false)
    private Integer rank;

    @Column(name = "score")
    private Long score;

    private ChefRanking(
            Long chefId,
            Type type,
            LocalDate periodStart,
            Integer rank,
            Long score
    ) {
        this.chefId = chefId;
        this.type = type;
        this.periodStart = periodStart;
        this.rank = rank;
        this.score = score;
    }

    public static ChefRanking ofDaily(RankingCommand.CreateDailyRanking command) {
        validateCreateCommand(command);
        
        return new ChefRanking(
                command.chefId(),
                Type.DAILY,
                command.periodStart(),
                command.rank(),
                command.score()
        );
    }

    /**
     * 랭킹 정보 갱신
     */
    public void updateRanking(RankingCommand.UpdateRanking command) {
        validateUpdateCommand(command);
        
        this.rank = command.rank();
        this.score = command.score();
    }

    private static void validateCreateCommand(RankingCommand.CreateDailyRanking command) {
        if (command.chefId() == null || command.chefId() <= 0) {
            throw new CoreException(ErrorType.BAD_REQUEST, "셰프 ID는 양수여야 합니다.");
        }
        if (command.periodStart() == null) {
            throw new CoreException(ErrorType.BAD_REQUEST, "랭킹 집계 날짜는 필수입니다.");
        }
        if (command.rank() == null || command.rank() <= 0) {
            throw new CoreException(ErrorType.BAD_REQUEST, "순위는 양수여야 합니다.");
        }
        if (command.score() == null || command.score() < 0) {
            throw new CoreException(ErrorType.BAD_REQUEST, "점수는 0 이상이어야 합니다.");
        }
    }

    private static void validateUpdateCommand(RankingCommand.UpdateRanking command) {
        if (command.rank() == null || command.rank() <= 0) {
            throw new CoreException(ErrorType.BAD_REQUEST, "순위는 양수여야 합니다.");
        }
        if (command.score() == null || command.score() < 0) {
            throw new CoreException(ErrorType.BAD_REQUEST, "점수는 0 이상이어야 합니다.");
        }
    }
}
