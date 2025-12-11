package com.blackwhitemap.blackwhitemap_back.domain.performer;

public class PerformerCommand {

    public record CreateChef(
            String name,
            String nickname,
            Chef.Type type,
            Restaurant restaurant,
            ChefImages chefImages
    ) {}

    public record CreateCategory(
            String address,
            Restaurant.Category restaurantCategory,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl
    ) {}
}
