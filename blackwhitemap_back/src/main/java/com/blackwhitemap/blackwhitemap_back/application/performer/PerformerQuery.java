package com.blackwhitemap.blackwhitemap_back.application.performer;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PerformerQuery {

    private final PerformerQueryRepository performerQueryRepository;

    /**
     * Chef 목록 조회
     * - type에 따라 필터링 (null이면 전체 조회)
     * - address가 있는 Chef만 반환
     *
     * @param criteria 조회 조건
     * @return Chef 정보 리스트
     */
    public List<PerformerResult.ChefInfo> getChefs(PerformerCriteria.GetChefs criteria) {
        Chef.Type chefType = "ALL".equalsIgnoreCase(criteria.type())
                ? null
                : Chef.Type.fromNullable(criteria.type());

        return performerQueryRepository.findChefsByType(chefType);
    }
}
