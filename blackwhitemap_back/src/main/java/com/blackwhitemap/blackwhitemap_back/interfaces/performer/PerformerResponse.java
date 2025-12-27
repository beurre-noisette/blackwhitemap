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
            String restaurantName,
            String address,
            String smallAddress,
            Double latitude,
            Double longitude,
            String closedDays,
            String category,
            String naverReservationUrl,
            String catchTableUrl,
            String instagramUrl,

            // Chef 이미지
            List<String> imageUrls,

            // 조회수
            Long viewCount
    ) {}

    /**
     * 지도 클러스터 조회 응답
     * - 시/도별로 그룹화된 셰프 통계 정보
     * - 지도 줌 아웃 시 클러스터 마커에 사용
     */
    public record ChefClusterInfo(
            String region,
            Integer blackCount,
            Integer whiteCount,
            Double latitude,
            Double longitude
    ) {}
}
