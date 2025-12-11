package com.blackwhitemap.blackwhitemap_back.domain.performer;

import com.blackwhitemap.blackwhitemap_back.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.*;

@Getter
@Entity
@Table(name = "chef")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chef extends BaseEntity {

    @Column(name = "name", nullable = false, length = 5)
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

    public static Chef of(PerformerCommand.CreateChef command) {
        return new Chef(
                command.name(),
                command.nickname(),
                command.type(),
                command.restaurant(),
                command.chefImages()
        );
    }

}
