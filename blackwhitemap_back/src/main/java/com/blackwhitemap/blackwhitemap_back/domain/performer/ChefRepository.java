package com.blackwhitemap.blackwhitemap_back.domain.performer;

public interface ChefRepository {

    void saveChef(PerformerCommand.CreateChef command);
}
