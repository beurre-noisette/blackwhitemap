package com.blackwhitemap.blackwhitemap_back.domain.performer;

import java.util.List;

public class PerformerCommand {

    public record RegisterChef(
            String name,
            String nickname,
            Chef.Type type,

            // Restaurant 관련 필드
            String address,
            Restaurant.Category restaurantCategory,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl,

            // ChefImage 관련 필드
            List<String> imageUrls
    ) {}

    /**
     * 셰프 정보 수정 Command
     * - 모든 필드는 선택적 (null 가능)
     * - null이 아닌 필드만 수정됨
     */
    public record UpdateChef(
            Long chefId,
            String name,
            String nickname,
            Chef.Type type,

            // Restaurant 관련 필드
            String address,
            Restaurant.Category restaurantCategory,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl,

            // ChefImage 관련 필드
            List<String> imageUrls
    ) {}
}
