package com.blackwhitemap.blackwhitemap_back.application.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.ranking.RankingCommand;
import com.blackwhitemap.blackwhitemap_back.domain.ranking.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;

@Component
@RequiredArgsConstructor
public class RankingFacade {

    private final RankingService rankingService;

    /**
     * 일간 랭킹 점수 추가 및 순위 재계산
     * 1. 각 셰프별 점수 누적 (기존 데이터 있으면 점수 추가, 없으면 생성)
     * 2. 해당 날짜의 모든 랭킹 순위 재계산
     */
    @Caching(evict = {
            @CacheEvict(value = "dailyBestChefs", allEntries = true),
            @CacheEvict(value = "weeklyBestChefs", allEntries = true)
    })
    @Transactional
    public void updateDailyRankings(RankingCriteria.UpdateDailyRanking updateCriteria) {
        // 1. 점수 누적
        for (RankingCriteria.RankingEntry entry : updateCriteria.rankings()) {
            RankingCommand.AddDailyScore addDailyScoreCommand = new RankingCommand.AddDailyScore(
                    entry.chefId(),
                    updateCriteria.periodStart(),
                    entry.score()
            );
            rankingService.addDailyScore(addDailyScoreCommand);
        }

        // 2. 순위 재계산
        rankingService.recalculateDailyRanks(updateCriteria.periodStart());
    }

    /**
     * 주간 랭킹 집계
     * - 배치 실행 시점과 무관하게 "가장 최근 완료된 화~월 주기"를 자동 계산
     * - 월요일: 해당 주 화~월 집계
     * - 화~일: 이전 주 화~월 집계
     */
    @CacheEvict(value = "weeklyBestChefs", allEntries = true)
    public void aggregateWeeklyRanking(RankingCriteria.AggregateWeekly aggregateCriteria) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate latestMonday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // 집계 대상 기간: 지난 화요일 ~ 이번 월요일
        LocalDate dataStartDate = latestMonday.minusDays(6);  // 화요일
        LocalDate dataEndDate = latestMonday;                               // 월요일

        // WEEKLY 데이터 표시 기간: 다음 화요일부터
        LocalDate displayPeriodStart = latestMonday.plusDays(1);  // 화요일

        RankingCommand.AggregateWeeklyRanking aggregateCommand = new RankingCommand.AggregateWeeklyRanking(
                dataStartDate,
                dataEndDate,
                displayPeriodStart,
                aggregateCriteria.topN()
        );

        rankingService.aggregateWeeklyRanking(aggregateCommand);
    }
}