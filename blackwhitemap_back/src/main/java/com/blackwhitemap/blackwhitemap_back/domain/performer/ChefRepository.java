package com.blackwhitemap.blackwhitemap_back.domain.performer;

import java.util.Optional;

public interface ChefRepository {

    void registerChef(Chef chef);

    Optional<Chef> findById(Long id);
}
