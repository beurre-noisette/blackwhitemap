package com.blackwhitemap.blackwhitemap_back.application.ranking;

import com.blackwhitemap.blackwhitemap_back.support.testcontainers.PostgreSQLTestContainersConfig;
import com.blackwhitemap.blackwhitemap_back.support.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Import(PostgreSQLTestContainersConfig.class)
@DisplayName("RankingQuery 테스트")
class RankingQueryTest {

    @Autowired
    private RankingQuery rankingQuery;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @Autowired
    private CacheManager cacheManager;

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
    }

    @Nested
    @DisplayName("getWeeklyBestChefs()")
    class GetWeeklyBestChefs {

        @Test
        @DisplayName("주간 Best Chef 5명을 조회한다")
        @Sql("/sql/ranking-test-data.sql")
        void getWeeklyBestChefs_withLimit5() {
            // given
            int limit = 5;

            // when
            List<RankingResult.WeeklyBestChef> results = rankingQuery.getWeeklyBestChefs(limit);

            // then
            assertAll(
                    () -> assertThat(results).hasSize(5),
                    () -> assertThat(results)
                            .extracting(RankingResult.WeeklyBestChef::rank)
                            .containsExactly(1, 2, 3, 4, 5),
                    () -> assertThat(results.get(0).name()).isEqualTo("유용욱"),
                    () -> assertThat(results.get(0).rank()).isEqualTo(1),
                    () -> assertThat(results.get(1).name()).isEqualTo("손종원"),
                    () -> assertThat(results.get(1).rank()).isEqualTo(2)
            );
        }

        @Test
        @DisplayName("limit이 3이면 TOP 3만 조회한다")
        @Sql("/sql/ranking-test-data.sql")
        void getWeeklyBestChefs_withLimit3() {
            // given
            int limit = 3;

            // when
            List<RankingResult.WeeklyBestChef> results = rankingQuery.getWeeklyBestChefs(limit);

            // then
            assertAll(
                    () -> assertThat(results).hasSize(3),
                    () -> assertThat(results)
                            .extracting(RankingResult.WeeklyBestChef::rank)
                            .containsExactly(1, 2, 3)
            );
        }

        @Test
        @DisplayName("랭킹 데이터가 없으면 빈 리스트를 반환한다")
        void getWeeklyBestChefs_withNoData() {
            // given
            int limit = 5;

            // when
            List<RankingResult.WeeklyBestChef> results = rankingQuery.getWeeklyBestChefs(limit);

            // then
            assertThat(results).isEmpty();
        }

        @Test
        @DisplayName("Chef 정보를 포함하여 조회한다")
        @Sql("/sql/ranking-test-data.sql")
        void getWeeklyBestChefs_includesChefInfo() {
            // given
            int limit = 1;

            // when
            List<RankingResult.WeeklyBestChef> results = rankingQuery.getWeeklyBestChefs(limit);

            // then
            RankingResult.WeeklyBestChef firstChef = results.get(0);
            assertAll(
                    () -> assertThat(firstChef.id()).isNotNull(),
                    () -> assertThat(firstChef.name()).isEqualTo("유용욱"),
                    () -> assertThat(firstChef.nickname()).isEqualTo("바베큐 연구소장"),
                    () -> assertThat(firstChef.type()).isEqualTo("BLACK"),
                    () -> assertThat(firstChef.restaurantName()).isEqualTo("유용옥 바베큐 연구소"),
                    () -> assertThat(firstChef.address()).isEqualTo("서울특별시 용산구 한강대로84길 5-7"),
                    () -> assertThat(firstChef.category()).isEqualTo("BBQ"),
                    () -> assertThat(firstChef.naverReservationUrl()).isEqualTo("https://naver.com/1"),
                    () -> assertThat(firstChef.catchTableUrl()).isEqualTo("https://catchtable.com/1"),
                    () -> assertThat(firstChef.viewCount()).isEqualTo(0L),
                    () -> assertThat(firstChef.rank()).isEqualTo(1),
                    () -> assertThat(firstChef.score()).isNull()
            );
        }

        @Test
        @DisplayName("rank 순서대로 정렬되어 조회된다")
        @Sql("/sql/ranking-test-data.sql")
        void getWeeklyBestChefs_orderedByRank() {
            // given
            int limit = 5;

            // when
            List<RankingResult.WeeklyBestChef> results = rankingQuery.getWeeklyBestChefs(limit);

            // then
            assertThat(results)
                    .extracting(RankingResult.WeeklyBestChef::rank)
                    .containsExactly(1, 2, 3, 4, 5)
                    .isSorted();
        }
    }
}
