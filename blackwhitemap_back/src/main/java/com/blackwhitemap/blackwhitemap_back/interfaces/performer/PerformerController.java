package com.blackwhitemap.blackwhitemap_back.interfaces.performer;

import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerCriteria;
import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerFacade;
import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerQuery;
import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerResult;
import com.blackwhitemap.blackwhitemap_back.interfaces.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/performer")
@RequiredArgsConstructor
public class PerformerController {

    private final PerformerFacade performerFacade;
    private final PerformerQuery performerQuery;

    @PostMapping("/chef")
    public ApiResponse<Object> registerChef(@Valid @RequestBody PerformerRequest.RegisterChef registerRequest) {
        PerformerCriteria.RegisterChef registerCriteria = new PerformerCriteria.RegisterChef(
                registerRequest.name(),
                registerRequest.nickname(),
                registerRequest.chefType(),
                registerRequest.restaurantName(),
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
                updateRequest.restaurantName(),
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

    /**
     * Chef 목록 조회
     * @param type Chef 타입 (ALL, BLACK, WHITE)
     * @return Chef 목록 (address가 있는 Chef만 반환)
     */
    @GetMapping("/chefs")
    public ApiResponse<List<PerformerResponse.ChefInfo>> getChefs(
            @RequestParam(required = false) String type
    ) {
        PerformerCriteria.GetChefs getCriteria = new PerformerCriteria.GetChefs(type);

        List<PerformerResult.ChefInfo> queryResults = performerQuery.getChefs(getCriteria);

        List<PerformerResponse.ChefInfo> chefInfos = queryResults.stream()
                .map(result -> new PerformerResponse.ChefInfo(
                        result.id(),
                        result.name(),
                        result.nickname(),
                        result.type(),
                        result.restaurantName(),
                        result.address(),
                        result.category(),
                        result.naverReservationUrl(),
                        result.catchTableUrl(),
                        result.instagramUrl(),
                        result.imageUrls(),
                        result.viewCount()
                ))
                .toList();

        return ApiResponse.success(chefInfos);
    }
}
