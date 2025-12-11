package com.blackwhitemap.blackwhitemap_back.domain.performer;

import com.blackwhitemap.blackwhitemap_back.support.error.CoreException;
import com.blackwhitemap.blackwhitemap_back.support.error.ErrorType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChefImages {

    private static final int MIN_IMAGE_COUNT = 1;
    private static final int MAX_IMAGE_COUNT = 3;

    @ElementCollection
    @CollectionTable(
            name = "chef_image",
            joinColumns = @JoinColumn(name = "chef_id")
    )
    @Column(name = "image_url", length = 500, nullable = false)
    @OrderColumn(name = "display_order")
    private List<String> imageUrls = new ArrayList<>();

    private ChefImages(List<String> imageUrls) {
        validateImageUrls(imageUrls);
        this.imageUrls = new ArrayList<>(imageUrls);
    }

    public static ChefImages of(List<String> imageUrls) {
        return new ChefImages(imageUrls);
    }

    public static ChefImages of(String... imageUrls) {
        return new ChefImages(List.of(imageUrls));
    }

    private void validateImageUrls(List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            throw new CoreException(ErrorType.BAD_REQUEST, "최소 " + MIN_IMAGE_COUNT + "개의 이미지가 필요합니다.");
        }
        if (imageUrls.size() > MAX_IMAGE_COUNT) {
            throw new CoreException(ErrorType.BAD_REQUEST, "최대 " + MAX_IMAGE_COUNT + "개의 이미지만 등록할 수 있습니다.");
        }
    }
}
