package com.blackwhitemap.blackwhitemap_back.domain.performer;

import java.util.List;

public class PerformerCommand {

    public record CreateChef(
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
