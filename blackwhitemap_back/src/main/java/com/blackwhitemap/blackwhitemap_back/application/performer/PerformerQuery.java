package com.blackwhitemap.blackwhitemap_back.application.performer;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PerformerQuery {

    private final PerformerQueryRepository performerQueryRepository;

    /**
     * Chef 목록 조회 (캐싱 적용)
     * - type에 따라 필터링 (null이면 전체 조회)
     * - address가 있는 Chef만 반환
     *
     * @param criteria 조회 조건
     * @return Chef 정보 리스트
     */
    @Cacheable(
            value = "chefs",
            key = "#criteria.type() != null ? #criteria.type().toUpperCase() : 'ALL'"
    )
    public List<PerformerResult.ChefInfo> getChefs(PerformerCriteria.GetChefs criteria) {
        Chef.Type chefType = "ALL".equalsIgnoreCase(criteria.type())
                ? null
                : Chef.Type.fromNullable(criteria.type());

        return performerQueryRepository.findChefsByType(chefType);
    }

    /**
     * Chef 클러스터 조회 (캐싱 적용)
     * - 시/도별 그룹화된 셰프 통계 정보 반환
     *
     * @return 시/도별 Chef 클러스터 정보 리스트
     */
    @Cacheable(
            value = "chefClusters",
            key = "'ALL'"
    )
    public List<PerformerResult.ChefClusterInfo> getChefClusters() {
        return performerQueryRepository.findChefClusters();
    }

    /**
     * 모든 Chef 간단 정보 조회 (Internal API용)
     * - 캐싱 미적용 (외부 스케줄러에서 사용)
     * - 삭제되지 않은 모든 Chef의 id, name, nickname 반환
     *
     * @return Chef 간단 정보 리스트
     */
    public List<PerformerResult.SimpleChefInfo> getAllChefsSimpleInfo() {
        return performerQueryRepository.findAllChefsSimpleInfo();
    }
}
