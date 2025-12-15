package com.blackwhitemap.blackwhitemap_back.domain.performer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerformerService {

    private final ChefRepository chefRepository;

    @Transactional
    public void registerChef(PerformerCommand.RegisterChef registerCommand) {
        Chef chef = Chef.of(registerCommand);
        chefRepository.registerChef(chef);
    }
}
