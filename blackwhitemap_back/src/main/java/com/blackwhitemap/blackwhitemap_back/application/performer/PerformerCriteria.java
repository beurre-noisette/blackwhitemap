package com.blackwhitemap.blackwhitemap_back.application.performer;

import java.util.List;

public class PerformerCriteria {

    public record RegisterChef(
            String name,
            String nickname,
            String chefType,

            // Restaurant 관련 필드
            String address,
            String restaurantCategory,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl,

            // ChefImage 관련 필드
            List<String> imageUrls
    ) {}
}
