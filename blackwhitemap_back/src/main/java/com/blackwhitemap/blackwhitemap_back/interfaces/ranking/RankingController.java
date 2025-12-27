package com.blackwhitemap.blackwhitemap_back.interfaces.ranking;

import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingQuery;
import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingResult;
import com.blackwhitemap.blackwhitemap_back.interfaces.ApiResponse;
import com.blackwhitemap.blackwhitemap_back.support.ImageUrlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {

    private final RankingQuery rankingQuery;
    private final ImageUrlConverter imageUrlConverter;

    /**
     * 이번주 Best Chef 조회
     * - 현재 주차 (매주 화요일 시작)의 TOP N Chef 조회
     * - 기본값: TOP 5
     * - 캐시 TTL: 1시간
     *
     * @param limit 조회할 랭킹 개수 (기본값 5)
     * @return 주간 Best Chef 정보 리스트
     */
    @GetMapping("/weekly-best")
    public ApiResponse<List<RankingResponse.WeeklyBestChef>> getWeeklyBestChefs(
            @RequestParam(defaultValue = "5") int limit
    ) {
        List<RankingResult.WeeklyBestChef> queryResults = rankingQuery.getWeeklyBestChefs(limit);

        List<RankingResponse.WeeklyBestChef> response = queryResults.stream()
                .map(result -> RankingResponse.WeeklyBestChef.from(
                        result,
                        imageUrlConverter.toFullUrls(result.imageUrls())
                ))
                .toList();

        return ApiResponse.success(response);
    }
}
