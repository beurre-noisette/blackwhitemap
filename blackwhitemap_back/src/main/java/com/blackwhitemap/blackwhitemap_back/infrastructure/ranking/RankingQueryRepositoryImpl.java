package com.blackwhitemap.blackwhitemap_back.infrastructure.ranking;

import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingQueryRepository;
import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingResult;
import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.ranking.ChefRanking;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.text.Collator;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.blackwhitemap.blackwhitemap_back.domain.performer.QChef.chef;
import static com.blackwhitemap.blackwhitemap_back.domain.ranking.QChefRanking.chefRanking;

@Repository
@RequiredArgsConstructor
public class RankingQueryRepositoryImpl implements RankingQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RankingResult.WeeklyBestChef> findWeeklyBestChefs(
            LocalDate periodStart,
            int limit
    ) {
        List<ChefRanking> rankings = queryFactory
                .selectFrom(chefRanking)
                .where(
                        chefRanking.type.eq(ChefRanking.Type.WEEKLY),
                        chefRanking.periodStart.eq(periodStart)
                )
                .orderBy(chefRanking.rank.asc())
                .limit(limit)
                .fetch();

        if (rankings.isEmpty()) {
            return List.of();
        }

        List<Long> chefIds = rankings.stream()
                .map(ChefRanking::getChefId)
                .toList();

        List<Chef> chefs = queryFactory
                .selectFrom(chef)
                .distinct()
                .leftJoin(chef.images.imageUrls).fetchJoin()
                .where(chef.id.in(chefIds))
                .fetch();

        Map<Long, Chef> chefMap = chefs.stream()
                .collect(Collectors.toMap(Chef::getId, Function.identity()));

        return rankings.stream()
                .map(ranking -> RankingResult.WeeklyBestChef.from(
                        ranking,
                        chefMap.get(ranking.getChefId())
                ))
                .toList();
    }

    @Override
    public List<RankingResult.DailyBestChef> findDailyBestChefs(
            List<LocalDate> periodStartDates,
            int resultLimit
    ) {
        NumberExpression<Long> totalScore = chefRanking.score.sumLong();

        // 1. 여러 날짜의 점수를 chefId별로 집계
        List<Tuple> aggregatedScores = queryFactory
                .select(
                        chefRanking.chefId,
                        totalScore
                )
                .from(chefRanking)
                .where(
                        chefRanking.type.eq(ChefRanking.Type.DAILY),
                        chefRanking.periodStart.in(periodStartDates)
                )
                .groupBy(chefRanking.chefId)
                .orderBy(totalScore.desc())
                .fetch();

        if (aggregatedScores.isEmpty()) {
            return List.of();
        }

        // 2. chefId → 합산점수 맵 생성
        Map<Long, Long> scoreMap = aggregatedScores.stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(chefRanking.chefId),
                        tuple -> {
                            Long score = tuple.get(totalScore);
                            return score != null ? score : 0L;
                        }
                ));

        List<Long> chefIds = new ArrayList<>(scoreMap.keySet());

        // 3. Chef 엔티티 조회 (tie-breaking용)
        List<Chef> chefs = queryFactory
                .selectFrom(chef)
                .where(chef.id.in(chefIds))
                .fetch();

        Map<Long, Chef> chefMap = chefs.stream()
                .collect(Collectors.toMap(Chef::getId, Function.identity()));

        // 4. AggregatedRanking 객체 생성 및 정렬
        Collator koreanCollator = Collator.getInstance(Locale.KOREAN);
        List<AggregatedRanking> rankings = chefIds.stream()
                .map(chefId -> new AggregatedRanking(
                        chefId,
                        scoreMap.get(chefId),
                        chefMap.get(chefId)
                ))
                .sorted(createAggregatedComparator(koreanCollator))
                .toList();

        // 5. 중복 제거 (nickname 우선, 없으면 name)
        Map<String, RankingResult.DailyBestChef> uniqueByName = new LinkedHashMap<>();
        int rank = 1;

        for (AggregatedRanking ranking : rankings) {
            Chef chefEntity = ranking.chef();
            if (chefEntity == null) {
                continue;
            }

            // nickname 우선, 없으면 name
            String uniqueKey = chefEntity.getNickname() != null
                    ? chefEntity.getNickname()
                    : chefEntity.getName();

            // 둘 다 없으면 스킵
            if (uniqueKey == null) {
                continue;
            }

            // 이미 존재하면 스킵 (먼저 등장 = 높은 점수)
            if (uniqueByName.containsKey(uniqueKey)) {
                continue;
            }

            uniqueByName.put(uniqueKey, RankingResult.DailyBestChef.from(
                    rank++,
                    ranking.totalScore(),
                    chefEntity
            ));
        }

        // 6. 상위 resultLimit명 반환
        return uniqueByName.values().stream()
                .limit(resultLimit)
                .toList();
    }

    /**
     * 합산 랭킹 데이터를 담는 Helper record
     *
     * @param chefId     셰프 ID
     * @param totalScore 합산 점수
     * @param chef       셰프 엔티티
     */
    private record AggregatedRanking(Long chefId, Long totalScore, Chef chef) {
    }

    /**
     * 합산 랭킹 정렬을 위한 Comparator 생성
     * - RankingService의 tie-breaking 로직과 동일
     * - 점수 내림차순 → address 유무 → chefType(WHITE 우선) → name/nickname ㄱㄴㄷ순
     */
    private Comparator<AggregatedRanking> createAggregatedComparator(Collator koreanCollator) {
        return Comparator
                // 1. 점수 내림차순
                .comparing(AggregatedRanking::totalScore, Comparator.reverseOrder())
                // 2. address 있는 경우 우선 (null이면 뒤로)
                .thenComparing(ranking -> {
                    Chef chef = ranking.chef();
                    if (chef == null || chef.getRestaurant() == null) {
                        return 1; // 뒤로
                    }
                    String address = chef.getRestaurant().getAddress();
                    return (address != null && !address.isBlank()) ? 0 : 1;
                })
                // 3. chefType이 WHITE인 경우 우선
                .thenComparing(ranking -> {
                    Chef chef = ranking.chef();
                    if (chef == null) {
                        return 1;
                    }
                    return chef.getType() == Chef.Type.WHITE ? 0 : 1;
                })
                // 4. name/nickname ㄱㄴㄷ순
                .thenComparing((r1, r2) -> {
                    String key1 = getSortKey(r1.chef());
                    String key2 = getSortKey(r2.chef());
                    return koreanCollator.compare(key1, key2);
                });
    }

    /**
     * 정렬용 키 반환
     * - BLACK 타입이면 nickname 우선, 그 외에는 name 우선
     * - RankingService의 getSortKey 로직과 동일
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
}
