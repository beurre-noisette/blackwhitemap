package com.blackwhitemap.blackwhitemap_back.interfaces.ranking;

import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingResult;

import java.util.List;

public class RankingResponse {

    /**
     * 주간 Best Chef 응답 DTO
     * - 프론트엔드의 BestChef 타입과 매칭
     */
    public record WeeklyBestChef(
            // Chef 기본 정보
            Long id,
            String name,
            String nickname,
            String type,

            // Restaurant 정보
            String restaurantName,
            String address,
            String smallAddress,
            String closedDays,
            String category,
            String naverReservationUrl,
            String catchTableUrl,

            // Chef 이미지
            List<String> imageUrls,

            // 조회수
            Long viewCount,

            // 랭킹 정보
            Integer rank,
            Long score
    ) {
        public static WeeklyBestChef from(RankingResult.WeeklyBestChef rankingResult) {
            return new WeeklyBestChef(
                    rankingResult.id(),
                    rankingResult.name(),
                    rankingResult.nickname(),
                    rankingResult.type(),
                    rankingResult.restaurantName(),
                    rankingResult.address(),
                    rankingResult.smallAddress(),
                    rankingResult.closedDays(),
                    rankingResult.category(),
                    rankingResult.naverReservationUrl(),
                    rankingResult.catchTableUrl(),
                    rankingResult.imageUrls(),
                    rankingResult.viewCount(),
                    rankingResult.rank(),
                    rankingResult.score()
            );
        }
    }
}
