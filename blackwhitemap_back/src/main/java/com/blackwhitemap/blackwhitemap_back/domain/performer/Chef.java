package com.blackwhitemap.blackwhitemap_back.domain.performer;

import com.blackwhitemap.blackwhitemap_back.domain.BaseEntity;
import com.blackwhitemap.blackwhitemap_back.support.error.CoreException;
import com.blackwhitemap.blackwhitemap_back.support.error.ErrorType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "chef")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chef extends BaseEntity {

    @Column(name = "name", length = 15)
    private String name;

    @Column(name = "nickname", length = 15)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "chef_type", nullable = false, length = 10)
    private Type type;

    @Embedded
    private Restaurant restaurant;

    @Embedded
    private ChefImages images;

    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;

    @Version
    @Column(name = "version")
    private Long version;

    @Getter
    @RequiredArgsConstructor
    public enum Type {
        BLACK("흑요리사"),
        WHITE("백요리사");

        private final String description;

        public static Type from(String value) {
            if (value == null || value.isBlank()) {
                throw new CoreException(ErrorType.BAD_REQUEST, "셰프 타입은 필수입니다.");
            }

            try {
                return Type.valueOf(value.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new CoreException(ErrorType.BAD_REQUEST,
                        "유효하지 않은 셰프 타입입니다. 가능한 값: BLACK, WHITE");
            }
        }

        public static Type fromNullable(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }

            try {
                return Type.valueOf(value.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new CoreException(ErrorType.BAD_REQUEST,
                        "유효하지 않은 셰프 타입입니다. 가능한 값: BLACK, WHITE");
            }
        }
    }

    private Chef(
            String name,
            String nickname,
            Type type,
            Restaurant restaurant,
            ChefImages images
    ) {
        this.name = name;
        this.nickname = nickname;
        this.type = type;
        this.restaurant = restaurant;
        this.images = images;
        this.viewCount = 0L;
    }

    public static Chef of(PerformerCommand.RegisterChef command) {
        validateChefType(command.type());
        validateNameOrNickname(command.name(), command.nickname());

        Restaurant restaurant = Restaurant.of(
                command.restaurantName(),
                command.address(),
                command.latitude(),
                command.longitude(),
                command.closedDays(),
                command.restaurantCategory(),
                command.naverReservationUrl(),
                command.catchTableUrl(),
                command.instagramUrl()
        );
        ChefImages images = ChefImages.of(command.imageUrls());

        return new Chef(
                command.name(),
                command.nickname(),
                command.type(),
                restaurant,
                images
        );
    }

    private static void validateChefType(Type type) {
        if (type == null) {
            throw new CoreException(ErrorType.BAD_REQUEST, "셰프 타입은 필수입니다.");
        }
    }

    /**
     * name과 nickname 중 하나는 반드시 존재해야 함
     */
    private static void validateNameOrNickname(String name, String nickname) {
        boolean hasName = name != null && !name.isBlank();
        boolean hasNickname = nickname != null && !nickname.isBlank();

        if (!hasName && !hasNickname) {
            throw new CoreException(ErrorType.BAD_REQUEST, "이름 또는 별명 중 하나는 필수입니다.");
        }
    }

    /**
     * 셰프 이름 수정
     * - name과 nickname 중 하나는 필수이므로, name을 null로 변경 시 nickname이 있어야 함
     */
    public void updateName(String name) {
        validateNameOrNickname(name, this.nickname);
        this.name = name;
    }

    /**
     * 셰프 별명 수정
     * - name과 nickname 중 하나는 필수이므로, nickname을 null로 변경 시 name이 있어야 함
     */
    public void updateNickname(String nickname) {
        validateNameOrNickname(this.name, nickname);
        this.nickname = nickname;
    }

    /**
     * 셰프 타입 수정
     * - Type은 필수 필드
     */
    public void updateType(Type type) {
        if (type == null) {
            throw new CoreException(ErrorType.BAD_REQUEST, "셰프 타입은 필수입니다.");
        }
        this.type = type;
    }

    /**
     * 셰프 레스토랑 정보 수정
     * - 새로운 Restaurant 객체를 생성하여 교체
     * - 모든 필드가 null인 경우 업데이트하지 않음
     */
    public void updateRestaurant(
            String restaurantName,
            String address,
            Double latitude,
            Double longitude,
            String closedDays,
            Restaurant.Category category,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl
    ) {
        // 업데이트할 필드가 하나라도 있는지 확인
        boolean hasUpdate = restaurantName != null && !restaurantName.isBlank()
                || address != null
                || latitude != null
                || longitude != null
                || closedDays != null
                || category != null
                || naverReservationUrl != null
                || catchTableUrl != null
                || instagramUrl != null;

        if (!hasUpdate) {
            return;
        }

        this.restaurant = Restaurant.of(
                restaurantName,
                address,
                latitude,
                longitude,
                closedDays,
                category,
                naverReservationUrl,
                catchTableUrl,
                instagramUrl
        );
    }

    /**
     * 셰프 이미지 수정
     * - 새로운 ChefImages 객체를 생성하여 교체
     */
    public void updateImages(List<String> imageUrls) {
        this.images = ChefImages.of(imageUrls);
    }

}
