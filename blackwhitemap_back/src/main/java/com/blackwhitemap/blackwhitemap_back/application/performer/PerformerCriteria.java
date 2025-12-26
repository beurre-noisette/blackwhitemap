package com.blackwhitemap.blackwhitemap_back.application.performer;

import java.util.List;

public class PerformerCriteria {

    public record RegisterChef(
            String name,
            String nickname,
            String chefType,

            // Restaurant 관련 필드
            String restaurantName,
            String address,
            Double latitude,
            Double longitude,
            String closedDays,
            String restaurantCategory,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl,

            // ChefImage 관련 필드
            List<String> imageUrls
    ) {}

    /**
     * 셰프 정보 수정 Criteria
     * - 모든 필드는 선택적 (null 가능)
     * - null이 아닌 필드만 수정됨
     */
    public record UpdateChef(
            Long chefId,
            String name,
            String nickname,
            String chefType,

            // Restaurant 관련 필드
            String restaurantName,
            String address,
            Double latitude,
            Double longitude,
            String closedDays,
            String restaurantCategory,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl,

            // ChefImage 관련 필드
            List<String> imageUrls
    ) {}

    /**
     * Chef 목록 조회 Criteria
     * - type: Chef 타입 (ALL, BLACK, WHITE 또는 null)
     * - null인 경우 모든 타입 조회
     */
    public record GetChefs(
            String type
    ) {}
}
