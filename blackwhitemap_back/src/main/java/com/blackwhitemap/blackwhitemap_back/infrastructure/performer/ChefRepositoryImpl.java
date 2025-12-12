package com.blackwhitemap.blackwhitemap_back.infrastructure.performer;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.performer.ChefRepository;
import com.blackwhitemap.blackwhitemap_back.domain.performer.PerformerCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChefRepositoryImpl implements ChefRepository {

    private final ChefJpaRepository chefJpaRepository;

    @Override
    public void saveChef(PerformerCommand.CreateChef command) {
        Chef chef = Chef.of(command);
        chefJpaRepository.save(chef);
    }
}
