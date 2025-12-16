package com.blackwhitemap.blackwhitemap_back.interfaces.performer;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
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
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF + "/" + chefId,
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
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF + "/" + chefId,
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
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF + "/" + chefId,
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
                    "서울시 강남구",
                    "KOREAN",
                    "https://naver.com/reservation",
                    "https://catchtable.com",
                    "https://instagram.com/chef",
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF + "/" + chefId,
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
                    List.of("https://example.com/new1.jpg", "https://example.com/new2.jpg")
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF + "/" + chefId,
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
                    "새주소",
                    "CAFE",
                    null,
                    null,
                    null,
                    List.of("https://example.com/image.jpg")
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF + "/" + chefId,
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
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF + "/" + nonExistentId,
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
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF + "/" + chefId,
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
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF + "/" + chefId,
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
                    "not-a-url",  // 잘못된 URL
                    null,
                    null,
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF + "/" + chefId,
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
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF + "/" + chefId,
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
                    "서울시 강남구",
                    wrongValue,
                    null,
                    null,
                    null,
                    null
            );

            // when
            ResponseEntity<ApiResponse<Object>> response = testRestTemplate.exchange(
                    ENDPOINT_REGISTER_CHEF + "/" + chefId,
                    HttpMethod.PATCH,
                    new HttpEntity<>(updateRequest),
                    new ParameterizedTypeReference<>() {}
            );

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }
}
