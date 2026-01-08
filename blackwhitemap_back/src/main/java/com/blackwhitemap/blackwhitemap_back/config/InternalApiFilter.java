package com.blackwhitemap.blackwhitemap_back.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Internal API 인증 필터
 * - /internal/** 경로에 대해 X-Internal-Api-Key 헤더 검증
 * - GitHub Actions 등 외부 스케줄러에서 호출할 때 사용
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InternalApiFilter extends OncePerRequestFilter {

    private static final String INTERNAL_API_PATH_PREFIX = "/internal/";
    private static final String API_KEY_HEADER = "X-Internal-Api-Key";

    @Value("${app.internal-api-key:}")
    private String internalApiKey;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        // /internal/** 경로가 아니면 필터 통과
        if (!requestUri.startsWith(INTERNAL_API_PATH_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Internal API Key가 설정되지 않은 경우
        if (internalApiKey == null || internalApiKey.isBlank()) {
            log.warn("Internal API Key가 설정되지 않았습니다. 요청 URI: {}", requestUri);
            sendUnauthorizedResponse(response, "Internal API Key가 설정되지 않았습니다.");
            return;
        }

        String requestApiKey = request.getHeader(API_KEY_HEADER);

        // API Key 검증
        if (!internalApiKey.equals(requestApiKey)) {
            log.warn("유효하지 않은 Internal API Key. 요청 URI: {}, IP: {}",
                    requestUri, request.getRemoteAddr());
            sendUnauthorizedResponse(response, "유효하지 않은 Internal API Key입니다.");
            return;
        }

        log.info("Internal API 인증 성공. 요청 URI: {}", requestUri);
        filterChain.doFilter(request, response);
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String jsonResponse = String.format(
                "{\"meta\":{\"result\":\"FAIL\",\"message\":\"%s\"},\"data\":null}",
                message
        );
        response.getWriter().write(jsonResponse);
    }
}