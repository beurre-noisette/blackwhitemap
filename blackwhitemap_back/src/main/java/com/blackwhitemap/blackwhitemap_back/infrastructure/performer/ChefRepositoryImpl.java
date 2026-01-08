package com.blackwhitemap.blackwhitemap_back.infrastructure.performer;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.performer.ChefRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChefRepositoryImpl implements ChefRepository {

    private final ChefJpaRepository chefJpaRepository;

    @Override
    public void registerChef(Chef chef) {
        chefJpaRepository.save(chef);
    }

    @Override
    public Optional<Chef> findById(Long id) {
        return chefJpaRepository.findById(id);
    }

    @Override
    public List<Chef> findAllByIdIn(List<Long> ids) {
        return chefJpaRepository.findAllByIdIn(ids);
    }
}
