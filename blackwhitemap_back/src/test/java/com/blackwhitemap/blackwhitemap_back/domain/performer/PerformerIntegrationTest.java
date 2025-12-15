package com.blackwhitemap.blackwhitemap_back.domain.performer;

import com.blackwhitemap.blackwhitemap_back.infrastructure.performer.ChefJpaRepository;
import com.blackwhitemap.blackwhitemap_back.support.testcontainers.PostgreSQLTestContainersConfig;
import com.blackwhitemap.blackwhitemap_back.support.utils.DatabaseCleanUp;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Import(PostgreSQLTestContainersConfig.class)
@Transactional
@DisplayName("Performer 통합 테스트")
class PerformerIntegrationTest {

    @Autowired
    private PerformerService performerService;

    @Autowired
    private ChefJpaRepository chefJpaRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
    }

    @Nested
    @DisplayName("Chef를 등록할 때")
    class RegisterChef {

        @Test
        @DisplayName("백요리사를 이름만으로 등록하면 DB에 저장된다")
        void registerWhiteChef_withNameOnly() {
            // given
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                "손종원",
                null,
                Chef.Type.WHITE,
                null,
                null,
                null,
                null,
                null,
                null
            );

            // when
            performerService.registerChef(command);

            // then
            List<Chef> chefs = chefJpaRepository.findAll();
            assertAll(
                () -> assertThat(chefs).hasSize(1),
                () -> assertThat(chefs.getFirst().getName()).isEqualTo("손종원"),
                () -> assertThat(chefs.getFirst().getType()).isEqualTo(Chef.Type.WHITE),
                () -> assertThat(chefs.getFirst().getViewCount()).isZero()
            );
        }

        @Test
        @DisplayName("흑요리사를 별명만으로 등록하면 DB에 저장된다")
        void registerBlackChef_withNicknameOnly() {
            // given
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                null,
                "나폴리맛피아",
                Chef.Type.BLACK,
                null,
                null,
                null,
                null,
                null,
                null
            );

            // when
            performerService.registerChef(command);

            // then
            List<Chef> chefs = chefJpaRepository.findAll();
            assertAll(
                () -> assertThat(chefs).hasSize(1),
                () -> assertThat(chefs.getFirst().getNickname()).isEqualTo("나폴리맛피아"),
                () -> assertThat(chefs.getFirst().getType()).isEqualTo(Chef.Type.BLACK)
            );
        }

        @Test
        @DisplayName("레스토랑 정보와 함께 등록하면 DB에 저장된다")
        void registerChef_withRestaurant() {
            // given
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                "권성준",
                "나폴리맛피아",
                Chef.Type.BLACK,
                "서울시 강남구",
                Restaurant.Category.ITALIAN,
                "https://naver.com/reservation",
                "https://catchtable.com",
                "https://instagram.com/chef",
                null
            );

            // when
            performerService.registerChef(command);

            // then
            Chef savedChef = chefJpaRepository.findAll().get(0);
            assertAll(
                () -> assertThat(savedChef.getName()).isEqualTo("권성준"),
                () -> assertThat(savedChef.getNickname()).isEqualTo("나폴리맛피아"),
                () -> assertThat(savedChef.getRestaurant()).isNotNull(),
                () -> {
                    Assertions.assertNotNull(savedChef.getRestaurant());
                    assertThat(savedChef.getRestaurant().getAddress()).isEqualTo("서울시 강남구");
                },
                () -> {
                    Assertions.assertNotNull(savedChef.getRestaurant());
                    assertThat(savedChef.getRestaurant().getCategory()).isEqualTo(Restaurant.Category.ITALIAN);
                }
            );
        }

        @Test
        @DisplayName("이미지 URL과 함께 등록하면 DB에 저장된다")
        void registerChef_withImages() {
            // given
            List<String> imageUrls = List.of(
                "https://example.com/image1.jpg",
                "https://example.com/image2.jpg"
            );
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                "손종원",
                null,
                Chef.Type.WHITE,
                null,
                null,
                null,
                null,
                null,
                imageUrls
            );

            // when
            performerService.registerChef(command);

            // then
            Chef savedChef = chefJpaRepository.findAll().getFirst();
            assertAll(
                () -> assertThat(savedChef.getImages()).isNotNull(),
                () -> {
                    Assertions.assertNotNull(savedChef.getImages());
                    assertThat(savedChef.getImages().getImageUrls()).hasSize(2);
                },
                () -> {
                    Assertions.assertNotNull(savedChef.getImages());
                    assertThat(savedChef.getImages().getImageUrls()).containsExactly(
                        "https://example.com/image1.jpg",
                        "https://example.com/image2.jpg"
                    );
                }
            );
        }

        @Test
        @DisplayName("중복된 이미지 URL은 하나로 저장된다")
        void registerChef_withDuplicateImages() {
            // given
            List<String> imageUrls = List.of(
                "https://example.com/image1.jpg",
                "https://example.com/image1.jpg",  // 중복
                "https://example.com/image2.jpg"
            );
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                "손종원",
                null,
                Chef.Type.WHITE,
                null,
                null,
                null,
                null,
                null,
                imageUrls
            );

            // when
            performerService.registerChef(command);

            // then
            Chef savedChef = chefJpaRepository.findAll().getFirst();
            assertAll(
                () -> assertThat(savedChef.getImages().getImageUrls()).hasSize(2),  // 3개 아닌 2개
                () -> assertThat(savedChef.getImages().getImageUrls()).containsExactly(
                    "https://example.com/image1.jpg",
                    "https://example.com/image2.jpg"
                )
            );
        }

        @Test
        @DisplayName("레스토랑 정보와 이미지를 모두 포함하여 등록하면 DB에 저장된다")
        void registerChef_withRestaurantAndImages() {
            // given
            List<String> imageUrls = List.of(
                "https://example.com/image1.jpg",
                "https://example.com/image2.jpg",
                "https://example.com/image3.jpg"
            );
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                "손종원",
                null,
                Chef.Type.WHITE,
                "서울시 용산구",
                Restaurant.Category.KOREAN,
                null,
                null,
                "https://instagram.com/whitechef",
                imageUrls
            );

            // when
            performerService.registerChef(command);

            // then
            Chef savedChef = chefJpaRepository.findAll().getFirst();
            assertAll(
                () -> assertThat(savedChef.getName()).isEqualTo("손종원"),
                () -> assertThat(savedChef.getType()).isEqualTo(Chef.Type.WHITE),
                () -> assertThat(savedChef.getRestaurant()).isNotNull(),
                () -> {
                    Assertions.assertNotNull(savedChef.getRestaurant());
                    assertThat(savedChef.getRestaurant().getAddress()).isEqualTo("서울시 용산구");
                },
                () -> {
                    Assertions.assertNotNull(savedChef.getRestaurant());
                    assertThat(savedChef.getRestaurant().getCategory()).isEqualTo(Restaurant.Category.KOREAN);
                },
                () -> assertThat(savedChef.getImages()).isNotNull(),
                () -> {
                    Assertions.assertNotNull(savedChef.getImages());
                    assertThat(savedChef.getImages().getImageUrls()).hasSize(3);
                }
            );
        }
    }
}
