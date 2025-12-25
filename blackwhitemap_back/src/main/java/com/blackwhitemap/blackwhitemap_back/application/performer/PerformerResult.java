package com.blackwhitemap.blackwhitemap_back.application.performer;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;

import java.util.List;

public class PerformerResult {

    /**
     * Chef 조회 결과
     * - QueryDSL에서 조회한 Chef 엔티티를 DTO로 변환
     */
    public record ChefInfo(
            Long id,
            String name,
            String nickname,
            String type,

            // Restaurant 정보
            String restaurantName,
            String address,
            String category,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl,

            // Chef 이미지
            List<String> imageUrls,

            // 조회수
            Long viewCount
    ) {
        public static ChefInfo from(Chef chef) {
            return new ChefInfo(
                    chef.getId(),
                    chef.getName(),
                    chef.getNickname(),
                    chef.getType() != null ? chef.getType().name() : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getName() : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getAddress() : null,
                    chef.getRestaurant() != null && chef.getRestaurant().getCategory() != null
                            ? chef.getRestaurant().getCategory().name()
                            : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getNaverReservationUrl() : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getCatchTableUrl() : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getInstagramUrl() : null,
                    chef.getImages() != null ? chef.getImages().getImageUrls() : List.of(),
                    chef.getViewCount()
            );
        }
    }

    /**
     * Chef 클러스터 조회 결과
     * - 시/도별로 그룹화된 셰프 통계 정보
     * - Region enum의 좌표 정보 포함
     */
    public record ChefClusterInfo(
            String region,
            Integer blackCount,
            Integer whiteCount,
            Double latitude,
            Double longitude
    ) {}
}
