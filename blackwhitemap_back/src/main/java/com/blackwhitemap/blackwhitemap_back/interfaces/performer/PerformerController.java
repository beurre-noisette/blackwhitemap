package com.blackwhitemap.blackwhitemap_back.interfaces.performer;

import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerCriteria;
import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerFacade;
import com.blackwhitemap.blackwhitemap_back.interfaces.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/performer")
@RequiredArgsConstructor
public class PerformerController {

    private final PerformerFacade performerFacade;

    @PostMapping("/chef")
    public ApiResponse<Object> registerChef(@Valid @RequestBody PerformerRequest.RegisterChef registerRequest) {
        PerformerCriteria.RegisterChef registerCriteria = new PerformerCriteria.RegisterChef(
                registerRequest.name(),
                registerRequest.nickname(),
                registerRequest.chefType(),
                registerRequest.address(),
                registerRequest.restaurantCategory(),
                registerRequest.naverReservationUrl(),
                registerRequest.catchTableUrl(),
                registerRequest.instagramUrl(),
                registerRequest.imageUrls()
        );

        performerFacade.registerChef(registerCriteria);

        return ApiResponse.success();
    }
}
