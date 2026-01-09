package com.blackwhitemap.blackwhitemap_back.domain.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.performer.ChefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        rankings.sort(createRankingComparator(chefMap, koreanCollator));

        // 순위 부여 (1, 2, 3, ... 순차적으로)
        for (int i = 0; i < rankings.size(); i++) {
            ChefRanking ranking = rankings.get(i);
            ranking.updateRank(new RankingCommand.UpdateRank(i + 1));
            chefRankingRepository.save(ranking);
        }
    }

    /**
     * 랭킹 정렬 Comparator 생성
     * - 점수 내림차순
     * - 동점 시: address 유무 → chefType(WHITE 우선) → name/nickname ㄱㄴㄷ순
     */
    private Comparator<ChefRanking> createRankingComparator(
            Map<Long, Chef> chefMap,
            Collator koreanCollator
    ) {
        return Comparator
                // 1. 점수 내림차순
                .comparing(ChefRanking::getScore, Comparator.reverseOrder())
                // 2. address 있는 경우 우선 (null이면 뒤로)
                .thenComparing(ranking -> {
                    Chef chef = chefMap.get(ranking.getChefId());
                    if (chef == null || chef.getRestaurant() == null) {
                        return 1; // 뒤로
                    }
                    String address = chef.getRestaurant().getAddress();
                    return (address != null && !address.isBlank()) ? 0 : 1;
                })
                // 3. chefType이 WHITE인 경우 우선
                .thenComparing(ranking -> {
                    Chef chef = chefMap.get(ranking.getChefId());
                    if (chef == null) {
                        return 1;
                    }
                    return chef.getType() == Chef.Type.WHITE ? 0 : 1;
                })
                // 4. name/nickname ㄱㄴㄷ순 (둘 다 BLACK이면 nickname 사용)
                .thenComparing((firstRanking, secondRanking) -> {
                    Chef firstChef = chefMap.get(firstRanking.getChefId());
                    Chef secondChef = chefMap.get(secondRanking.getChefId());

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
}