package com.blackwhitemap.blackwhitemap_back.interfaces.performer;

import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerCriteria;
import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerFacade;
import com.blackwhitemap.blackwhitemap_back.interfaces.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/chef/{chefId}")
    public ApiResponse<Object> updateChefInfo(
            @PathVariable Long chefId,
            @Valid @RequestBody PerformerRequest.UpdateChefInfo updateRequest
    ) {
        PerformerCriteria.UpdateChef updateCriteria = new PerformerCriteria.UpdateChef(
                chefId,
                updateRequest.name(),
                updateRequest.nickname(),
                updateRequest.chefType(),
                updateRequest.address(),
                updateRequest.restaurantCategory(),
                updateRequest.naverReservationUrl(),
                updateRequest.catchTableUrl(),
                updateRequest.instagramUrl(),
                updateRequest.imageUrls()
        );

        performerFacade.updateChef(updateCriteria);

        return ApiResponse.success();
    }
}
