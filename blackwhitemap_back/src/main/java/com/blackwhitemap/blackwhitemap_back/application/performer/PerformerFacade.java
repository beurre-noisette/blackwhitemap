package com.blackwhitemap.blackwhitemap_back.application.performer;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.performer.PerformerCommand;
import com.blackwhitemap.blackwhitemap_back.domain.performer.PerformerService;
import com.blackwhitemap.blackwhitemap_back.domain.performer.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerformerFacade {

    private final PerformerService performerService;

    /**
     * Chef 등록 (캐시 무효화)
     * - 새 Chef 추가 시 ALL 캐시에 영향
     */
    @Caching(evict = {
            @CacheEvict(value = "chefs", allEntries = true),
            @CacheEvict(value = "chefClusters", allEntries = true)
    })
    public void registerChef(PerformerCriteria.RegisterChef registerCriteria) {
        Chef.Type chefType = Chef.Type.from(registerCriteria.chefType());
        Restaurant.Category restaurantCategory = Restaurant.Category.fromNullable(registerCriteria.restaurantCategory());

        PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                registerCriteria.name(),
                registerCriteria.nickname(),
                chefType,
                registerCriteria.restaurantName(),
                registerCriteria.address(),
                registerCriteria.smallAddress(),
                registerCriteria.latitude(),
                registerCriteria.longitude(),
                registerCriteria.closedDays(),
                restaurantCategory,
                registerCriteria.naverReservationUrl(),
                registerCriteria.catchTableUrl(),
                registerCriteria.instagramUrl(),
                registerCriteria.imageUrls(),
                registerCriteria.finalPlacement()
        );

        performerService.registerChef(registerCommand);
    }

    /**
     * Chef 수정 (캐시 무효화)
     * - chefs, chefClusters 캐시 모두 무효화 필요
     */
    @Caching(evict = {
            @CacheEvict(value = "chefs", allEntries = true),
            @CacheEvict(value = "chefClusters", allEntries = true)
    })
    public void updateChef(PerformerCriteria.UpdateChef updateCriteria) {
        Chef.Type chefType = Chef.Type.fromNullable(updateCriteria.chefType());
        Restaurant.Category restaurantCategory = Restaurant.Category.fromNullable(updateCriteria.restaurantCategory());

        PerformerCommand.UpdateChef updateCommand = new PerformerCommand.UpdateChef(
                updateCriteria.chefId(),
                updateCriteria.name(),
                updateCriteria.nickname(),
                chefType,
                updateCriteria.restaurantName(),
                updateCriteria.address(),
                updateCriteria.smallAddress(),
                updateCriteria.latitude(),
                updateCriteria.longitude(),
                updateCriteria.closedDays(),
                restaurantCategory,
                updateCriteria.naverReservationUrl(),
                updateCriteria.catchTableUrl(),
                updateCriteria.instagramUrl(),
                updateCriteria.imageUrls()
        );

        performerService.updateChef(updateCommand);
    }
}
