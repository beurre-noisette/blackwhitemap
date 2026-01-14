package com.blackwhitemap.blackwhitemap_back.interfaces.ranking;

import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingCriteria;
import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingFacade;
import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingQuery;
import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingResult;
import com.blackwhitemap.blackwhitemap_back.interfaces.ApiResponse;
import com.blackwhitemap.blackwhitemap_back.support.ImageUrlConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {

    private final RankingQuery rankingQuery;
    private final RankingFacade rankingFacade;
    private final ImageUrlConverter imageUrlConverter;

    /**
     * 이번주 Best Chef 조회
     * - 현재 주차 (매주 화요일 시작)의 TOP N Chef 조회
     * - 기본값: TOP 5
     * - 캐시 TTL: 5분
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

    /**
     * 일일 Best Chef 조회
     */
    @GetMapping("/daily-best")
    public ApiResponse<List<RankingResponse.DailyBestChef>> getDailyBestChefs(){
        List<RankingResult.DailyBestChef> queryResults = rankingQuery.getDailyBestChefs();

        List<RankingResponse.DailyBestChef> response = queryResults.stream()
                .map(RankingResponse.DailyBestChef::from)
                .toList();

        return ApiResponse.success(response);
    }

    /**
     * 주간 랭킹 집계
     * - 가장 최근 완료된 화~월 주기의 DAILY 데이터를 집계하여 WEEKLY로 저장
     * - 월요일: 해당 주 화~월 집계
     * - 화~일: 이전 주 화~월 집계
     *
     * @param request topN 집계할 랭킹 수
     */
    @PostMapping("/weekly-best")
    public ApiResponse<Object> aggregateWeeklyRanking(
            @Valid @RequestBody RankingRequest.AggregateWeekly request
    ) {
        RankingCriteria.AggregateWeekly aggregateCriteria = new RankingCriteria.AggregateWeekly(request.topN());

        rankingFacade.aggregateWeeklyRanking(aggregateCriteria);

        return ApiResponse.success();
    }
}
