package com.blackwhitemap.blackwhitemap_back.infrastructure.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.ranking.ChefRanking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChefRankingJpaRepository extends JpaRepository<ChefRanking, Long> {
}
