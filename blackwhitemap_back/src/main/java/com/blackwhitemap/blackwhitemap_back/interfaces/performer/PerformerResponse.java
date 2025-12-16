package com.blackwhitemap.blackwhitemap_back.interfaces.performer;

import java.util.List;

public class PerformerResponse {

    /**
     * Chef 조회 응답
     * - 지도에 표시할 모든 정보 포함
     */
    public record ChefInfo(
            Long id,
            String name,
            String nickname,
            String type,

            // Restaurant 정보
            String address,
            String category,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl,

            // Chef 이미지
            List<String> imageUrls,

            // 조회수
            Long viewCount
    ) {}
}
