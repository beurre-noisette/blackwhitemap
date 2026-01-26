package com.blackwhitemap.blackwhitemap_back.domain.performer;

import com.blackwhitemap.blackwhitemap_back.infrastructure.performer.ChefJpaRepository;
import com.blackwhitemap.blackwhitemap_back.support.error.CoreException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                null,
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
                null,
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
                "비아톨레도 파스타바",
                "서울시 강남구",
                "강남",
                36.123456,
                127.654321,
                "월, 일",
                Restaurant.Category.ITALIAN,
                "https://naver.com/reservation",
                "https://catchtable.com",
                "https://instagram.com/chef",
                null,
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
                assertThat(savedChef.getRestaurant().getSmallAddress()).isEqualTo("강남");
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
                "라망 시크레",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                imageUrls,
                null
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
                "라망 시크레",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                imageUrls,
                null
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
                    "라망 시크레",
                    "서울시 용산구",
                    "용산",
                    36.123456,
                    127.654321,
                    "월, 일",
                    Restaurant.Category.KOREAN,
                    null,
                    null,
                    "https://instagram.com/whitechef",
                    imageUrls,
                    null
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

    @Nested
    @DisplayName("Chef를 수정할 때")
    class UpdateChef {

        @Test
        @DisplayName("이름만 수정하면 DB에 반영된다")
        void updateChef_nameOnly() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    "요리천재",
                    Chef.Type.WHITE,
                    "라망 시크레",
                    null,
                    null,
                    36.123456,
                    127.654321,
                    "월, 일",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            performerService.registerChef(registerCommand);
            Chef savedChef = chefJpaRepository.findAll().getFirst();
            Long chefId = savedChef.getId();

            PerformerCommand.UpdateChef updateCommand = new PerformerCommand.UpdateChef(
                    chefId,
                    "안유성",  // 이름 변경
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // when
            performerService.updateChef(updateCommand);

            // then
            Chef updatedChef = chefJpaRepository.findById(chefId).orElseThrow();
            assertAll(
                    () -> assertThat(updatedChef.getName()).isEqualTo("안유성"),
                    () -> assertThat(updatedChef.getNickname()).isEqualTo("요리천재"),  // 변경 안 됨
                    () -> assertThat(updatedChef.getType()).isEqualTo(Chef.Type.WHITE)  // 변경 안 됨
            );
        }

        @Test
        @DisplayName("별명만 수정하면 DB에 반영된다")
        void updateChef_nicknameOnly() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "권성준",
                    "나폴리맛피아",
                    Chef.Type.BLACK,
                    "비아톨레도 파스타바",
                    null,
                    null,
                    36.123456,
                    127.654321,
                    "월, 일",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            performerService.registerChef(registerCommand);
            Chef savedChef = chefJpaRepository.findAll().getFirst();
            Long chefId = savedChef.getId();

            PerformerCommand.UpdateChef updateCommand = new PerformerCommand.UpdateChef(
                    chefId,
                    null,
                    "이탈리안마스터",  // 별명 변경
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // when
            performerService.updateChef(updateCommand);

            // then
            Chef updatedChef = chefJpaRepository.findById(chefId).orElseThrow();
            assertAll(
                    () -> assertThat(updatedChef.getName()).isEqualTo("권성준"),
                    () -> assertThat(updatedChef.getNickname()).isEqualTo("이탈리안마스터")
            );
        }

        @Test
        @DisplayName("타입을 수정하면 DB에 반영된다")
        void updateChef_type() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    "요리천재",
                    Chef.Type.BLACK,
                    "라망 시크레",
                    null,
                    null,
                    36.123456,
                    127.654321,
                    "월, 일",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            performerService.registerChef(registerCommand);
            Chef savedChef = chefJpaRepository.findAll().getFirst();
            Long chefId = savedChef.getId();

            PerformerCommand.UpdateChef updateCommand = new PerformerCommand.UpdateChef(
                    chefId,
                    null,
                    null,
                    Chef.Type.WHITE,  // 타입 변경
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // when
            performerService.updateChef(updateCommand);

            // then
            Chef updatedChef = chefJpaRepository.findById(chefId).orElseThrow();
            assertThat(updatedChef.getType()).isEqualTo(Chef.Type.WHITE);
        }

        @Test
        @DisplayName("레스토랑 정보를 모두 수정하면 DB에 반영된다")
        void updateChef_restaurantAllFields() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    "요리천재",
                    Chef.Type.WHITE,
                    "라망 시크레",
                    null,
                    null,
                    36.123456,
                    127.654321,
                    "월, 일",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            performerService.registerChef(registerCommand);
            Chef savedChef = chefJpaRepository.findAll().getFirst();
            Long chefId = savedChef.getId();

            PerformerCommand.UpdateChef updateCommand = new PerformerCommand.UpdateChef(
                    chefId,
                    null,
                    null,
                    null,
                    "다른 이름",
                    "서울시 강남구",
                    "강남",
                    12.123,
                    999.999,
                    "화, 수",
                    Restaurant.Category.KOREAN,
                    "https://naver.com/reservation",
                    "https://catchtable.com",
                    "https://instagram.com/chef",
                    null
            );

            // when
            performerService.updateChef(updateCommand);

            // then
            Chef updatedChef = chefJpaRepository.findById(chefId).orElseThrow();
            assertAll(
                    () -> assertThat(updatedChef.getRestaurant()).isNotNull(),
                    () -> {
                        Assertions.assertNotNull(updatedChef.getRestaurant());
                        assertThat(updatedChef.getRestaurant().getAddress()).isEqualTo("서울시 강남구");
                    },
                    () -> {
                        Assertions.assertNotNull(updatedChef.getRestaurant());
                        assertThat(updatedChef.getRestaurant().getSmallAddress()).isEqualTo("강남");
                    },
                    () -> {
                        Assertions.assertNotNull(updatedChef.getRestaurant());
                        assertThat(updatedChef.getRestaurant().getLatitude()).isEqualTo(12.123);
                    },
                    () -> {
                        Assertions.assertNotNull(updatedChef.getRestaurant());
                        assertThat(updatedChef.getRestaurant().getLongitude()).isEqualTo(999.999);
                    },
                    () -> {
                        Assertions.assertNotNull(updatedChef.getRestaurant());
                        assertThat(updatedChef.getRestaurant().getClosedDays()).isEqualTo("화, 수");
                    },
                    () -> {
                        Assertions.assertNotNull(updatedChef.getRestaurant());
                        assertThat(updatedChef.getRestaurant().getCategory()).isEqualTo(Restaurant.Category.KOREAN);
                    },
                    () -> {
                        Assertions.assertNotNull(updatedChef.getRestaurant());
                        assertThat(updatedChef.getRestaurant().getNaverReservationUrl()).isEqualTo("https://naver.com/reservation");
                    },
                    () -> {
                        Assertions.assertNotNull(updatedChef.getRestaurant());
                        assertThat(updatedChef.getRestaurant().getCatchTableUrl()).isEqualTo("https://catchtable.com");
                    },
                    () -> {
                        Assertions.assertNotNull(updatedChef.getRestaurant());
                        assertThat(updatedChef.getRestaurant().getInstagramUrl()).isEqualTo("https://instagram.com/chef");
                    }
            );
        }

        @Test
        @DisplayName("레스토랑 정보를 일부만 수정하면 DB에 반영된다")
        void updateChef_restaurantPartialFields() {
            // given
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                    "손종원",
                    null,
                    Chef.Type.WHITE,
                    "라망 시크레",
                    "서울시 강남구",
                    "강남",
                    36.123456,
                    127.654321,
                    "월, 일",
                    Restaurant.Category.KOREAN,
                    "https://naver.com",
                    "https://catchtable.com",
                    "https://instagram.com",
                    null,
                    null
            );
            performerService.registerChef(command);
            Chef savedChef = chefJpaRepository.findAll().getFirst();
            Long chefId = savedChef.getId();

            PerformerCommand.UpdateChef updateCommand = new PerformerCommand.UpdateChef(
                    chefId,
                    null,
                    null,
                    null,
                    "라망 시크레",
                    "부산시 해운대구",  // 주소만 변경
                    null,
                    null,
                    null,
                    null,
                    Restaurant.Category.JAPANESE,  // 카테고리만 변경
                    null,
                    null,
                    null,
                    null
            );

            // when
            performerService.updateChef(updateCommand);

            // then
            Chef updatedChef = chefJpaRepository.findById(chefId).orElseThrow();
            assertAll(
                    () -> assertThat(updatedChef.getRestaurant()).isNotNull(),
                    () -> {
                        Assertions.assertNotNull(updatedChef.getRestaurant());
                        assertThat(updatedChef.getRestaurant().getAddress()).isEqualTo("부산시 해운대구");
                    },
                    () -> {
                        Assertions.assertNotNull(updatedChef.getRestaurant());
                        assertThat(updatedChef.getRestaurant().getCategory()).isEqualTo(Restaurant.Category.JAPANESE);
                    }
            );
        }

        @Test
        @DisplayName("이미지를 수정하면 DB에 반영된다")
        void updateChef_images() {
            // given
            List<String> images = List.of(
                    "https://example.com/old1.jpg",
                    "https://example.com/old2.jpg"
            );
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                    "손종원",
                    null,
                    Chef.Type.WHITE,
                    "라망 시크레",
                    null,
                    null,
                    36.123456,
                    127.654321,
                    "월, 일",
                    null,
                    null,
                    null,
                    null,
                    images,
                    null
            );
            performerService.registerChef(command);
            Chef savedChef = chefJpaRepository.findAll().getFirst();
            Long chefId = savedChef.getId();

            List<String> newImages = List.of(
                    "https://example.com/new1.jpg",
                    "https://example.com/new2.jpg"
            );
            PerformerCommand.UpdateChef updateCommand = new PerformerCommand.UpdateChef(
                    chefId,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    newImages
            );

            // when
            performerService.updateChef(updateCommand);

            // then
            Chef updatedChef = chefJpaRepository.findById(chefId).orElseThrow();
            assertAll(
                    () -> assertThat(updatedChef.getImages()).isNotNull(),
                    () -> {
                        Assertions.assertNotNull(updatedChef.getImages());
                        assertThat(updatedChef.getImages().getImageUrls()).hasSize(2);
                    },
                    () -> {
                        Assertions.assertNotNull(updatedChef.getImages());
                        assertThat(updatedChef.getImages().getImageUrls()).containsExactly(
                                "https://example.com/new1.jpg",
                                "https://example.com/new2.jpg"
                        );
                    }
            );
        }

        @Test
        @DisplayName("여러 필드를 동시에 수정하면 DB에 반영된다")
        void updateChef_multipleFields() {
            // given
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                    "손종원",
                    null,
                    Chef.Type.BLACK,
                    "라망 시크레",
                    "서울시 강남구",
                    "강남",
                    36.123456,
                    127.654321,
                    "월, 일",
                    Restaurant.Category.KOREAN,
                    "https://naver.com",
                    "https://catchtable.com",
                    "https://instagram.com",
                    null,
                    null
            );
            performerService.registerChef(command);
            Chef savedChef = chefJpaRepository.findAll().getFirst();
            Long chefId = savedChef.getId();

            List<String> newImages = List.of("https://example.com/image1.jpg");
            PerformerCommand.UpdateChef updateCommand = new PerformerCommand.UpdateChef(
                    chefId,
                    "새이름",  // 이름 변경
                    "새별명",  // 별명 변경
                    Chef.Type.WHITE,  // 타입 변경
                    "새가게", // 가게 이름 변경
                    "새주소",  // 주소 변경
                    "새미니주소",
                    12.12,
                    99.99,
                    "화, 수",  // 휴무일 변경
                    Restaurant.Category.CAFE,  // 카테고리 변경
                    null,
                    null,
                    null,
                    newImages  // 이미지 추가
            );

            // when
            performerService.updateChef(updateCommand);

            // then
            Chef updatedChef = chefJpaRepository.findById(chefId).orElseThrow();
            assertAll(
                    () -> assertThat(updatedChef.getName()).isEqualTo("새이름"),
                    () -> assertThat(updatedChef.getNickname()).isEqualTo("새별명"),
                    () -> assertThat(updatedChef.getType()).isEqualTo(Chef.Type.WHITE),
                    () -> assertThat(updatedChef.getRestaurant().getName()).isEqualTo("새가게"),
                    () -> assertThat(updatedChef.getRestaurant().getAddress()).isEqualTo("새주소"),
                    () -> assertThat(updatedChef.getRestaurant().getSmallAddress()).isEqualTo("새미니주소"),
                    () -> assertThat(updatedChef.getRestaurant().getLatitude()).isEqualTo(12.12),
                    () -> assertThat(updatedChef.getRestaurant().getLongitude()).isEqualTo(99.99),
                    () -> assertThat(updatedChef.getRestaurant().getClosedDays()).isEqualTo("화, 수"),
                    () -> assertThat(updatedChef.getRestaurant().getCategory()).isEqualTo(Restaurant.Category.CAFE),
                    () -> assertThat(updatedChef.getImages().getImageUrls()).hasSize(1)
            );
        }

        @Test
        @DisplayName("존재하지 않는 Chef ID로 수정하면 예외가 발생한다")
        void updateChef_notFound() {
            // given
            Long nonExistentId = 99999L;
            PerformerCommand.UpdateChef updateCommand = new PerformerCommand.UpdateChef(
                    nonExistentId,
                    "새이름",
                    null,
                    null,
                    null,
                    null,
                    null,
                    36.123456,
                    127.654321,
                    "월, 일",
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // when & then
            CoreException exception =
                    assertThrows(CoreException.class,
                            () -> performerService.updateChef(updateCommand));

            assertThat(exception.getCustomMessage()).contains("셰프를 찾을 수 없습니다");
        }
    }
}
