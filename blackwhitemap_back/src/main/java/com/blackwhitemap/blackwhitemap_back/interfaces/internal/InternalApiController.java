package com.blackwhitemap.blackwhitemap_back.interfaces.internal;

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
     * 일간 랭킹 갱신
     * - 뉴스 수집 스크립트에서 분석 결과 저장
     * - 기존 데이터가 있으면 갱신, 없으면 생성
     */
    @PostMapping("/daily-ranking")
    public ApiResponse<Object> updateDailyRanking(
            @Valid @RequestBody InternalRequest.UpdateDailyRanking request
    ) {
        List<RankingCriteria.RankingEntry> entries = request.rankings().stream()
                .map(entry -> new RankingCriteria.RankingEntry(
                        entry.chefId(),
                        entry.rank(),
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
}