package com.blackwhitemap.blackwhitemap_back.domain.performer;

import com.blackwhitemap.blackwhitemap_back.support.error.CoreException;
import com.blackwhitemap.blackwhitemap_back.support.error.ErrorType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Column(name = "restaurant_name", length = 15)
    private String name;

    @Column(name = "restaurant_address", length = 50)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "restaurant_category", length = 10)
    private Category category;

    @Column(name = "naver_reservation_url", length = 100)
    private String naverReservationUrl;

    @Column(name = "catch_table_url", length = 100)
    private String catchTableUrl;

    @Column(name = "instagram_url", length = 100)
    private String instagramUrl;

    // TODO 가격, 휴무일 필드 추가될 수 있음

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
        FRENCH("프렌치"),
        CAFE("카페/베이커리");

        private final String koreanLabel;

        public static Category fromNullable(String value) {
            if (value == null || value.isBlank()) {
                return null;  // 선택적 필드
            }

            try {
                return Category.valueOf(value.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new CoreException(
                        ErrorType.BAD_REQUEST,
                        "유효하지 않은 레스토랑 카테고리입니다. 가능한 값: " +
                                Arrays.stream(Category.values())
                                        .map(Enum::name)
                                        .collect(Collectors.joining(", "))
                );
            }
        }
    }

    private Restaurant(
            String restaurantName,
            String address,
            Category category,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl
    ) {
        this.name = restaurantName;
        this.address = address;
        this.category = category;
        this.naverReservationUrl = naverReservationUrl;
        this.catchTableUrl = catchTableUrl;
        this.instagramUrl = instagramUrl;
    }

    public static Restaurant of(
            String restaurantName,
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
                restaurantName,
                address,
                category,
                naverReservationUrl,
                catchTableUrl,
                instagramUrl
        );
    }
}
