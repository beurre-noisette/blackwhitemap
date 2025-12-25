package com.blackwhitemap.blackwhitemap_back.infrastructure.performer;

import com.blackwhitemap.blackwhitemap_back.application.performer.PerformerResult;
import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.performer.PerformerCommand;
import com.blackwhitemap.blackwhitemap_back.domain.performer.PerformerService;
import com.blackwhitemap.blackwhitemap_back.domain.performer.Restaurant;
import com.blackwhitemap.blackwhitemap_back.support.testcontainers.PostgreSQLTestContainersConfig;
import com.blackwhitemap.blackwhitemap_back.support.utils.DatabaseCleanUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Import(PostgreSQLTestContainersConfig.class)
@DisplayName("PerformerQueryRepository 통합 테스트")
class PerformerQueryRepositoryImplTest {

    @Autowired
    private PerformerQueryRepositoryImpl performerQueryRepository;

    @Autowired
    private PerformerService performerService;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
    }

    @Nested
    @DisplayName("findChefClusters() 메서드는")
    class FindChefClusters {

        @Test
        @DisplayName("시/도별로 흑요리사와 백요리사 수를 정확히 집계한다")
        void aggregatesCorrectly() {
            // given - 서울특별시에 흑2, 백1 / 부산광역시에 흑1, 백2
            registerChef("권성준", Chef.Type.BLACK, "서울특별시 강남구 테헤란로 123");
            registerChef("안유성", Chef.Type.BLACK, "서울특별시 종로구 종로 1");
            registerChef("손종원", Chef.Type.WHITE, "서울특별시 서초구 강남대로 456");

            registerChef("김부산1", Chef.Type.BLACK, "부산광역시 해운대구 해운대로 789");
            registerChef("김부산2", Chef.Type.WHITE, "부산광역시 부산진구 중앙대로 111");
            registerChef("김부산3", Chef.Type.WHITE, "부산광역시 동래구 온천장로 222");

            // when
            List<PerformerResult.ChefClusterInfo> clusters = performerQueryRepository.findChefClusters();

            // then
            assertThat(clusters).hasSize(2);

            PerformerResult.ChefClusterInfo seoulCluster = clusters.stream()
                    .filter(cluster -> cluster.region().equals("서울특별시"))
                    .findFirst()
                    .orElseThrow();

            PerformerResult.ChefClusterInfo busanCluster = clusters.stream()
                    .filter(cluster -> cluster.region().equals("부산광역시"))
                    .findFirst()
                    .orElseThrow();

            assertAll(
                    // 서울 검증
                    () -> assertThat(seoulCluster.region()).isEqualTo("서울특별시"),
                    () -> assertThat(seoulCluster.blackCount()).isEqualTo(2),
                    () -> assertThat(seoulCluster.whiteCount()).isEqualTo(1),
                    () -> assertThat(seoulCluster.latitude()).isEqualTo(37.5665),
                    () -> assertThat(seoulCluster.longitude()).isEqualTo(126.978),

                    // 부산 검증
                    () -> assertThat(busanCluster.region()).isEqualTo("부산광역시"),
                    () -> assertThat(busanCluster.blackCount()).isEqualTo(1),
                    () -> assertThat(busanCluster.whiteCount()).isEqualTo(2),
                    () -> assertThat(busanCluster.latitude()).isEqualTo(35.1796),
                    () -> assertThat(busanCluster.longitude()).isEqualTo(129.0756)
            );
        }

        @Test
        @DisplayName("address가 없는 Chef는 집계에서 제외된다")
        void excludesChefsWithoutAddress() {
            // given - address 있는 Chef 1명, address 없는 Chef 2명
            registerChef("권성준", Chef.Type.BLACK, "서울특별시 강남구 테헤란로 123");
            registerChefWithoutAddress("안유성", Chef.Type.BLACK);
            registerChefWithoutAddress("손종원", Chef.Type.WHITE);

            // when
            List<PerformerResult.ChefClusterInfo> clusters = performerQueryRepository.findChefClusters();

            // then
            assertThat(clusters).hasSize(1);

            PerformerResult.ChefClusterInfo seoulCluster = clusters.getFirst();
            assertAll(
                    () -> assertThat(seoulCluster.region()).isEqualTo("서울특별시"),
                    () -> assertThat(seoulCluster.blackCount()).isEqualTo(1),
                    () -> assertThat(seoulCluster.whiteCount()).isEqualTo(0)
            );
        }

        @Test
        @DisplayName("Region enum에 매칭되지 않는 주소는 집계에서 제외된다")
        void excludesUnknownRegions() {
            // given - 유효한 주소 1개, 잘못된 주소 1개
            registerChef("권성준", Chef.Type.BLACK, "서울특별시 강남구 테헤란로 123");
            registerChef("김철수", Chef.Type.BLACK, "알수없는지역 어딘가 123");

            // when
            List<PerformerResult.ChefClusterInfo> clusters = performerQueryRepository.findChefClusters();

            // then
            assertThat(clusters).hasSize(1);
            assertThat(clusters.getFirst().region()).isEqualTo("서울특별시");
        }

        @Test
        @DisplayName("여러 시/도에 걸쳐 Chef가 있으면 각각 집계된다")
        void aggregatesMultipleRegions() {
            // given - 5개 시/도에 각각 Chef 등록
            registerChef("서울셰프", Chef.Type.BLACK, "서울특별시 강남구 테헤란로 123");
            registerChef("부산셰프", Chef.Type.WHITE, "부산광역시 해운대구 해운대로 456");
            registerChef("경기셰프", Chef.Type.BLACK, "경기도 성남시 분당구 판교역로 789");
            registerChef("인천셰프", Chef.Type.WHITE, "인천광역시 연수구 송도과학로 1");
            registerChef("제주셰프", Chef.Type.BLACK, "제주특별자치도 제주시 첨단로 2");

            // when
            List<PerformerResult.ChefClusterInfo> clusters = performerQueryRepository.findChefClusters();

            // then
            assertThat(clusters).hasSize(5);

            List<String> regionNames = clusters.stream()
                    .map(PerformerResult.ChefClusterInfo::region)
                    .toList();

            assertThat(regionNames).containsExactlyInAnyOrder(
                    "서울특별시",
                    "부산광역시",
                    "경기도",
                    "인천광역시",
                    "제주특별자치도"
            );
        }

        @Test
        @DisplayName("한 지역에 흑요리사만 있으면 백요리사 수는 0이다")
        void whenOnlyBlackChefs_whiteCountIsZero() {
            // given - 서울특별시에 흑요리사만 3명
            registerChef("권성준", Chef.Type.BLACK, "서울특별시 강남구 테헤란로 123");
            registerChef("안유성", Chef.Type.BLACK, "서울특별시 종로구 종로 1");
            registerChef("김철수", Chef.Type.BLACK, "서울특별시 서초구 강남대로 456");

            // when
            List<PerformerResult.ChefClusterInfo> clusters = performerQueryRepository.findChefClusters();

            // then
            assertThat(clusters).hasSize(1);

            PerformerResult.ChefClusterInfo seoulCluster = clusters.getFirst();
            assertAll(
                    () -> assertThat(seoulCluster.region()).isEqualTo("서울특별시"),
                    () -> assertThat(seoulCluster.blackCount()).isEqualTo(3),
                    () -> assertThat(seoulCluster.whiteCount()).isEqualTo(0)
            );
        }

        @Test
        @DisplayName("한 지역에 백요리사만 있으면 흑요리사 수는 0이다")
        void whenOnlyWhiteChefs_blackCountIsZero() {
            // given - 경기도에 백요리사만 2명
            registerChef("손종원", Chef.Type.WHITE, "경기도 성남시 분당구 판교역로 123");
            registerChef("이영희", Chef.Type.WHITE, "경기도 수원시 영통구 광교로 456");

            // when
            List<PerformerResult.ChefClusterInfo> clusters = performerQueryRepository.findChefClusters();

            // then
            assertThat(clusters).hasSize(1);

            PerformerResult.ChefClusterInfo gyeonggiCluster = clusters.getFirst();
            assertAll(
                    () -> assertThat(gyeonggiCluster.region()).isEqualTo("경기도"),
                    () -> assertThat(gyeonggiCluster.blackCount()).isEqualTo(0),
                    () -> assertThat(gyeonggiCluster.whiteCount()).isEqualTo(2)
            );
        }

        @Test
        @DisplayName("클러스터 데이터에 Region의 중심 좌표가 포함된다")
        void includesRegionCoordinates() {
            // given
            registerChef("권성준", Chef.Type.BLACK, "서울특별시 강남구 테헤란로 123");

            // when
            List<PerformerResult.ChefClusterInfo> clusters = performerQueryRepository.findChefClusters();

            // then
            assertThat(clusters).hasSize(1);

            PerformerResult.ChefClusterInfo seoulCluster = clusters.getFirst();
            assertAll(
                    () -> assertThat(seoulCluster.latitude()).isEqualTo(37.5665),
                    () -> assertThat(seoulCluster.longitude()).isEqualTo(126.978)
            );
        }

        @Test
        @DisplayName("Chef가 한 명도 없으면 빈 리스트를 반환한다")
        void whenNoChefs_returnsEmptyList() {
            // given - Chef 등록 안 함

            // when
            List<PerformerResult.ChefClusterInfo> clusters = performerQueryRepository.findChefClusters();

            // then
            assertThat(clusters).isEmpty();
        }

        @Test
        @DisplayName("결과는 지역명으로 정렬되어 반환된다")
        void resultsSortedByRegionName() {
            // given - 여러 시/도에 Chef 등록 (가나다순이 아닌 순서로)
            registerChef("제주셰프", Chef.Type.BLACK, "제주특별자치도 제주시 첨단로 1");
            registerChef("경기셰프", Chef.Type.BLACK, "경기도 성남시 분당구 판교역로 2");
            registerChef("서울셰프", Chef.Type.BLACK, "서울특별시 강남구 테헤란로 3");
            registerChef("부산셰프", Chef.Type.BLACK, "부산광역시 해운대구 해운대로 4");

            // when
            List<PerformerResult.ChefClusterInfo> clusters = performerQueryRepository.findChefClusters();

            // then
            assertThat(clusters).hasSize(4);

            List<String> regionNames = clusters.stream()
                    .map(PerformerResult.ChefClusterInfo::region)
                    .toList();

            // 가나다순 정렬 확인
            assertThat(regionNames).containsExactly(
                    "경기도",
                    "부산광역시",
                    "서울특별시",
                    "제주특별자치도"
            );
        }
    }

    // === Helper Methods ===

    private void registerChef(String name, Chef.Type type, String address) {
        PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                name,
                null,
                type,
                "가게",
                address,
                Restaurant.Category.KOREAN,
                null,
                null,
                null,
                null
        );
        performerService.registerChef(command);
    }

    private void registerChefWithoutAddress(String name, Chef.Type type) {
        PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                name,
                null,
                type,
                "가게",
                null,  // address 없음
                null,
                null,
                null,
                null,
                null
        );
        performerService.registerChef(command);
    }
}