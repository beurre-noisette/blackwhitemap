package com.blackwhitemap.blackwhitemap_back.infrastructure.ranking;

import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingQueryRepository;
import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingResult;
import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.ranking.ChefRanking;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
}
