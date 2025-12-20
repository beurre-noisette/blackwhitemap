package com.blackwhitemap.blackwhitemap_back.interfaces.performer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public class PerformerRequest {

    public record RegisterChef(
            @Size(max = 5, message = "이름은 최대 5자까지 입력 가능합니다.")
            String name,

            @Size(max = 15, message = "별명은 최대 15자까지 입력 가능합니다.")
            String nickname,

            @NotBlank(message = "셰프 타입은 필수입니다.")
            String chefType,

            // Restaurant 관련 필드
            @Size(max = 15, message = "레스토랑 이름은 최대 15자까지 입력 가능합니다.")
            String restaurantName,

            @Size(max = 50, message = "주소는 최대 50자까지 입력 가능합니다.")
            String address,

            String restaurantCategory,

            @Size(max = 100, message = "네이버 예약 URL은 최대 100자까지 입력 가능합니다.")
            @URL
            String naverReservationUrl,

            @Size(max = 100, message = "캐치테이블 URL은 최대 100자까지 입력 가능합니다.")
            @URL
            String catchTableUrl,

            @Size(max = 100, message = "인스타그램 URL은 최대 100자까지 입력 가능합니다.")
            @URL
            String instagramUrl,

            // ChefImage 관련 필드
            List<@Size(max = 500, message = "이미지 URL은 최대 500자까지 입력 가능합니다.") @URL String> imageUrls
    ) {}

    public record UpdateChefInfo(
            @Size(max = 5, message = "이름은 최대 5자까지 입력 가능합니다.")
            String name,

            @Size(max = 15, message = "별명은 최대 15자까지 입력 가능합니다.")
            String nickname,

            String chefType,

            // Restaurant 관련 필드
            @Size(max = 15, message = "레스토랑 이름은 최대 15자까지 입력 가능합니다.")
            String restaurantName,

            @Size(max = 50, message = "주소는 최대 50자까지 입력 가능합니다.")
            String address,

            String restaurantCategory,

            @Size(max = 100, message = "네이버 예약 URL은 최대 100자까지 입력 가능합니다.")
            @URL
            String naverReservationUrl,

            @Size(max = 100, message = "캐치테이블 URL은 최대 100자까지 입력 가능합니다.")
            @URL
            String catchTableUrl,

            @Size(max = 100, message = "인스타그램 URL은 최대 100자까지 입력 가능합니다.")
            @URL
            String instagramUrl,

            // ChefImage 관련 필드
            List<@Size(max = 500, message = "이미지 URL은 최대 500자까지 입력 가능합니다.") @URL String> imageUrls
    ) {}
}
