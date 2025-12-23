package com.blackwhitemap.blackwhitemap_back.interfaces.ranking;

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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(PostgreSQLTestContainersConfig.class)
@DisplayName("Ranking API E2E 테스트")
class RankingApiE2ETest {

    private static final String ENDPOINT_WEEKLY_BEST = "/ranking/weekly-best";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @Autowired
    private org.springframework.cache.CacheManager cacheManager;

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
        // 캐시 클리어 (캐싱된 데이터가 다음 테스트에 영향을 주지 않도록)
        var cache = cacheManager.getCache("weeklyBestChefs");
        if (cache != null) {
            cache.clear();
        }
    }

    @Nested
    @DisplayName("GET /ranking/weekly-best")
    class GetWeeklyBestChefs {

        @Test
        @DisplayName("주간 Best Chef 5명을 조회하고 200 OK를 반환한다")
        @Sql("/sql/ranking-test-data.sql")
        void getWeeklyBestChefs_success() {
            // when
            ResponseEntity<ApiResponse<List<RankingResponse.WeeklyBestChef>>> response = testRestTemplate.exchange(
                    ENDPOINT_WEEKLY_BEST,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isNotNull(),
                    () -> assertThat(response.getBody().meta().result()).isEqualTo(ApiResponse.Metadata.Result.SUCCESS),
                    () -> assertThat(response.getBody().data()).hasSize(5)
            );
        }

        @Test
        @DisplayName("limit 파라미터로 조회 개수를 지정할 수 있다")
        @Sql("/sql/ranking-test-data.sql")
        void getWeeklyBestChefs_withLimitParameter() {
            // when
            ResponseEntity<ApiResponse<List<RankingResponse.WeeklyBestChef>>> response = testRestTemplate.exchange(
                    ENDPOINT_WEEKLY_BEST + "?limit=3",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isNotNull(),
                    () -> assertThat(response.getBody().data()).hasSize(3)
            );
        }

        @Test
        @DisplayName("랭킹 데이터가 없으면 빈 리스트를 반환하고 200 OK를 반환한다")
        void getWeeklyBestChefs_withNoData() {
            // when
            ResponseEntity<ApiResponse<List<RankingResponse.WeeklyBestChef>>> response = testRestTemplate.exchange(
                    ENDPOINT_WEEKLY_BEST,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> assertThat(response.getBody()).isNotNull(),
                    () -> assertThat(response.getBody().meta().result()).isEqualTo(ApiResponse.Metadata.Result.SUCCESS),
                    () -> assertThat(response.getBody().data()).isEmpty()
            );
        }

        @Test
        @DisplayName("응답에 모든 필드 정보를 포함한다")
        @Sql("/sql/ranking-test-data.sql")
        void getWeeklyBestChefs_includesAllFields() {
            // when
            ResponseEntity<ApiResponse<List<RankingResponse.WeeklyBestChef>>> response = testRestTemplate.exchange(
                    ENDPOINT_WEEKLY_BEST + "?limit=1",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).hasSize(1);

            RankingResponse.WeeklyBestChef chef = response.getBody().data().get(0);
            assertAll(
                    () -> assertThat(chef.id()).isNotNull(),
                    () -> assertThat(chef.name()).isEqualTo("유용욱"),
                    () -> assertThat(chef.nickname()).isEqualTo("바베큐 연구소장"),
                    () -> assertThat(chef.type()).isEqualTo("BLACK"),
                    () -> assertThat(chef.restaurantName()).isEqualTo("유용옥 바베큐 연구소"),
                    () -> assertThat(chef.address()).isEqualTo("서울특별시 용산구 한강대로84길 5-7"),
                    () -> assertThat(chef.category()).isEqualTo("BBQ"),
                    () -> assertThat(chef.naverReservationUrl()).isEqualTo("https://naver.com/1"),
                    () -> assertThat(chef.catchTableUrl()).isEqualTo("https://catchtable.com/1"),
                    () -> assertThat(chef.viewCount()).isEqualTo(0L),
                    () -> assertThat(chef.rank()).isEqualTo(1),
                    () -> assertThat(chef.score()).isNull()
            );
        }

        @Test
        @DisplayName("rank 순서대로 정렬되어 반환된다")
        @Sql("/sql/ranking-test-data.sql")
        void getWeeklyBestChefs_orderedByRank() {
            // when
            ResponseEntity<ApiResponse<List<RankingResponse.WeeklyBestChef>>> response = testRestTemplate.exchange(
                    ENDPOINT_WEEKLY_BEST,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data())
                    .extracting(RankingResponse.WeeklyBestChef::rank)
                    .containsExactly(1, 2, 3, 4, 5)
                    .isSorted();
        }

        @Test
        @DisplayName("각 Chef의 이름이 올바르게 매핑된다")
        @Sql("/sql/ranking-test-data.sql")
        void getWeeklyBestChefs_correctChefMapping() {
            // when
            ResponseEntity<ApiResponse<List<RankingResponse.WeeklyBestChef>>> response = testRestTemplate.exchange(
                    ENDPOINT_WEEKLY_BEST,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data())
                    .extracting(RankingResponse.WeeklyBestChef::name)
                    .containsExactly("유용욱", "손종원", "에드워드 리", "김도윤", "정지선");
        }
    }
}
