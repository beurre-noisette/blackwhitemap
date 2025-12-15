package com.blackwhitemap.blackwhitemap_back.application.performer;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.performer.PerformerCommand;
import com.blackwhitemap.blackwhitemap_back.domain.performer.PerformerService;
import com.blackwhitemap.blackwhitemap_back.domain.performer.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PerformerFacade {

    private final PerformerService performerService;

    public void registerChef(PerformerCriteria.RegisterChef registerCriteria) {
        Chef.Type chefType = Chef.Type.from(registerCriteria.chefType());
        Restaurant.Category restaurantCategory = Restaurant.Category.fromNullable(registerCriteria.restaurantCategory());

        PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                registerCriteria.name(),
                registerCriteria.nickname(),
                chefType,
                registerCriteria.address(),
                restaurantCategory,
                registerCriteria.naverReservationUrl(),
                registerCriteria.catchTableUrl(),
                registerCriteria.instagramUrl(),
                registerCriteria.imageUrls()
        );

        performerService.registerChef(registerCommand);
    }
}
