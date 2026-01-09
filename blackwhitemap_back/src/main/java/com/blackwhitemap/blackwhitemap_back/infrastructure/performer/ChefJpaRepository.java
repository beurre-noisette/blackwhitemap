package com.blackwhitemap.blackwhitemap_back.infrastructure.performer;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChefJpaRepository extends JpaRepository<Chef, Long> {

    List<Chef> findAllByIdIn(List<Long> ids);
}