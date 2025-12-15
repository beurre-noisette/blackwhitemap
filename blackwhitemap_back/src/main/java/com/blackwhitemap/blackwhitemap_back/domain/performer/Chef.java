package com.blackwhitemap.blackwhitemap_back.domain.performer;

import com.blackwhitemap.blackwhitemap_back.domain.BaseEntity;
import com.blackwhitemap.blackwhitemap_back.support.error.CoreException;
import com.blackwhitemap.blackwhitemap_back.support.error.ErrorType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@Table(name = "chef")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chef extends BaseEntity {

    @Column(name = "name", length = 5)
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
        validateChefInfo(
                command.name(),
                command.nickname(),
                command.type()
        );

        Restaurant restaurant = Restaurant.of(
                command.address(),
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

    /**
     * 셰프 정보 검증
     * - Type은 필수
     * - name과 nickname 중 하나는 필수 (둘 다 없으면 안 됨)
     * - restaurant와 images는 선택적 (null 가능)
     */
    private static void validateChefInfo(
            String name,
            String nickname,
            Type type
    ) {
        if (type == null) {
            throw new CoreException(ErrorType.BAD_REQUEST, "셰프 타입은 필수입니다.");
        }

        boolean hasName = name != null && !name.isBlank();
        boolean hasNickname = nickname != null && !nickname.isBlank();

        if (!hasName && !hasNickname) {
            throw new CoreException(ErrorType.BAD_REQUEST, "이름 또는 별명 중 하나는 필수입니다.");
        }
    }

}
