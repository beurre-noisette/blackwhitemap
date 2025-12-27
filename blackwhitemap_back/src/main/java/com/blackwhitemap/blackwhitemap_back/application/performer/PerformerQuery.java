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
     * - 캐시 TTL: 5분
     * - 캐시 키: type을 대문자로 변환 (null은 "ALL")
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
     * - 캐시 TTL: 5분
     * -캐시 키: "ALL"
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
}
