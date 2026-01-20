package com.blackwhitemap.blackwhitemap_back.interfaces.internal;

import com.blackwhitemap.blackwhitemap_back.application.cache.CacheFacade;
import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerQuery;
import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerResult;
import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingCriteria;
import com.blackwhitemap.blackwhitemap_back.application.ranking.RankingFacade;
import com.blackwhitemap.blackwhitemap_back.interfaces.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalApiController {

    private final PerformerQuery performerQuery;
    private final RankingFacade rankingFacade;
    private final CacheFacade cacheFacade;

    /**
     * 모든 셰프 간단 정보 조회
     * - 랭킹 수집 스크립트에서 뉴스 검색 키워드로 사용
     *
     * @return 셰프 간단 정보 리스트 (id, name, nickname)
     */
    @GetMapping("/chefs")
    public ApiResponse<List<InternalResponse.SimpleChefInfo>> getAllChefs() {
        List<PerformerResult.SimpleChefInfo> simpleChefInfos = performerQuery.getAllChefsSimpleInfo();

        List<InternalResponse.SimpleChefInfo> response = simpleChefInfos.stream()
                .map(simpleChefInfo -> new InternalResponse.SimpleChefInfo(
                        simpleChefInfo.id(),
                        simpleChefInfo.name(),
                        simpleChefInfo.nickname()
                ))
                .toList();

        return ApiResponse.success(response);
    }

    /**
     * 일간 랭킹 점수 추가 및 순위 재계산
     * - 뉴스 수집 스크립트에서 분석 결과(점수) 저장
     * - 기존 데이터가 있으면 점수 누적, 없으면 생성
     * - 저장 완료 후 해당 날짜의 모든 랭킹 순위 재계산
     */
    @PostMapping("/daily-ranking")
    public ApiResponse<Object> updateDailyRanking(
            @Valid @RequestBody InternalRequest.UpdateDailyRanking request
    ) {
        List<RankingCriteria.RankingEntry> entries = request.rankings().stream()
                .map(entry -> new RankingCriteria.RankingEntry(
                        entry.chefId(),
                        entry.score()
                ))
                .toList();

        RankingCriteria.UpdateDailyRanking updateCriteria = new RankingCriteria.UpdateDailyRanking(
                request.periodStart(),
                entries
        );

        rankingFacade.updateDailyRankings(updateCriteria);

        return ApiResponse.success();
    }

    @DeleteMapping("/caches")
    public ApiResponse<Object> evictAllCaches() {
        cacheFacade.evictAllCaches();

        return ApiResponse.success();
    }

    @DeleteMapping("/caches/{cacheName}")
    public ApiResponse<Object> evictCache(@PathVariable String cacheName) {
        cacheFacade.evictCache(cacheName);

        return ApiResponse.success();
    }
}