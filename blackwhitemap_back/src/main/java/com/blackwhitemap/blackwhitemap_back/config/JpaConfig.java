package com.blackwhitemap.blackwhitemap_back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.blackwhitemap.blackwhitemap_back.infrastructure")
public class JpaConfig {
}
