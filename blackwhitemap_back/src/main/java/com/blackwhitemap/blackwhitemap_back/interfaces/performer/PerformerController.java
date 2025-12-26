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
                registerRequest.latitude(),
                registerRequest.longitude(),
                registerRequest.closedDays(),
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
                updateRequest.latitude(),
                updateRequest.longitude(),
                updateRequest.closedDays(),
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
                        result.latitude(),
                        result.longitude(),
                        result.closedDays(),
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

    /**
     * 지도에 표시할 클러스터 데이터 조회
     * <p>
     * 시/도 단위로 그룹화된 셰프 통계 정보를 반환합니다.
     * - 서비스 초기 로드 시 getChefs API와 함께 호출
     * - 프론트엔드에서 지도 줌 아웃 시 클러스터 마커로 표시
     * - 캐싱 적용 (1시간 TTL)
     *
     * @return 클러스터 정보 리스트
     */
    @GetMapping("/chefs/cluster")
    public ApiResponse<List<PerformerResponse.ChefClusterInfo>> getChefClusters() {
        List<PerformerResult.ChefClusterInfo> queryResults = performerQuery.getChefClusters();

        List<PerformerResponse.ChefClusterInfo> response = queryResults.stream()
                .map(result -> new PerformerResponse.ChefClusterInfo(
                        result.region(),
                        result.blackCount(),
                        result.whiteCount(),
                        result.latitude(),
                        result.longitude()
                ))
                .toList();

        return ApiResponse.success(response);
    }
}
