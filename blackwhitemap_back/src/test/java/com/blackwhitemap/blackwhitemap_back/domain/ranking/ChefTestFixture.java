package com.blackwhitemap.blackwhitemap_back.domain.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.performer.PerformerCommand;
import com.blackwhitemap.blackwhitemap_back.domain.performer.Restaurant;

import java.lang.reflect.Field;

public class ChefTestFixture {

    public static Chef createChef(
            Long id,
            String name,
            String nickname,
            Chef.Type type,
            String address
    ) {
        PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                name,
                nickname,
                type,
                "테스트 레스토랑",
                address,
                null,
                null,
                null,
                null,
                address != null ? Restaurant.Category.KOREAN : null,
                null,
                null,
                null,
                null,
                null
        );

        Chef chef = Chef.of(command);

        try {
            Field idField = chef.getClass().getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(chef, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Chef id 설정 실패", e);
        }

        return chef;
    }
}