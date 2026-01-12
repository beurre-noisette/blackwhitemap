package com.blackwhitemap.blackwhitemap_back.application.ranking;

import com.blackwhitemap.blackwhitemap_back.support.testcontainers.PostgreSQLTestContainersConfig;
import com.blackwhitemap.blackwhitemap_back.support.utils.DatabaseCleanUp;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
                    () -> {
                        Assertions.assertNotNull(results);
                        assertThat(results.getFirst().name()).isEqualTo("유용욱");
                    },
                    () -> {
                        Assertions.assertNotNull(results);
                        assertThat(results.getFirst().rank()).isEqualTo(1);
                    },
                    () -> {
                        Assertions.assertNotNull(results);
                        assertThat(results.get(1).name()).isEqualTo("손종원");
                    },
                    () -> {
                        Assertions.assertNotNull(results);
                        assertThat(results.get(1).rank()).isEqualTo(2);
                    }
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

    @Nested
    @DisplayName("getDailyBestChefs()")
    class GetDailyBestChefs {

        @Test
        @DisplayName("일일 Best Chef 5명을 조회한다 (중복 nickname 제거)")
        @Sql("/sql/ranking-daily-test-data.sql")
        void getDailyBestChefs_filtersDuplicateNicknames() {
            // given & when
            List<RankingResult.DailyBestChef> results = rankingQuery.getDailyBestChefs();

            // then
            assertAll(
                    () -> assertThat(results).hasSize(5),
                    // 손종원(nickname: 요리 천재)이 3명이지만 rank 2인 1명만 포함
                    () -> assertThat(results)
                            .extracting(RankingResult.DailyBestChef::nickname)
                            .containsExactly("바베큐 연구소장", "요리 천재", "컬리넌", "마시마로", null),
                    // rank는 원본 rank 유지 (1, 2, 5, 6, 7)
                    () -> assertThat(results)
                            .extracting(RankingResult.DailyBestChef::rank)
                            .containsExactly(1, 2, 5, 6, 7)
            );
        }

        @Test
        @DisplayName("랭킹 데이터가 없으면 빈 리스트를 반환한다")
        void getDailyBestChefs_withNoData() {
            // given & when
            List<RankingResult.DailyBestChef> results = rankingQuery.getDailyBestChefs();

            // then
            assertThat(results).isEmpty();
        }

        @Test
        @DisplayName("Chef 정보를 포함하여 조회한다")
        @Sql("/sql/ranking-daily-test-data.sql")
        void getDailyBestChefs_includesChefInfo() {
            // given & when
            List<RankingResult.DailyBestChef> results = rankingQuery.getDailyBestChefs();

            // then
            RankingResult.DailyBestChef firstChef = results.getFirst();
            assertAll(
                    () -> assertThat(firstChef.id()).isNotNull(),
                    () -> assertThat(firstChef.name()).isEqualTo("유용욱"),
                    () -> assertThat(firstChef.nickname()).isEqualTo("바베큐 연구소장"),
                    () -> assertThat(firstChef.type()).isEqualTo("BLACK"),
                    () -> assertThat(firstChef.restaurantName()).isEqualTo("유용옥 바베큐 연구소"),
                    () -> assertThat(firstChef.smallAddress()).isEqualTo("용산구"),
                    () -> assertThat(firstChef.category()).isEqualTo("BBQ"),
                    () -> assertThat(firstChef.rank()).isEqualTo(1),
                    () -> assertThat(firstChef.score()).isEqualTo(1000L)
            );
        }

        @Test
        @DisplayName("nickname이 null인 셰프는 name으로 중복 판단한다")
        @Sql("/sql/ranking-daily-test-data.sql")
        void getDailyBestChefs_usesNameWhenNicknameIsNull() {
            // given & when
            List<RankingResult.DailyBestChef> results = rankingQuery.getDailyBestChefs();

            // then
            // 정지선 셰프는 nickname이 null이므로 name으로 중복 판단
            RankingResult.DailyBestChef lastChef = results.get(4);
            assertAll(
                    () -> assertThat(lastChef.name()).isEqualTo("정지선"),
                    () -> assertThat(lastChef.nickname()).isNull(),
                    () -> assertThat(lastChef.rank()).isEqualTo(7)
            );
        }

        @Test
        @DisplayName("가장 높은 rank의 셰프만 포함된다")
        @Sql("/sql/ranking-daily-test-data.sql")
        void getDailyBestChefs_includesHighestRankOnly() {
            // given & when
            List<RankingResult.DailyBestChef> results = rankingQuery.getDailyBestChefs();

            // then
            // 손종원은 rank 2, 3, 4에 있지만 rank 2만 포함
            RankingResult.DailyBestChef sonChef = results.stream()
                    .filter(chef -> "요리 천재".equals(chef.nickname()))
                    .findFirst()
                    .orElseThrow();

            assertAll(
                    () -> assertThat(sonChef.name()).isEqualTo("손종원"),
                    () -> assertThat(sonChef.rank()).isEqualTo(2),
                    () -> assertThat(sonChef.restaurantName()).isEqualTo("라망 시크레 본점")
            );
        }
    }
}
