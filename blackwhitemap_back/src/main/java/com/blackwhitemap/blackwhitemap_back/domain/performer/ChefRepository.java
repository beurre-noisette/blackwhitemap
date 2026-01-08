package com.blackwhitemap.blackwhitemap_back.domain.performer;

import java.util.List;
import java.util.Optional;

public interface ChefRepository {

    void registerChef(Chef chef);

    Optional<Chef> findById(Long id);

    List<Chef> findAllByIdIn(List<Long> ids);
}
