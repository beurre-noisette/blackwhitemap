package com.blackwhitemap.blackwhitemap_back.application.performer;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;

import java.util.List;

/**
 * Performer 도메인 조회 전용 Repository
 * - 구현체는 Infrastructure 계층에 위치 (PerformerQueryRepositoryImpl)
 */
public interface PerformerQueryRepository {

    /**
     * Chef 타입별 목록 조회
     * - type이 null이면 전체 조회
     * - address가 있는 Chef만 반환
     *
     * @param type Chef 타입 (null 가능)
     * @return Chef 정보 리스트
     */
    List<PerformerResult.ChefInfo> findChefsByType(Chef.Type type);

    /**
     * Chef 클러스터 조회
     * - 시/도별로 그룹화된 셰프 통계 정보 반환
     * - address가 있는 Chef만 포함
     * - Region enum으로 변환 가능한 주소만 포함
     *
     * @return 시/도별 Chef 클러스터 정보 리스트
     */
    List<PerformerResult.ChefClusterInfo> findChefClusters();

    /**
     * 모든 Chef 간단 정보 조회 (Internal API용)
     * - 삭제되지 않은 모든 Chef의 id, name, nickname 반환
     * - address 유무와 관계없이 전체 조회
     *
     * @return Chef 간단 정보 리스트
     */
    List<PerformerResult.SimpleChefInfo> findAllChefsSimpleInfo();
}
