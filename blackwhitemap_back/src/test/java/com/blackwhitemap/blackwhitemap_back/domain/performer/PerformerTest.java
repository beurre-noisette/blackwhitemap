package com.blackwhitemap.blackwhitemap_back.domain.performer;

import com.blackwhitemap.blackwhitemap_back.support.error.CoreException;
import com.blackwhitemap.blackwhitemap_back.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Performer 도메인 단위 테스트")
class PerformerTest {

    @Nested
    @DisplayName("Chef 엔티티를 생성 할 때")
    class CreateChef {
        @Test
        @DisplayName("백요리사의 경우 이름만 제공되어도 성공적으로 생성된다.")
        void createWhiteChef_WithNameOnly() {
            // given
            PerformerCommand.CreateChef command = new PerformerCommand.CreateChef(
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
            Chef chef = Chef.of(command);

            // then
            assertThat(chef.getName()).isEqualTo("손종원");
            assertThat(chef.getNickname()).isNull();
            assertThat(chef.getType()).isEqualTo(Chef.Type.WHITE);
            assertThat(chef.getViewCount()).isZero();
        }

        @Test
        @DisplayName("흑요리사의 경우 별명만 제공되어도 성공적으로 생성된다.")
        void createBlackChef_WithNicknameOnly() {
            // given
            PerformerCommand.CreateChef command = new PerformerCommand.CreateChef(
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
            Chef chef = Chef.of(command);

            // then
            assertThat(chef.getName()).isNull();
            assertThat(chef.getNickname()).isEqualTo("나폴리맛피아");
            assertThat(chef.getType()).isEqualTo(Chef.Type.BLACK);
        }

        @Test
        @DisplayName("흑요리사의 경우 이름과 별명이 모두 제공되어도 성공적으로 생성된다.")
        void createBlackChef_WithBothNameAndNickname() {
            // given
            PerformerCommand.CreateChef command = new PerformerCommand.CreateChef(
                    "권성준",
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
            Chef chef = Chef.of(command);

            // then
            assertThat(chef.getName()).isEqualTo("권성준");
            assertThat(chef.getNickname()).isEqualTo("나폴리맛피아");
            assertThat(chef.getType()).isEqualTo(Chef.Type.BLACK);
        }

        @Test
        @DisplayName("Restaurant 정보가 포함된 Chef가 정상적으로 생성된다.")
        void createChef_WithRestaurant() {
            // given
            PerformerCommand.CreateChef command = new PerformerCommand.CreateChef(
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
            Chef chef = Chef.of(command);

            // then
            assertThat(chef.getRestaurant()).isNotNull();
            assertThat(chef.getRestaurant().getAddress()).isEqualTo("서울시 강남구");
            assertThat(chef.getRestaurant().getCategory()).isEqualTo(Restaurant.Category.ITALIAN);
            assertThat(chef.getImages().getImageUrls().size()).isZero();
        }

        @Test
        @DisplayName("ChefImages 정보가 포함된 Chef가 정상적으로 생성된다.")
        void createChef_WithImages() {
            // given
            List<String> imageUrls = List.of(
                    "https://example.com/image1.jpg",
                    "https://example.com/image2.jpg"
            );
            PerformerCommand.CreateChef command = new PerformerCommand.CreateChef(
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
            Chef chef = Chef.of(command);

            // then
            assertThat(chef.getImages()).isNotNull();
            assertThat(chef.getImages().getImageUrls()).hasSize(2);
            assertThat(chef.getRestaurant()).isNull();
        }

        @Test
        @DisplayName("Restaurant와 ChefImages 모두 포함된 Chef가 정상적으로 생성된다.")
        void createChef_WithRestaurantAndImages() {
            // given
            List<String> imageUrls = List.of(
                    "https://example.com/image1.jpg",
                    "https://example.com/image2.jpg",
                    "https://example.com/image3.jpg"
            );
            PerformerCommand.CreateChef command = new PerformerCommand.CreateChef(
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
            Chef chef = Chef.of(command);

            // then
            assertThat(chef.getName()).isEqualTo("손종원");
            assertThat(chef.getType()).isEqualTo(Chef.Type.WHITE);
            assertThat(chef.getRestaurant()).isNotNull();
            assertThat(chef.getRestaurant().getAddress()).isEqualTo("서울시 용산구");
            assertThat(chef.getRestaurant().getCategory()).isEqualTo(Restaurant.Category.KOREAN);
            assertThat(chef.getImages()).isNotNull();
            assertThat(chef.getImages().getImageUrls()).hasSize(3);
        }

        @Test
        @DisplayName("타입이 제공되지 않을 경우 BAD_REQUEST 예외를 반환한다.")
        void throwBadRequestException_whenTypeIsNull() {
            // given
            PerformerCommand.CreateChef command = new PerformerCommand.CreateChef(
                    "손종원",
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
            CoreException exception = assertThrows(CoreException.class,
                    () -> Chef.of(command));

            // then
            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("셰프 타입은 필수입니다.");
        }

        @Test
        @DisplayName("이름과 별명이 모두 없을 경우 BAD_REQUEST 예외를 반환한다.")
        void throwBadRequestException_whenNameAndNicknameBothNull() {
            // given
            PerformerCommand.CreateChef command = new PerformerCommand.CreateChef(
                    null,
                    null,
                    Chef.Type.BLACK,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // when
            CoreException exception = assertThrows(CoreException.class,
                    () -> Chef.of(command));

            // then
            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("이름 또는 별명 중 하나는 필수입니다.");
        }

        @ParameterizedTest
        @DisplayName("이름과 별명이 모두 빈 문자열일 경우 BAD_REQUEST 예외를 반환한다.")
        @EmptySource
        void throwBadRequestException_whenNameAndNicknameBothEmpty(String emptySource) {
            // given
            PerformerCommand.CreateChef command = new PerformerCommand.CreateChef(
                    emptySource,
                    emptySource,
                    Chef.Type.WHITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // when
            CoreException exception = assertThrows(CoreException.class,
                    () -> Chef.of(command));

            // then
            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("이름 또는 별명 중 하나는 필수입니다.");
        }
    }

    @Nested
    @DisplayName("ChefImages 생성 할 때")
    class CreateChefImages {

        @ParameterizedTest
        @DisplayName("이미지가 없을 때(null, empty) 정상적으로 생성된다")
        @NullAndEmptySource
        void createWithNullOrEmptyList(List<String> nullOrEmptyList) {
            // when
            ChefImages images = ChefImages.of(nullOrEmptyList);

            // then
            assertThat(images).isNotNull();
        }

        @ParameterizedTest
        @DisplayName("이미지가 1개~3개일 때 정상적으로 생성된다")
        @ValueSource(ints = {1, 3})
        void createWithValidImageCount(int imageCount) {
            // given
            List<String> imageUrls = new ArrayList<>();
            for (int i = 1; i <= imageCount; i++) {
                imageUrls.add("https://example.com/image" + i + ".jpg");
            }

            // when
            ChefImages images = ChefImages.of(imageUrls);

            // then
            assertThat(images).isNotNull();
            assertThat(images.getImageUrls().size()).isEqualTo(imageCount);
        }

        @Test
        @DisplayName("이미지가 4개 이상일 때 BAD_REQUEST 예외를 반환한다")
        void throwBadRequestException_whenMoreThanThreeImages() {
            // given
            List<String> imageUrls = List.of(
                    "https://example.com/image1.jpg",
                    "https://example.com/image2.jpg",
                    "https://example.com/image3.jpg",
                    "https://example.com/image4.jpg"
            );

            // when
            CoreException exception = assertThrows(CoreException.class,
                    () -> ChefImages.of(imageUrls));

            // then
            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("최대 3개");
        }
    }

    @Nested
    @DisplayName("Restaurant 생성 할 때")
    class CreateRestaurant {
        @Test
        @DisplayName("모든 요소가 제공되지 않으면 null을 반환한다.")
        void createWithAllNull() {
            // when
            Restaurant restaurant = Restaurant.of(
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // then
            assertThat(restaurant).isNull();
        }

        @Test
        @DisplayName("요소가 하나라도 제공되면 Restaurant가 생성된다.")
        void createRestaurant_whenAtLeastOneFieldProvided() {
            // when
            Restaurant restaurant = Restaurant.of(
                    "서울시 강남구",
                    null,
                    null,
                    null,
                    null
            );

            // then
            assertThat(restaurant).isNotNull();
            assertThat(restaurant.getAddress()).isEqualTo("서울시 강남구");
        }
    }
}
