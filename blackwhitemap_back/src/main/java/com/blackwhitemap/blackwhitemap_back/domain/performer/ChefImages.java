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
import java.util.LinkedHashSet;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChefImages {

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
        List<String> deduplicatedUrls = deduplicateImageUrls(imageUrls);
        validateImageUrls(deduplicatedUrls);
        this.imageUrls = deduplicatedUrls;
    }

    public static ChefImages of(List<String> imageUrls) {
        return new ChefImages(imageUrls);
    }

    /**
     * 이미지 중복 URL 제거 (순서는 유지 됨)
     */
    private List<String> deduplicateImageUrls(List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return new ArrayList<>();
        }

        return new ArrayList<>(new LinkedHashSet<>(imageUrls));
    }

    /**
     * 이미지 URL 목록 검증
     * - 이미지가 없는 경우 허용
     * - 최대 3개까지만 허용
     */
    private void validateImageUrls(List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            return;
        }

        if (imageUrls.size() > MAX_IMAGE_COUNT) {
            throw new CoreException(ErrorType.BAD_REQUEST, "최대 " + MAX_IMAGE_COUNT + "개의 이미지만 등록할 수 있습니다.");
        }
    }
}
