package com.blackwhitemap.blackwhitemap_back.infrastructure.performer;

import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerQueryRepository;
import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerResult;
import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
