package com.blackwhitemap.blackwhitemap_back.infrastructure.ranking;

import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingQueryRepository;
import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingResult;
import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.ranking.ChefRanking;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
            LocalDate periodStart,
            int fetchLimit,
            int resultLimit
    ) {
        // 1. ChefRanking 조회 (rank 오름차순)
        List<ChefRanking> rankings = queryFactory
                .selectFrom(chefRanking)
                .where(
                        chefRanking.type.eq(ChefRanking.Type.DAILY),
                        chefRanking.periodStart.eq(periodStart)
                )
                .orderBy(chefRanking.rank.asc())
                .limit(fetchLimit)
                .fetch();

        if (rankings.isEmpty()) {
            return List.of();
        }

        // 2. Chef 정보 조회
        List<Long> chefIds = rankings.stream()
                .map(ChefRanking::getChefId)
                .toList();

        List<Chef> chefs = queryFactory
                .selectFrom(chef)
                .where(chef.id.in(chefIds))
                .fetch();

        Map<Long, Chef> chefMap = chefs.stream()
                .collect(Collectors.toMap(Chef::getId, Function.identity()));

        // 3. 중복 이름 필터링 (nickname 우선, 없으면 name)
        Map<String, RankingResult.DailyBestChef> uniqueByName = new LinkedHashMap<>();

        for (ChefRanking ranking : rankings) {
            Chef chefEntity = chefMap.get(ranking.getChefId());
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

            // 이미 존재하면 스킵 (먼저 등장 = 높은 rank)
            if (!uniqueByName.containsKey(uniqueKey)) {
                uniqueByName.put(uniqueKey, RankingResult.DailyBestChef.from(ranking, chefEntity));
            }
        }

        // 4. 상위 resultLimit명 반환
        return uniqueByName.values().stream()
                .limit(resultLimit)
                .toList();
    }
}
