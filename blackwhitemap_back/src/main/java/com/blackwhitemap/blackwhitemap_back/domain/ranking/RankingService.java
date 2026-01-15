package com.blackwhitemap.blackwhitemap_back.domain.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.performer.ChefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Collator;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final ChefRankingRepository chefRankingRepository;
    private final ChefRepository chefRepository;

    /**
     * 일간 랭킹 점수 추가
     * - 기존 데이터가 있으면 점수 누적, 없으면 새로 생성
     * - 순위는 0으로 설정 (이후 recalculateRanks에서 일괄 계산)
     */
    public ChefRanking addDailyScore(RankingCommand.AddDailyScore addDailyScoreCommand) {
        return chefRankingRepository
                .findByTypeAndPeriodStartAndChefId(
                        ChefRanking.Type.DAILY,
                        addDailyScoreCommand.periodStart(),
                        addDailyScoreCommand.chefId()
                )
                .map(existing -> {
                    existing.addScore(addDailyScoreCommand.score());
                    return chefRankingRepository.save(existing);
                })
                .orElseGet(() -> {
                    ChefRanking newRanking = ChefRanking.ofDailyWithScore(addDailyScoreCommand);
                    return chefRankingRepository.save(newRanking);
                });
    }

    /**
     * 특정 날짜의 일간 랭킹 순위 재계산
     * - 점수 기준 내림차순 정렬
     * - 동점자 정렬 기준:
     *   1. address 값이 있는 경우 우선
     *   2. chefType이 WHITE인 경우 우선
     *   3. name 첫글자 ㄱㄴㄷ순 (둘 다 BLACK이면 nickname으로 비교)
     */
    public void recalculateDailyRanks(LocalDate periodStart) {
        List<ChefRanking> rankings = chefRankingRepository.findAllByTypeAndPeriodStart(
                ChefRanking.Type.DAILY,
                periodStart
        );

        if (rankings.isEmpty()) {
            return;
        }

        // Chef 정보 조회 (동점자 정렬용)
        List<Long> chefIds = rankings.stream()
                .map(ChefRanking::getChefId)
                .toList();
        Map<Long, Chef> chefMap = chefRepository.findAllByIdIn(chefIds).stream()
                .collect(Collectors.toMap(Chef::getId, Function.identity()));

        // 정렬: 점수 내림차순 → 동점자 세부 정렬
        Collator koreanCollator = Collator.getInstance(Locale.KOREAN);
        rankings.sort(createRankingComparator(
                ChefRanking::getChefId,
                ChefRanking::getScore,
                chefMap,
                koreanCollator
        ));

        // 순위 부여 (1, 2, 3, ... 순차적으로)
        for (int i = 0; i < rankings.size(); i++) {
            ChefRanking ranking = rankings.get(i);
            ranking.updateRank(new RankingCommand.UpdateRank(i + 1));
            chefRankingRepository.save(ranking);
        }
    }

    /**
     * 재사용 가능한 랭킹 Comparator 생성
     * - chefId, score 추출 로직만 전달받아 공통 타이브레이커 적용
     */
    private <T> Comparator<T> createRankingComparator(
            Function<T, Long> chefIdExtractor,
            Function<T, Long> scoreExtractor,
            Map<Long, Chef> chefMap,
            Collator koreanCollator
    ) {
        return Comparator
                // 1. 점수 내림차순
                .comparing(scoreExtractor, Comparator.reverseOrder())
                // 2. address 있는 경우 우선 (null이면 뒤로)
                .thenComparing(item -> {
                    Chef chef = chefMap.get(chefIdExtractor.apply(item));
                    if (chef == null || chef.getRestaurant() == null) {
                        return 1; // 뒤로
                    }
                    String address = chef.getRestaurant().getAddress();
                    return (address != null && !address.isBlank()) ? 0 : 1;
                })
                // 3. chefType이 WHITE인 경우 우선
                .thenComparing(item -> {
                    Chef chef = chefMap.get(chefIdExtractor.apply(item));
                    if (chef == null) {
                        return 1;
                    }
                    return chef.getType() == Chef.Type.WHITE ? 0 : 1;
                })
                // 4. name/nickname ㄱㄴㄷ순 (둘 다 BLACK이면 nickname 사용)
                .thenComparing((firstItem, secondItem) -> {
                    Chef firstChef = chefMap.get(chefIdExtractor.apply(firstItem));
                    Chef secondChef = chefMap.get(chefIdExtractor.apply(secondItem));

                    String firstChefSortKey = getSortKey(firstChef);
                    String secondChefSortKey = getSortKey(secondChef);

                    return koreanCollator.compare(firstChefSortKey, secondChefSortKey);
                });
    }

    /**
     * 정렬용 키 반환
     * - BLACK 타입이면 nickname 우선, 그 외에는 name 우선
     */
    private String getSortKey(Chef chef) {
        if (chef == null) {
            return "";
        }

        // BLACK 타입이면 nickname 우선
        if (chef.getType() == Chef.Type.BLACK) {
            if (chef.getNickname() != null && !chef.getNickname().isBlank()) {
                return chef.getNickname();
            }
        }

        // name이 있으면 name, 없으면 nickname
        if (chef.getName() != null && !chef.getName().isBlank()) {
            return chef.getName();
        }
        if (chef.getNickname() != null && !chef.getNickname().isBlank()) {
            return chef.getNickname();
        }
        return "";
    }

    /**
     * 특정 날짜의 일간 랭킹 전체 삭제
     */
    public void deleteDailyRankingByDate(LocalDate periodStart) {
        chefRankingRepository.deleteByTypeAndPeriodStart(
                ChefRanking.Type.DAILY,
                periodStart
        );
    }

    /**
     * 주간 랭킹 집계
     * - 기존 해당 주차 WEEKLY 데이터 삭제 후 새로 생성
     * - DAILY 데이터를 chef별로 합산하여 상위 N명 선정
     *
     * @param aggregateCommand weekStart(화요일), topN(상위 몇 명)
     */
    @Transactional
    public void aggregateWeeklyRanking(RankingCommand.AggregateWeeklyRanking aggregateCommand) {
        LocalDate weekStart = aggregateCommand.weekStart();
        LocalDate weekEnd = weekStart.plusDays(6);
        int topN = aggregateCommand.topN();

        // 1. 기존 해당 주차 WEEKLY 데이터 삭제
        chefRankingRepository.deleteByTypeAndPeriodStart(
                ChefRanking.Type.WEEKLY,
                weekStart
        );

        // 2. 해당 기간의 DAILY 데이터 조회
        List<ChefRanking> targetRankings = chefRankingRepository.findDailyRankingsByPeriodRange(
                weekStart,
                weekEnd
        );

        if (targetRankings.isEmpty()) {
            return;
        }

        // 3. chef별 점수 합산
        Map<Long, Long> chefScoreMap = targetRankings.stream()
                .collect(Collectors.groupingBy(
                        ChefRanking::getChefId,
                        Collectors.summingLong(ChefRanking::getScore)
                ));

        // 4. Chef 정보 조회 (동점자 정렬용)
        List<Long> chefIds = chefScoreMap.keySet().stream().toList();
        Map<Long, Chef> chefMap = chefRepository.findAllByIdIn(chefIds).stream()
                .collect(Collectors.toMap(Chef::getId, Function.identity()));

        // 5. 정렬 및 상위 N명 선정
        List<Map.Entry<Long, Long>> sortedEntries = chefScoreMap.entrySet().stream()
                .sorted(createRankingComparator(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        chefMap,
                        Collator.getInstance(Locale.KOREAN)
                ))
                .limit(topN)
                .toList();

        // 6. WEEKLY 랭킹 생성 및 저장
        for (int i = 0; i < sortedEntries.size(); i++) {
            Map.Entry<Long, Long> entry = sortedEntries.get(i);

            RankingCommand.CreateWeeklyRanking command =
                    new RankingCommand.CreateWeeklyRanking(
                            entry.getKey(),
                            weekStart,
                            entry.getValue(),
                            i + 1
                    );

            ChefRanking weeklyRanking = ChefRanking.ofWeekly(command);
            chefRankingRepository.save(weeklyRanking);
        }
    }
}