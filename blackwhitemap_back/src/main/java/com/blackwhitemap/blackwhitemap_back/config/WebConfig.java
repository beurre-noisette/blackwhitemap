package com.blackwhitemap.blackwhitemap_back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * CORS 허용 출처 목록
     * - 환경 변수 ALLOWED_ORIGINS에서 쉼표로 구분된 값을 읽음
     * - 기본값: localhost:5173 (개발 환경)
     */
    @Value("${app.cors.allowed-origins:http://localhost:5173}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "PATCH"
                )
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}