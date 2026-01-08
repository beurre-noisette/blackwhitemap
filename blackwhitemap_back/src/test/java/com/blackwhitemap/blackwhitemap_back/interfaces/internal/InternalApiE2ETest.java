package com.blackwhitemap.blackwhitemap_back.interfaces.internal;

import com.blackwhitemap.blackwhitemap_back.domain.ranking.ChefRanking;
import com.blackwhitemap.blackwhitemap_back.infrastructure.ranking.ChefRankingJpaRepository;
import com.blackwhitemap.blackwhitemap_back.interfaces.ApiResponse;
import com.blackwhitemap.blackwhitemap_back.support.testcontainers.PostgreSQLTestContainersConfig;
import com.blackwhitemap.blackwhitemap_back.support.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(PostgreSQLTestContainersConfig.class)
@TestPropertySource(properties = "app.internal-api-key=test-api-key-12345")
@DisplayName("Internal API E2E 테스트")
class InternalApiE2ETest {

    private static final String ENDPOINT_GET_CHEFS = "/internal/chefs";
    private static final String ENDPOINT_DAILY_RANKING = "/internal/daily-ranking";
    private static final String TEST_API_KEY = "test-api-key-12345";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @Autowired
    private ChefRankingJpaRepository chefRankingJpaRepository;

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
    }

    private HttpHeaders createHeadersWithApiKey() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Api-Key", TEST_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Nested
    @DisplayName("GET /internal/chefs")
    class GetAllChefs {

        @Test
        @DisplayName("유효한 API Key로 셰프 목록을 조회하면 200 OK를 반환한다")
        @Sql("/sql/ranking-test-data.sql")
        void getAllChefs_withValidApiKey_success() {
            // given
            HttpEntity<Void> request = new HttpEntity<>(createHeadersWithApiKey());

            // when
            ResponseEntity<ApiResponse<List<InternalResponse.SimpleChefInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEFS,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isNotNull(),
                    () -> assertThat(response.getBody().meta().result()).isEqualTo(ApiResponse.Metadata.Result.SUCCESS),
                    () -> assertThat(response.getBody().data()).isNotEmpty()
            );
        }

        @Test
        @DisplayName("API Key가 없으면 401 Unauthorized를 반환한다")
        void getAllChefs_withoutApiKey_unauthorized() {
            // when
            ResponseEntity<String> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEFS,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }

        @Test
        @DisplayName("잘못된 API Key로 요청하면 401 Unauthorized를 반환한다")
        void getAllChefs_withInvalidApiKey_unauthorized() {
            // given
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Internal-Api-Key", "wrong-api-key");
            HttpEntity<Void> request = new HttpEntity<>(headers);

            // when
            ResponseEntity<String> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEFS,
                    HttpMethod.GET,
                    request,
                    String.class
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }

        @Test
        @DisplayName("응답에 id, name, nickname 필드가 포함된다")
        @Sql("/sql/ranking-test-data.sql")
        void getAllChefs_responseContainsRequiredFields() {
            // given
            HttpEntity<Void> request = new HttpEntity<>(createHeadersWithApiKey());

            // when
            ResponseEntity<ApiResponse<List<InternalResponse.SimpleChefInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEFS,
                    HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).isNotEmpty();

            InternalResponse.SimpleChefInfo chef = response.getBody().data().getFirst();
            assertAll(
                    () -> assertThat(chef.id()).isNotNull(),
                    () -> assertThat(chef.name()).isNotNull(),
                    () -> assertThat(chef.nickname()).isNotNull()
            );
        }
    }

    @Nested
    @DisplayName("POST /internal/daily-ranking")
    class UpdateDailyRanking {

        @Test
        @DisplayName("유효한 요청으로 일간 랭킹을 저장하면 200 OK를 반환한다")
        @Sql("/sql/ranking-test-data.sql")
        void updateDailyRanking_withValidRequest_success() {
            // given
            InternalRequest.UpdateDailyRanking requestBody = new InternalRequest.UpdateDailyRanking(
                    LocalDate.of(2025, 1, 6),
                    List.of(
                            new InternalRequest.RankingEntry(101L, 1, 100L),
                            new InternalRequest.RankingEntry(102L, 2, 80L),
                            new InternalRequest.RankingEntry(103L, 3, 60L)
                    )
            );
            HttpEntity<InternalRequest.UpdateDailyRanking> request = new HttpEntity<>(requestBody, createHeadersWithApiKey());

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_DAILY_RANKING,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isNotNull(),
                    () -> assertThat(response.getBody().meta().result()).isEqualTo(ApiResponse.Metadata.Result.SUCCESS)
            );

            // DB에 저장되었는지 확인
            List<ChefRanking> savedRankings = chefRankingJpaRepository.findAll().stream()
                    .filter(chefRanking -> chefRanking.getType() == ChefRanking.Type.DAILY)
                    .toList();
            assertThat(savedRankings).hasSize(3);
        }

        @Test
        @DisplayName("API Key가 없으면 401 Unauthorized를 반환한다")
        void updateDailyRanking_withoutApiKey_unauthorized() {
            // given
            InternalRequest.UpdateDailyRanking requestBody = new InternalRequest.UpdateDailyRanking(
                    LocalDate.of(2025, 1, 6),
                    List.of(new InternalRequest.RankingEntry(101L, 1, 100L))
            );
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<InternalRequest.UpdateDailyRanking> request = new HttpEntity<>(requestBody, headers);

            // when
            ResponseEntity<String> response = testRestTemplate.exchange(
                    ENDPOINT_DAILY_RANKING,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }

        @Test
        @DisplayName("같은 날짜에 기존 랭킹이 있으면 갱신한다")
        @Sql("/sql/ranking-test-data.sql")
        void updateDailyRanking_updateExistingRanking() {
            // given - 먼저 랭킹 저장
            LocalDate targetDate = LocalDate.of(2025, 1, 6);
            InternalRequest.UpdateDailyRanking firstRequest = new InternalRequest.UpdateDailyRanking(
                    targetDate,
                    List.of(new InternalRequest.RankingEntry(101L, 1, 100L))
            );
            HttpEntity<InternalRequest.UpdateDailyRanking> request1 = new HttpEntity<>(firstRequest, createHeadersWithApiKey());
            testRestTemplate.exchange(ENDPOINT_DAILY_RANKING, HttpMethod.POST, request1, new ParameterizedTypeReference<ApiResponse<Object>>() {});

            // when - 같은 날짜, 같은 셰프로 다시 요청
            InternalRequest.UpdateDailyRanking secondRequest = new InternalRequest.UpdateDailyRanking(
                    targetDate,
                    List.of(new InternalRequest.RankingEntry(101L, 2, 150L))
            );
            HttpEntity<InternalRequest.UpdateDailyRanking> request2 = new HttpEntity<>(secondRequest, createHeadersWithApiKey());
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_DAILY_RANKING,
                    HttpMethod.POST,
                    request2,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            // 같은 날짜, 같은 셰프의 랭킹이 갱신되었는지 확인
            List<ChefRanking> rankings = chefRankingJpaRepository.findAll().stream()
                    .filter(r -> r.getType() == ChefRanking.Type.DAILY
                            && r.getPeriodStart().equals(targetDate)
                            && r.getChefId().equals(101L))
                    .toList();

            assertThat(rankings).hasSize(1);
            assertThat(rankings.getFirst().getRank()).isEqualTo(2);
            assertThat(rankings.getFirst().getScore()).isEqualTo(150L);
        }

        @Test
        @DisplayName("빈 랭킹 리스트로 요청하면 400 Bad Request를 반환한다")
        void updateDailyRanking_withEmptyRankings_badRequest() {
            // given
            InternalRequest.UpdateDailyRanking requestBody = new InternalRequest.UpdateDailyRanking(
                    LocalDate.of(2025, 1, 6),
                    List.of()
            );
            HttpEntity<InternalRequest.UpdateDailyRanking> request = new HttpEntity<>(requestBody, createHeadersWithApiKey());

            // when
            ResponseEntity<String> response = testRestTemplate.exchange(
                    ENDPOINT_DAILY_RANKING,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }
}