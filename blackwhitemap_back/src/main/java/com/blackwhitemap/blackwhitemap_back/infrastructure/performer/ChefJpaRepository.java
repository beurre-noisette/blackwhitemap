package com.blackwhitemap.blackwhitemap_back.infrastructure.performer;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChefJpaRepository extends JpaRepository<Chef, Long> {
}