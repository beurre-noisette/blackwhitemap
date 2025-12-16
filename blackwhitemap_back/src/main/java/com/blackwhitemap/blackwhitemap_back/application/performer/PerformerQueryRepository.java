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
}
