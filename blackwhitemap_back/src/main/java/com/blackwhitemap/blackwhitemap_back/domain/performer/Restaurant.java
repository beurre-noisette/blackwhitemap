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

    @Column(name = "restaurant_name", length = 30)
    private String name;

    @Column(name = "restaurant_address", length = 50)
    private String address;

    @Column(name = "restaurant_small_address", length = 20)
    private String smallAddress;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "restaurant_closed_days", length = 50)
    private String closedDays;

    @Enumerated(EnumType.STRING)
    @Column(name = "restaurant_category", length = 20)
    private Category category;

    @Column(name = "naver_reservation_url")
    private String naverReservationUrl;

    @Column(name = "catch_table_url")
    private String catchTableUrl;

    @Column(name = "instagram_url")
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
        FRENCH("프렌치"),
        AMERICAN("아메리칸"),
        BUNSIK("분식"),
        DINING("파인다이닝"),
        CONTEMPORARY("컨템포러리"),
        SEAFOOD("해산물"),
        COURSE("코스요리"),
        WINE("와인바"),
        TONKATSU("돈카츠"),
        NAENGMYEON("냉면"),
        ASIAN("아시안"),
        GRILLED_EEL("장어구이"),
        EUROPEAN("유러피안"),
        SASHIMI("회/사시미"),
        HAMBURGER("햄버거"),
        VIETNAMESE("베트남음식"),
        PIZZA("피자"),
        FUSION("퓨전"),
        WESTERN_FOOD("양식"),
        BRUNCH("브런치"),
        MEAT("고기요리"),
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
            String smallAddress,
            Double latitude,
            Double longitude,
            String closedDays,
            Category category,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl
    ) {
        this.name = restaurantName;
        this.address = address;
        this.smallAddress = smallAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.closedDays = closedDays;
        this.category = category;
        this.naverReservationUrl = naverReservationUrl;
        this.catchTableUrl = catchTableUrl;
        this.instagramUrl = instagramUrl;
    }

    public static Restaurant of(
            String restaurantName,
            String address,
            String smallAddress,
            Double latitude,
            Double longitude,
            String closedDays,
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
                smallAddress,
                latitude,
                longitude,
                closedDays,
                category,
                naverReservationUrl,
                catchTableUrl,
                instagramUrl
        );
    }
}
