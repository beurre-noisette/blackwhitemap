package com.blackwhitemap.blackwhitemap_back.interfaces.performer;

import com.blackwhitemap.blackwhitemap_back.infrastructure.performer.ChefJpaRepository;
import com.blackwhitemap.blackwhitemap_back.interfaces.ApiResponse;
import com.blackwhitemap.blackwhitemap_back.support.testcontainers.PostgreSQLTestContainersConfig;
import com.blackwhitemap.blackwhitemap_back.support.utils.DatabaseCleanUp;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(PostgreSQLTestContainersConfig.class)
@DisplayName("Performer API E2E 테스트")
class PerformerApiE2ETest {

    private static final String ENDPOINT_REGISTER_CHEF = "/performer/chef";
    private static final String ENDPOINT_UPDATE_CHEF_INFO = "/performer/chef/";
    private static final String ENDPOINT_GET_CHEFS = "/performer/chefs";
    private static final String ENDPOINT_GET_CHEF_CLUSTERS = "/performer/chefs/cluster";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ChefJpaRepository chefJpaRepository;

    @Autowired
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void tearDown() {
        databaseCleanUp.truncateAllTables();
    }

    @Nested
    @DisplayName("POST /performer/chef")
    class RegisterChef {

        @Test
        @DisplayName("유효한 요청이 주어지면 Chef를 등록하고 200 OK를 반환한다")
        void registerChef_withValidRequest() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                "손종원",
                null,
                "WHITE",
                null,
                null,
                null,
                null,
                null,
                null,
                null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                ENDPOINT_REGISTER_CHEF,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> {
                    Assertions.assertNotNull(response.getBody());
                    assertThat(response.getBody().meta().result()).isEqualTo(ApiResponse.Metadata.Result.SUCCESS);
                },
                () -> assertThat(chefJpaRepository.findAll()).hasSize(1)
            );
        }

        @Test
        @DisplayName("레스토랑 정보와 이미지를 포함한 요청이 주어지면 모두 저장된다")
        void registerChef_withRestaurantAndImages() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                "권성준",
                "나폴리맛피아",
                "BLACK",
                "비아톨레도 파스타바",
                "서울시 강남구",
                "ITALIAN",
                "https://naver.com/reservation",
                "https://catchtable.com",
                "https://instagram.com/chef",
                List.of("https://example.com/image1.jpg", "https://example.com/image2.jpg")
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                ENDPOINT_REGISTER_CHEF,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(chefJpaRepository.findAll()).hasSize(1);
        }

        @Test
        @DisplayName("chefType이 null이면 400 BAD_REQUEST를 반환한다")
        void throwsBadRequest_whenChefTypeIsNull() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    null,  // chefType null
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                ENDPOINT_REGISTER_CHEF,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(chefJpaRepository.findAll()).isEmpty()
            );
        }

        @Test
        @DisplayName("chefType이 빈 문자열이면 400 BAD_REQUEST를 반환한다")
        void throwsBadRequest_whenChefTypeIsEmpty() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "",  // 빈 문자열
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                ENDPOINT_REGISTER_CHEF,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(chefJpaRepository.findAll()).isEmpty()
            );
        }

        @Test
        @DisplayName("잘못된 chefType 값이 주어지면 400 BAD_REQUEST를 반환한다")
        void throwsBadRequest_whenInvalidChefType() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "YELLOW",  // 잘못된 값
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                ENDPOINT_REGISTER_CHEF,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(chefJpaRepository.findAll()).isEmpty()
            );
        }

        @Test
        @DisplayName("소문자 chefType 'black'도 정상적으로 등록된다")
        void registerChef_withLowercaseChefType() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                    "권성준",
                    null,
                    "black",  // 소문자
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                ENDPOINT_REGISTER_CHEF,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(chefJpaRepository.findAll()).hasSize(1)
            );
        }

        @Test
        @DisplayName("이름이 5자를 초과하면 400 BAD_REQUEST를 반환한다")
        void throwsBadRequest_whenNameExceedsMaxLength() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                "손종원입니다",  // 6자
                null,
                "WHITE",
                null,
                null,
                null,
                null,
                null,
                null,
                null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                ENDPOINT_REGISTER_CHEF,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("별명이 15자를 초과하면 400 BAD_REQUEST를 반환한다")
        void throwsBadRequest_whenNicknameExceedsMaxLength() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                null,
                "나폴리맛피아입니다만최고입니다요",  // 16자
                "BLACK",
                null,
                null,
                null,
                null,
                null,
                null,
                null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                ENDPOINT_REGISTER_CHEF,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("잘못된 URL 형식이 주어지면 400 BAD_REQUEST를 반환한다")
        void throwsBadRequest_whenInvalidUrlFormat() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                "손종원",
                null,
                "WHITE",
                null,
                null,
                null,
                "not-a-url",  // 잘못된 URL
                null,
                null,
                null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                ENDPOINT_REGISTER_CHEF,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("이미지 URL이 잘못된 형식이면 400 BAD_REQUEST를 반환한다")
        void throwsBadRequest_whenInvalidImageUrlFormat() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                "손종원",
                null,
                "WHITE",
                null,
                null,
                null,
                null,
                null,
                null,
                List.of("not-a-url", "12345")  // 잘못된 URL
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                ENDPOINT_REGISTER_CHEF,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("잘못된 restaurantCategory 값이 주어지면 400 BAD_REQUEST를 반환한다")
        void throwsBadRequest_whenInvalidRestaurantCategory() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                "손종원",
                null,
                "WHITE",
                "라망 시크레",
                "서울시 강남구",
                "MEXICAN",  // 존재하지 않는 카테고리
                null,
                null,
                null,
                null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                ENDPOINT_REGISTER_CHEF,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(chefJpaRepository.findAll()).isEmpty()
            );
        }

        @Test
        @DisplayName("이름과 별명이 모두 없으면 400 BAD_REQUEST를 반환한다")
        void throwsBadRequest_whenBothNameAndNicknameAreNull() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                null,  // 이름 없음
                null,  // 별명 없음
                "WHITE",
                null,
                null,
                null,
                null,
                null,
                null,
                null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                ENDPOINT_REGISTER_CHEF,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(chefJpaRepository.findAll()).isEmpty()
            );
        }
    }

    @Nested
    @DisplayName("PATCH /performer/chef/{chefId}")
    class UpdateChefInfo {

        @Test
        @DisplayName("이름만 수정하면 200 OK를 반환한다")
        void updateChef_nameOnly() {
            // given
            PerformerRequest.RegisterChef registerRequest = new PerformerRequest.RegisterChef(
                    "손종원",
                    "요리천재",
                    "BLACK",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(registerRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            Long chefId = chefJpaRepository.findAll().getFirst().getId();

            PerformerRequest.UpdateChefInfo updateRequest = new PerformerRequest.UpdateChefInfo(
                    "안유성",  // 이름 변경
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
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_UPDATE_CHEF_INFO + chefId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(chefJpaRepository.findById(chefId).orElseThrow().getName()).isEqualTo("안유성");
        }

        @Test
        @DisplayName("별명만 수정하면 200 OK를 반환한다")
        void updateChef_nicknameOnly() {
            // given
            PerformerRequest.RegisterChef registerRequest = new PerformerRequest.RegisterChef(
                    "권성준",
                    "나폴리맛피아",
                    "BLACK",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(registerRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            Long chefId = chefJpaRepository.findAll().getFirst().getId();

            PerformerRequest.UpdateChefInfo updateRequest = new PerformerRequest.UpdateChefInfo(
                    null,
                    "이탈리안마스터",  // 별명 변경
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
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_UPDATE_CHEF_INFO + chefId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(chefJpaRepository.findById(chefId).orElseThrow().getNickname()).isEqualTo("이탈리안마스터");
        }

        @Test
        @DisplayName("타입을 수정하면 200 OK를 반환한다")
        void updateChef_type() {
            // given
            PerformerRequest.RegisterChef registerRequest = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "BLACK",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(registerRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            Long chefId = chefJpaRepository.findAll().getFirst().getId();

            PerformerRequest.UpdateChefInfo updateRequest = new PerformerRequest.UpdateChefInfo(
                    null,
                    null,
                    "WHITE",  // 타입 변경
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_UPDATE_CHEF_INFO + chefId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        @DisplayName("레스토랑 정보를 수정하면 200 OK를 반환한다")
        void updateChef_restaurant() {
            // given
            PerformerRequest.RegisterChef registerRequest = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "WHITE",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(registerRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            Long chefId = chefJpaRepository.findAll().getFirst().getId();

            PerformerRequest.UpdateChefInfo updateRequest = new PerformerRequest.UpdateChefInfo(
                    null,
                    null,
                    null,
                    "라망 시크레",
                    "서울시 강남구",
                    "KOREAN",
                    "https://naver.com/reservation",
                    "https://catchtable.com",
                    "https://instagram.com/chef",
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_UPDATE_CHEF_INFO + chefId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> {
                        Assertions.assertNotNull(chefJpaRepository.findById(chefId).orElseThrow().getRestaurant());
                        assertThat(chefJpaRepository.findById(chefId).orElseThrow().getRestaurant().getAddress())
                                .isEqualTo("서울시 강남구");
                    },
                    () -> {
                        Assertions.assertNotNull(chefJpaRepository.findById(chefId).orElseThrow().getRestaurant());
                        assertThat(chefJpaRepository.findById(chefId).orElseThrow().getRestaurant().getName())
                                .isEqualTo("라망 시크레");
                    }
            );
        }

        @Test
        @DisplayName("이미지를 수정하면 200 OK를 반환한다")
        @Transactional
        void updateChef_images() {
            // given
            PerformerRequest.RegisterChef registerRequest = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "WHITE",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(registerRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            Long chefId = chefJpaRepository.findAll().getFirst().getId();

            PerformerRequest.UpdateChefInfo updateRequest = new PerformerRequest.UpdateChefInfo(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    List.of("https://example.com/new1.jpg", "https://example.com/new2.jpg")
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_UPDATE_CHEF_INFO + chefId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertAll(
                    () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
                    () -> {
                        Assertions.assertNotNull(chefJpaRepository.findById(chefId).orElseThrow().getImages());
                        assertThat(chefJpaRepository.findById(chefId).orElseThrow().getImages().getImageUrls())
                                .hasSize(2);
                    }
            );
        }

        @Test
        @DisplayName("여러 필드를 동시에 수정하면 200 OK를 반환한다")
        void updateChef_multipleFields() {
            // given
            PerformerRequest.RegisterChef registerRequest = new PerformerRequest.RegisterChef(
                    "손종원",
                    "요리천재",
                    "WHITE",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(registerRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            Long chefId = chefJpaRepository.findAll().getFirst().getId();
            
            PerformerRequest.UpdateChefInfo updateRequest = new PerformerRequest.UpdateChefInfo(
                    "새이름",
                    "새별명",
                    "WHITE",
                    null,
                    "새주소",
                    "CAFE",
                    null,
                    null,
                    null,
                    List.of("https://example.com/image.jpg")
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_UPDATE_CHEF_INFO + chefId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        @DisplayName("존재하지 않는 Chef ID로 수정하면 404 NOT_FOUND를 반환한다")
        void updateChef_notFound() {
            // given
            Long nonExistentId = 99999L;
            PerformerRequest.UpdateChefInfo updateRequest = new PerformerRequest.UpdateChefInfo(
                    "새이름",
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
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_UPDATE_CHEF_INFO + nonExistentId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        @DisplayName("이름이 5자를 초과하면 400 BAD_REQUEST를 반환한다")
        void updateChef_nameTooLong() {
            // given
            PerformerRequest.RegisterChef registerRequest = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "WHITE",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(registerRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            Long chefId = chefJpaRepository.findAll().getFirst().getId();

            PerformerRequest.UpdateChefInfo updateRequest = new PerformerRequest.UpdateChefInfo(
                    "손종원입니다",  // 6자
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
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_UPDATE_CHEF_INFO + chefId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("별명이 15자를 초과하면 400 BAD_REQUEST를 반환한다")
        void updateChef_nicknameTooLong() {
            // given
            PerformerRequest.RegisterChef registerRequest = new PerformerRequest.RegisterChef(
                    "손종원",
                    "요리천재",
                    "WHITE",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(registerRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            Long chefId = chefJpaRepository.findAll().getFirst().getId();
            PerformerRequest.UpdateChefInfo updateRequest = new PerformerRequest.UpdateChefInfo(
                    null,
                    "나폴리맛피아입니다만최고입니다요",  // 16자
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
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_UPDATE_CHEF_INFO + chefId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("잘못된 URL 형식이 주어지면 400 BAD_REQUEST를 반환한다")
        void updateChef_invalidUrlFormat() {
            // given
            PerformerRequest.RegisterChef registerRequest = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "WHITE",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(registerRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            Long chefId = chefJpaRepository.findAll().getFirst().getId();

            PerformerRequest.UpdateChefInfo updateRequest = new PerformerRequest.UpdateChefInfo(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "not-a-url",  // 잘못된 URL
                    null,
                    null,
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_UPDATE_CHEF_INFO + chefId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @ParameterizedTest
        @DisplayName("잘못된 chefType 값이 주어지면 400 BAD_REQUEST를 반환한다")
        @ValueSource(strings = {
                "YELLOW",
                "Pink"
        })
        void updateChef_invalidChefType(String wrongValue) {
            // given
            PerformerRequest.RegisterChef registerRequest = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "WHITE",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(registerRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            Long chefId = chefJpaRepository.findAll().getFirst().getId();

            PerformerRequest.UpdateChefInfo updateRequest = new PerformerRequest.UpdateChefInfo(
                    null,
                    null,
                    wrongValue,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_UPDATE_CHEF_INFO + chefId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @ParameterizedTest
        @DisplayName("잘못된 restaurantCategory 값이 주어지면 400 BAD_REQUEST를 반환한다")
        @ValueSource(strings = {
                "MEXICAN",
                "TACO"
        })
        void updateChef_invalidRestaurantCategory(String wrongValue) {
            // given
            PerformerRequest.RegisterChef registerRequest = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "WHITE",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(registerRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            Long chefId = chefJpaRepository.findAll().getFirst().getId();

            PerformerRequest.UpdateChefInfo updateRequest = new PerformerRequest.UpdateChefInfo(
                    null,
                    null,
                    null,
                    null,
                    "서울시 강남구",
                    wrongValue,
                    null,
                    null,
                    null,
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_UPDATE_CHEF_INFO + chefId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }

    @Nested
    @DisplayName("GET /performer/chefs")
    class GetChefs {

        @Test
        @DisplayName("type이 ALL인 경우 address가 있는 모든 Chef를 조회한다")
        void getChefs_withTypeAll() {
            // given - BLACK 요리사 (address 있음)
            PerformerRequest.RegisterChef blackChefRegisterRequest = new PerformerRequest.RegisterChef(
                    "권성준",
                    "나폴리맛피아",
                    "BLACK",
                    "비아톨레도 파스타바",
                    "서울시 강남구",
                    "ITALIAN",
                    null,
                    null,
                    null,
                    List.of("https://example.com/image1.jpg")
            );

            // given - WHITE 요리사 (address 있음)
            PerformerRequest.RegisterChef whiteChefRegisterRequest = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "WHITE",
                    "라망 시크레",
                    "서울시 서초구",
                    "KOREAN",
                    null,
                    null,
                    null,
                    null
            );

            // given - BLACK 요리사 (address 없음 - 조회되지 않아야 함)
            PerformerRequest.RegisterChef blackChefWithoutAddress = new PerformerRequest.RegisterChef(
                    "안유성",
                    null,
                    "BLACK",
                    "흑 가게",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(blackChefRegisterRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(whiteChefRegisterRequest),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(blackChefWithoutAddress),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEFS + "?type=ALL",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).hasSize(2);  // address가 있는 2명만 조회
        }

        @Test
        @DisplayName("type이 BLACK인 경우 address가 있는 흑요리사만 조회한다")
        void getChefs_withTypeBlack() {
            // given - BLACK 요리사 (address 있음)
            PerformerRequest.RegisterChef blackChef1 = new PerformerRequest.RegisterChef(
                    "권성준",
                    "나폴리맛피아",
                    "BLACK",
                    "비아톨레도 파스타바",
                    "서울시 강남구",
                    "ITALIAN",
                    null,
                    null,
                    null,
                    null
            );

            // given - BLACK 요리사 (address 있음)
            PerformerRequest.RegisterChef blackChef2 = new PerformerRequest.RegisterChef(
                    "안유성",
                    null,
                    "BLACK",
                    "가게 이름",
                    "서울시 종로구",
                    "KOREAN",
                    null,
                    null,
                    null,
                    null
            );

            // given - WHITE 요리사 (address 있음 - 조회되지 않아야 함)
            PerformerRequest.RegisterChef whiteChef = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "WHITE",
                    "라망 시크레",
                    "서울시 서초구",
                    "KOREAN",
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(blackChef1),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(blackChef2),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(whiteChef),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEFS + "?type=BLACK",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).hasSize(2);
            assertThat(response.getBody().data())
                    .extracting(PerformerResponse.ChefInfo::type)
                    .containsOnly("BLACK");
        }

        @Test
        @DisplayName("type이 WHITE인 경우 address가 있는 백요리사만 조회한다")
        void getChefs_withTypeWhite() {
            // given - WHITE 요리사 (address 있음)
            PerformerRequest.RegisterChef whiteChef1 = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "WHITE",
                    "라망 시크레",
                    "서울시 서초구",
                    "KOREAN",
                    null,
                    null,
                    null,
                    null
            );

            // given - BLACK 요리사 (address 있음 - 조회되지 않아야 함)
            PerformerRequest.RegisterChef blackChef = new PerformerRequest.RegisterChef(
                    "권성준",
                    "나폴리맛피아",
                    "BLACK",
                    "비아톨레도 파스타바",
                    "서울시 강남구",
                    "ITALIAN",
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(whiteChef1),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(blackChef),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEFS + "?type=WHITE",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).hasSize(1);
            assertThat(response.getBody().data().getFirst().type()).isEqualTo("WHITE");
        }

        @Test
        @DisplayName("type이 없으면 address가 있는 모든 Chef를 조회한다")
        void getChefs_withoutType() {
            // given
            PerformerRequest.RegisterChef blackChef = new PerformerRequest.RegisterChef(
                    "권성준",
                    "나폴리맛피아",
                    "BLACK",
                    "비아톨레도 파스타바",
                    "서울시 강남구",
                    "ITALIAN",
                    null,
                    null,
                    null,
                    null
            );

            PerformerRequest.RegisterChef whiteChef = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "WHITE",
                    "라망 시크레",
                    "서울시 서초구",
                    "KOREAN",
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(blackChef),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(whiteChef),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEFS,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).hasSize(2);
        }

        @Test
        @DisplayName("address가 없는 Chef는 조회되지 않는다")
        void getChefs_excludesChefsWithoutAddress() {
            // given - address 없음
            PerformerRequest.RegisterChef chefWithoutAddress = new PerformerRequest.RegisterChef(
                    "안유성",
                    null,
                    "BLACK",
                    "가게 이름",
                    null,  // address 없음
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(chefWithoutAddress),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
                    );

            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEFS + "?type=ALL",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).isEmpty();
        }

        @Test
        @DisplayName("잘못된 type 값이 주어지면 400 BAD_REQUEST를 반환한다")
        void getChefs_invalidType() {
            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEFS + "?type=YELLOW",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("조회된 Chef는 모든 필드 정보를 포함한다")
        void getChefs_includesAllFields() {
            // given
            PerformerRequest.RegisterChef request = new PerformerRequest.RegisterChef(
                    "권성준",
                    "나폴리맛피아",
                    "BLACK",
                    "비아톨레도 파스타바",
                    "서울시 강남구",
                    "ITALIAN",
                    "https://naver.com/reservation",
                    "https://catchtable.com",
                    "https://instagram.com/chef",
                    List.of("https://example.com/image1.jpg", "https://example.com/image2.jpg")
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(request),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEFS + "?type=BLACK",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).hasSize(1);

            PerformerResponse.ChefInfo chef = response.getBody().data().getFirst();
            assertAll(
                    () -> assertThat(chef.id()).isNotNull(),
                    () -> assertThat(chef.name()).isEqualTo("권성준"),
                    () -> assertThat(chef.nickname()).isEqualTo("나폴리맛피아"),
                    () -> assertThat(chef.type()).isEqualTo("BLACK"),
                    () -> assertThat(chef.restaurantName()).isEqualTo("비아톨레도 파스타바"),
                    () -> assertThat(chef.address()).isEqualTo("서울시 강남구"),
                    () -> assertThat(chef.category()).isEqualTo("ITALIAN"),
                    () -> assertThat(chef.naverReservationUrl()).isEqualTo("https://naver.com/reservation"),
                    () -> assertThat(chef.catchTableUrl()).isEqualTo("https://catchtable.com"),
                    () -> assertThat(chef.instagramUrl()).isEqualTo("https://instagram.com/chef"),
                    () -> assertThat(chef.imageUrls()).hasSize(2),
                    () -> assertThat(chef.viewCount()).isEqualTo(0L)
            );
        }
    }

    @Nested
    @DisplayName("GET /performer/chefs/cluster")
    class GetChefClusters {

        @Test
        @DisplayName("클러스터 데이터를 조회하면 200 OK를 반환한다")
        void getChefClusters_returnsOk() {
            // given - 서울특별시에 흑요리사 2명, 백요리사 1명
            PerformerRequest.RegisterChef seoulBlackChef1 = new PerformerRequest.RegisterChef(
                    "권성준",
                    "나폴리맛피아",
                    "BLACK",
                    "비아톨레도 파스타바",
                    "서울특별시 강남구 테헤란로 123",
                    "ITALIAN",
                    null,
                    null,
                    null,
                    null
            );

            PerformerRequest.RegisterChef seoulBlackChef2 = new PerformerRequest.RegisterChef(
                    "안유성",
                    null,
                    "BLACK",
                    "흑가게",
                    "서울특별시 종로구 종로 1",
                    "KOREAN",
                    null,
                    null,
                    null,
                    null
            );

            PerformerRequest.RegisterChef seoulWhiteChef = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "WHITE",
                    "라망 시크레",
                    "서울특별시 서초구 강남대로 456",
                    "KOREAN",
                    null,
                    null,
                    null,
                    null
            );

            // given - 부산광역시에 흑요리사 1명
            PerformerRequest.RegisterChef busanBlackChef = new PerformerRequest.RegisterChef(
                    "김부산",
                    null,
                    "BLACK",
                    "부산가게",
                    "부산광역시 해운대구 해운대로 789",
                    "JAPANESE",
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(seoulBlackChef1),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(seoulBlackChef2),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(seoulWhiteChef),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(busanBlackChef),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefClusterInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEF_CLUSTERS,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).hasSize(2);  // 서울, 부산
        }

        @Test
        @DisplayName("시/도별로 흑요리사와 백요리사 수를 정확히 집계한다")
        void getChefClusters_aggregatesCorrectly() {
            // given - 서울특별시에 흑2, 백1
            PerformerRequest.RegisterChef seoulBlackChef1 = new PerformerRequest.RegisterChef(
                    "권성준",
                    null,
                    "BLACK",
                    "가게1",
                    "서울특별시 강남구 테헤란로 123",
                    null,
                    null,
                    null,
                    null,
                    null
            );

            PerformerRequest.RegisterChef seoulBlackChef2 = new PerformerRequest.RegisterChef(
                    "안유성",
                    null,
                    "BLACK",
                    "가게2",
                    "서울특별시 종로구 종로 1",
                    null,
                    null,
                    null,
                    null,
                    null
            );

            PerformerRequest.RegisterChef seoulWhiteChef = new PerformerRequest.RegisterChef(
                    "손종원",
                    null,
                    "WHITE",
                    "가게3",
                    "서울특별시 서초구 강남대로 456",
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(seoulBlackChef1),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(seoulBlackChef2),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(seoulWhiteChef),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefClusterInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEF_CLUSTERS,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();

            PerformerResponse.ChefClusterInfo seoulCluster = response.getBody().data()
                    .stream()
                    .filter(cluster -> cluster.region().equals("서울특별시"))
                    .findFirst()
                    .orElseThrow();

            assertAll(
                    () -> assertThat(seoulCluster.region()).isEqualTo("서울특별시"),
                    () -> assertThat(seoulCluster.blackCount()).isEqualTo(2),
                    () -> assertThat(seoulCluster.whiteCount()).isEqualTo(1),
                    () -> assertThat(seoulCluster.latitude()).isEqualTo(37.5665),
                    () -> assertThat(seoulCluster.longitude()).isEqualTo(126.978)
            );
        }

        @Test
        @DisplayName("address가 없는 Chef는 클러스터 집계에서 제외된다")
        void getChefClusters_excludesChefsWithoutAddress() {
            // given - address가 없는 Chef
            PerformerRequest.RegisterChef chefWithoutAddress = new PerformerRequest.RegisterChef(
                    "안유성",
                    null,
                    "BLACK",
                    "가게",
                    null,  // address 없음
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(chefWithoutAddress),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefClusterInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEF_CLUSTERS,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).isEmpty();  // 클러스터 데이터 없음
        }

        @Test
        @DisplayName("Region enum에 없는 주소는 클러스터 집계에서 제외된다")
        void getChefClusters_excludesUnknownRegions() {
            // given - Region enum에 없는 주소 (예: 잘못된 형식)
            PerformerRequest.RegisterChef chefWithInvalidAddress = new PerformerRequest.RegisterChef(
                    "김철수",
                    null,
                    "BLACK",
                    "가게",
                    "알수없는지역 어딘가 123",  // Region enum에 없음
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(chefWithInvalidAddress),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefClusterInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEF_CLUSTERS,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).isEmpty();  // 매칭되는 Region 없어서 제외됨
        }

        @Test
        @DisplayName("여러 시/도에 걸쳐 Chef가 있으면 각각 집계된다")
        void getChefClusters_aggregatesMultipleRegions() {
            // given - 서울, 부산, 경기도에 각각 Chef 등록
            PerformerRequest.RegisterChef seoulChef = new PerformerRequest.RegisterChef(
                    "서울셰프",
                    null,
                    "BLACK",
                    "서울가게",
                    "서울특별시 강남구 테헤란로 123",
                    null,
                    null,
                    null,
                    null,
                    null
            );

            PerformerRequest.RegisterChef busanChef = new PerformerRequest.RegisterChef(
                    "부산셰프",
                    null,
                    "WHITE",
                    "부산가게",
                    "부산광역시 해운대구 해운대로 456",
                    null,
                    null,
                    null,
                    null,
                    null
            );

            PerformerRequest.RegisterChef gyeonggiChef = new PerformerRequest.RegisterChef(
                    "경기셰프",
                    null,
                    "BLACK",
                    "경기가게",
                    "경기도 성남시 분당구 판교역로 789",
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(seoulChef),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(busanChef),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );
            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(gyeonggiChef),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefClusterInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEF_CLUSTERS,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).hasSize(3);  // 서울, 부산, 경기도

            List<String> regionNames = response.getBody().data().stream()
                    .map(PerformerResponse.ChefClusterInfo::region)
                    .toList();

            assertThat(regionNames).containsExactlyInAnyOrder("서울특별시", "부산광역시", "경기도");
        }

        @Test
        @DisplayName("응답 데이터는 모든 필드를 포함한다")
        void getChefClusters_includesAllFields() {
            // given
            PerformerRequest.RegisterChef seoulChef = new PerformerRequest.RegisterChef(
                    "권성준",
                    null,
                    "BLACK",
                    "가게",
                    "서울특별시 강남구 테헤란로 123",
                    null,
                    null,
                    null,
                    null,
                    null
            );

            testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF,
                    HttpMethod.POST,
                    new HttpEntity<>(seoulChef),
                    new ParameterizedTypeReference<ApiResponse<Object>>() {}
            );

            // when
            ResponseEntity<ApiResponse<List<PerformerResponse.ChefClusterInfo>>> response = testRestTemplate.exchange(
                    ENDPOINT_GET_CHEF_CLUSTERS,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().data()).hasSize(1);

            PerformerResponse.ChefClusterInfo cluster = response.getBody().data().getFirst();
            assertAll(
                    () -> assertThat(cluster.region()).isNotNull(),
                    () -> assertThat(cluster.blackCount()).isNotNull(),
                    () -> assertThat(cluster.whiteCount()).isNotNull(),
                    () -> assertThat(cluster.latitude()).isNotNull(),
                    () -> assertThat(cluster.longitude()).isNotNull()
            );
        }
    }
}
