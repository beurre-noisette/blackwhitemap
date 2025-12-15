package com.blackwhitemap.blackwhitemap_back.support.utils;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseCleanUp implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private final List<String> tableNames = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        entityManager.getMetamodel().getEntities().stream()
            .filter(entity -> entity.getJavaType().getAnnotation(Entity.class) != null)
            .map(entity -> {
                Table tableAnnotation = entity.getJavaType().getAnnotation(Table.class);
                return tableAnnotation != null ? tableAnnotation.name() : entity.getName().toLowerCase();
            })
            .forEach(tableNames::add);
    }

    @Transactional
    public void truncateAllTables() {
        entityManager.flush();

        // PostgreSQL은 CASCADE로 외래키 제약 무시
        entityManager.createNativeQuery("SET CONSTRAINTS ALL DEFERRED").executeUpdate();

        for (String table : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + table + " RESTART IDENTITY CASCADE").executeUpdate();
        }
    }
}
