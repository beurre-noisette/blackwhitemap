package com.blackwhitemap.blackwhitemap_back.infrastructure.performer;

import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerQueryRepository;
import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerResult;
import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.performer.Region;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.blackwhitemap.blackwhitemap_back.domain.performer.QChef.chef;

@Repository
@RequiredArgsConstructor
public class PerformerQueryRepositoryImpl implements PerformerQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PerformerResult.ChefInfo> findChefsByType(Chef.Type type) {
        // Chef 엔티티 조회 (imageUrls는 ElementCollection이므로 fetch join)
        List<Chef> chefs = queryFactory
                .selectFrom(chef)
                .distinct()
                .leftJoin(chef.images.imageUrls).fetchJoin()
                .where(
                        chef.restaurant.address.isNotNull(),
                        type != null ? chef.type.eq(type) : null
                )
                .orderBy(chef.id.asc())
                .fetch();

        // Chef 엔티티를 PerformerResult.ChefInfo DTO로 변환
        return chefs.stream()
                .map(PerformerResult.ChefInfo::from)
                .toList();
    }

    @Override
    public List<PerformerResult.ChefClusterInfo> findChefClusters() {
        List<Chef> chefs = queryFactory
                .selectFrom(chef)
                .where(chef.restaurant.address.isNotNull())
                .fetch();

        // e.g. {SEOUL={BLACK=10, WHITE=5}, BUSAN={BLACK=7, WHITE=3}}
        Map<Region, Map<Chef.Type, Long>> regionStats = chefs.stream()
                .flatMap(chef -> {
                    Region region = Region.fromAddress(chef.getRestaurant().getAddress());
                    return region != null
                            ? Stream.of(Map.entry(chef, region))
                            : Stream.empty();
                })
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        Collectors.groupingBy(
                                entry -> entry.getKey().getType(),
                                Collectors.counting()
                        )
                ));

        return regionStats.entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .map(entry -> {
                    Region region = entry.getKey();
                    Map<Chef.Type, Long> typeCounts = entry.getValue();

                    int blackCount = typeCounts.getOrDefault(Chef.Type.BLACK, 0L).intValue();
                    int whiteCount = typeCounts.getOrDefault(Chef.Type.WHITE, 0L).intValue();

                    return new PerformerResult.ChefClusterInfo(
                            region.name(),
                            blackCount,
                            whiteCount,
                            region.getLatitude(),
                            region.getLongitude()
                    );
                })
                .sorted(Comparator.comparing(PerformerResult.ChefClusterInfo::region))
                .toList();
    }
}
