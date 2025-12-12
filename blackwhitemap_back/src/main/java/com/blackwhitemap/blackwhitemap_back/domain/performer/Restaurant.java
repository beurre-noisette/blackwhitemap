package com.blackwhitemap.blackwhitemap_back.domain.performer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Column(name = "store_address", length = 50)
    private String address;

    @Column(name = "store_category", length = 10)
    private Category category;

    @Column(name = "naver_reservation_url", length = 100)
    private String naverReservationUrl;

    @Column(name = "catch_table_url", length = 100)
    private String catchTableUrl;

    @Column(name = "instagram_url", length = 100)
    private String instagramUrl;

    @Getter
    @RequiredArgsConstructor
    public enum Category {
        KOREAN("한식"),
        CHINESE("중식"),
        JAPANESE("일식"),
        IZAKAYA("이자카야"),
        ITALIAN("이탈리안"),
        WESTERN("양식"),
        BISTRO("비스트로"),
        OMAKASE("오마카세"),
        BBQ("바베큐"),
        CAFE("카페/베이커리");

        private final String koreanLabel;
    }

    private Restaurant(
            String address,
            Category category,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl
    ) {
        this.address = address;
        this.category = category;
        this.naverReservationUrl = naverReservationUrl;
        this.catchTableUrl = catchTableUrl;
        this.instagramUrl = instagramUrl;
    }

    public static Restaurant of(
            String address,
            Category category,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl
    ) {
        boolean hasRestaurantInfo = address != null
                || category != null
                || naverReservationUrl != null
                || catchTableUrl != null
                || instagramUrl != null;

        if (!hasRestaurantInfo) {
            return null;
        }

        return new Restaurant(
                address,
                category,
                naverReservationUrl,
                catchTableUrl,
                instagramUrl
        );
    }
}
