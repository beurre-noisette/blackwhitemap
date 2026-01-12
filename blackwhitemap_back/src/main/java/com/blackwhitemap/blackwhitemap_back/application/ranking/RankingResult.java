package com.blackwhitemap.blackwhitemap_back.application.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.ranking.ChefRanking;

import java.util.List;

public class RankingResult {

    /**
     * 주간 Best Chef 조회 결과
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
        public static WeeklyBestChef from(
                ChefRanking ranking,
                Chef chef
        ) {
            return new WeeklyBestChef(
                    chef.getId(),
                    chef.getName(),
                    chef.getNickname(),
                    chef.getType() != null ? chef.getType().name() : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getName() : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getAddress() : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getSmallAddress() : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getClosedDays() : null,
                    chef.getRestaurant() != null && chef.getRestaurant().getCategory() != null
                            ? chef.getRestaurant().getCategory().name()
                            : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getNaverReservationUrl() : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getCatchTableUrl() : null,
                    chef.getImages() != null ? chef.getImages().getImageUrls() : List.of(),
                    chef.getViewCount(),
                    ranking.getRank(),
                    ranking.getScore()
            );
        }
    }

    public record DailyBestChef(
            // Chef 기본 정보
            Long id,
            String name,
            String nickname,
            String type,

            // Restaurant 정보
            String restaurantName,
            String smallAddress,
            String category,
            String naverReservationUrl,
            String catchTableUrl,

            // 랭킹 정보
            Integer rank,
            Long score
    ) {
        public static DailyBestChef from(
                ChefRanking ranking,
                Chef chef
        ) {
            return new DailyBestChef(
                    chef.getId(),
                    chef.getName(),
                    chef.getNickname(),
                    chef.getType() != null ? chef.getType().name() : null,

                    chef.getRestaurant() != null ? chef.getRestaurant().getName() : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getSmallAddress() : null,
                    chef.getRestaurant() != null && chef.getRestaurant().getCategory() != null
                            ? chef.getRestaurant().getCategory().name()
                            : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getNaverReservationUrl() : null,
                    chef.getRestaurant() != null ? chef.getRestaurant().getCatchTableUrl() : null,

                    ranking.getRank(),
                    ranking.getScore()
            );
        }
    }
}
