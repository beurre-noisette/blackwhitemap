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
    class RegisterChef {
        @Test
        @DisplayName("백요리사의 경우 이름만 제공되어도 성공적으로 생성된다.")
        void createWhiteChef_WithNameOnly() {
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
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
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
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
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
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
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
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
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
    class RegisterChefImages {

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

    @Nested
    @DisplayName("Chef의 이름을 수정할 때")
    class UpdateChefName {

        @Test
        @DisplayName("정상적인 이름으로 변경이 성공한다.")
        void updateName_withValidName() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    "요리천재",
                    Chef.Type.WHITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            Chef chef = Chef.of(registerCommand);

            // when
            chef.updateName("안유성");

            // then
            assertThat(chef.getName()).isEqualTo("안유성");
            assertThat(chef.getNickname()).isEqualTo("요리천재");
        }

        @Test
        @DisplayName("nickname이 있는 경우 name을 null로 변경할 수 있다.")
        void updateName_toNull_whenNicknameExists() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    "요리천재",
                    Chef.Type.WHITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            Chef chef = Chef.of(registerCommand);

            // when
            chef.updateName(null);

            // then
            assertThat(chef.getName()).isNull();
            assertThat(chef.getNickname()).isEqualTo("요리천재");
        }

        @Test
        @DisplayName("nickname이 있는 경우 name을 빈 문자열로 변경할 수 있다.")
        void updateName_toEmpty_whenNicknameExists() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    "요리천재",
                    Chef.Type.WHITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            Chef chef = Chef.of(registerCommand);

            // when
            chef.updateName("");

            // then
            assertThat(chef.getName()).isEmpty();
            assertThat(chef.getNickname()).isEqualTo("요리천재");
        }

        @Test
        @DisplayName("nickname이 없는 경우 name을 null로 변경하면 BAD_REQUEST 예외가 발생한다.")
        void throwBadRequestException_whenUpdateNameToNull_andNicknameIsNull() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
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
            Chef chef = Chef.of(registerCommand);

            // when & then
            CoreException exception = assertThrows(CoreException.class,
                    () -> chef.updateName(null));

            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("이름 또는 별명 중 하나는 필수입니다.");
        }

        @ParameterizedTest
        @DisplayName("nickname이 없는 경우 name을 빈 문자열로 변경하면 BAD_REQUEST 예외가 발생한다.")
        @EmptySource
        void throwBadRequestException_whenUpdateNameToEmpty_andNicknameIsNull(String emptyString) {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
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
            Chef chef = Chef.of(registerCommand);

            // when & then
            CoreException exception = assertThrows(CoreException.class,
                    () -> chef.updateName(emptyString));

            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("이름 또는 별명 중 하나는 필수입니다.");
        }

        @Test
        @DisplayName("nickname이 빈 문자열인 경우 name을 null로 변경하면 BAD_REQUEST 예외가 발생한다.")
        void throwBadRequestException_whenUpdateNameToNull_andNicknameIsEmpty() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    "",
                    Chef.Type.WHITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            Chef chef = Chef.of(registerCommand);

            // when & then
            CoreException exception = assertThrows(CoreException.class,
                    () -> chef.updateName(null));

            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("이름 또는 별명 중 하나는 필수입니다.");
        }
    }

    @Nested
    @DisplayName("Chef의 별명을 수정할 때")
    class UpdateChefNickname {

        @Test
        @DisplayName("정상적인 별명으로 변경이 성공한다.")
        void updateNickname_withValidNickname() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
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
            Chef chef = Chef.of(registerCommand);

            // when
            chef.updateNickname("이탈리안마스터");

            // then
            assertThat(chef.getName()).isEqualTo("권성준");
            assertThat(chef.getNickname()).isEqualTo("이탈리안마스터");
        }

        @Test
        @DisplayName("name이 있는 경우 nickname을 null로 변경할 수 있다.")
        void updateNickname_toNull_whenNameExists() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
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
            Chef chef = Chef.of(registerCommand);

            // when
            chef.updateNickname(null);

            // then
            assertThat(chef.getName()).isEqualTo("권성준");
            assertThat(chef.getNickname()).isNull();
        }

        @Test
        @DisplayName("name이 있는 경우 nickname을 빈 문자열로 변경할 수 있다.")
        void updateNickname_toEmpty_whenNameExists() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
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
            Chef chef = Chef.of(registerCommand);

            // when
            chef.updateNickname("");

            // then
            assertThat(chef.getName()).isEqualTo("권성준");
            assertThat(chef.getNickname()).isEmpty();
        }

        @Test
        @DisplayName("name이 없는 경우 nickname을 null로 변경하면 BAD_REQUEST 예외가 발생한다.")
        void throwBadRequestException_whenUpdateNicknameToNull_andNameIsNull() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
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
            Chef chef = Chef.of(registerCommand);

            // when & then
            CoreException exception = assertThrows(CoreException.class,
                    () -> chef.updateNickname(null));

            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("이름 또는 별명 중 하나는 필수입니다.");
        }

        @ParameterizedTest
        @DisplayName("name이 없는 경우 nickname을 빈 문자열로 변경하면 BAD_REQUEST 예외가 발생한다.")
        @EmptySource
        void throwBadRequestException_whenUpdateNicknameToEmpty_andNameIsNull(String emptyString) {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
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
            Chef chef = Chef.of(registerCommand);

            // when & then
            CoreException exception = assertThrows(CoreException.class,
                    () -> chef.updateNickname(emptyString));

            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("이름 또는 별명 중 하나는 필수입니다.");
        }

        @Test
        @DisplayName("name이 빈 문자열인 경우 nickname을 null로 변경하면 BAD_REQUEST 예외가 발생한다.")
        void throwBadRequestException_whenUpdateNicknameToNull_andNameIsEmpty() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "",
                    "나폴리맛피아",
                    Chef.Type.BLACK,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            Chef chef = Chef.of(registerCommand);

            // when & then
            CoreException exception = assertThrows(CoreException.class,
                    () -> chef.updateNickname(null));

            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("이름 또는 별명 중 하나는 필수입니다.");
        }
    }

    @Nested
    @DisplayName("Chef의 타입을 수정할 때")
    class UpdateChefType {

        @Test
        @DisplayName("BLACK에서 WHITE로 변경이 성공한다.")
        void updateType_fromBlackToWhite() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    null,
                    Chef.Type.BLACK,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            Chef chef = Chef.of(registerCommand);

            // when
            chef.updateType(Chef.Type.WHITE);

            // then
            assertThat(chef.getType()).isEqualTo(Chef.Type.WHITE);
        }

        @Test
        @DisplayName("WHITE에서 BLACK로 변경이 성공한다.")
        void updateType_fromWhiteToBlack() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
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
            Chef chef = Chef.of(registerCommand);

            // when
            chef.updateType(Chef.Type.BLACK);

            // then
            assertThat(chef.getType()).isEqualTo(Chef.Type.BLACK);
        }

        @Test
        @DisplayName("타입을 null로 변경하면 BAD_REQUEST 예외가 발생한다.")
        void throwBadRequestException_whenUpdateTypeToNull() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
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
            Chef chef = Chef.of(registerCommand);

            // when & then
            CoreException exception = assertThrows(CoreException.class,
                    () -> chef.updateType(null));

            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("셰프 타입은 필수입니다.");
        }
    }

    @Nested
    @DisplayName("Chef의 레스토랑 정보를 수정할 때")
    class UpdateChefRestaurant {

        @Test
        @DisplayName("모든 레스토랑 정보를 제공하여 수정이 성공한다.")
        void updateRestaurant_withAllFields() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
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
            Chef chef = Chef.of(registerCommand);

            // when
            chef.updateRestaurant(
                    "서울시 강남구",
                    Restaurant.Category.KOREAN,
                    "https://naver.com/reservation",
                    "https://catchtable.com",
                    "https://instagram.com/chef"
            );

            // then
            assertThat(chef.getRestaurant()).isNotNull();
            assertThat(chef.getRestaurant().getAddress()).isEqualTo("서울시 강남구");
            assertThat(chef.getRestaurant().getCategory()).isEqualTo(Restaurant.Category.KOREAN);
            assertThat(chef.getRestaurant().getNaverReservationUrl()).isEqualTo("https://naver.com/reservation");
            assertThat(chef.getRestaurant().getCatchTableUrl()).isEqualTo("https://catchtable.com");
            assertThat(chef.getRestaurant().getInstagramUrl()).isEqualTo("https://instagram.com/chef");
        }

        @Test
        @DisplayName("일부 레스토랑 정보만 제공하여 수정이 성공한다.")
        void updateRestaurant_withPartialFields() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
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
            Chef chef = Chef.of(registerCommand);

            // when
            chef.updateRestaurant(
                    "서울시 용산구",
                    Restaurant.Category.ITALIAN,
                    null,
                    null,
                    "https://instagram.com/chef"
            );

            // then
            assertThat(chef.getRestaurant()).isNotNull();
            assertThat(chef.getRestaurant().getAddress()).isEqualTo("서울시 용산구");
            assertThat(chef.getRestaurant().getCategory()).isEqualTo(Restaurant.Category.ITALIAN);
            assertThat(chef.getRestaurant().getNaverReservationUrl()).isNull();
            assertThat(chef.getRestaurant().getCatchTableUrl()).isNull();
            assertThat(chef.getRestaurant().getInstagramUrl()).isEqualTo("https://instagram.com/chef");
        }

        @Test
        @DisplayName("모든 필드를 null로 전달하면 restaurant는 변경되지 않는다.")
        void updateRestaurant_toNull_whenAllFieldsAreNull() {
            // given
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                    "손종원",
                    null,
                    Chef.Type.WHITE,
                    "서울시 강남구",
                    Restaurant.Category.KOREAN,
                    "https://naver.com",
                    "https://catchtable.com",
                    "https://instagram.com",
                    null
            );
            Chef chef = Chef.of(command);
            Restaurant originalRestaurant = chef.getRestaurant();

            // when
            chef.updateRestaurant(
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // then
            assertThat(chef.getRestaurant()).isSameAs(originalRestaurant);
        }

        @Test
        @DisplayName("기존 레스토랑 정보를 새로운 정보로 덮어쓸 수 있다.")
        void updateRestaurant_replaceExistingRestaurant() {
            // given
            PerformerCommand.RegisterChef command = new PerformerCommand.RegisterChef(
                    "손종원",
                    null,
                    Chef.Type.WHITE,
                    "서울시 강남구",
                    Restaurant.Category.KOREAN,
                    "https://naver.com",
                    "https://catchtable.com",
                    "https://instagram.com",
                    null
            );
            Chef chef = Chef.of(command);
            Restaurant oldRestaurant = chef.getRestaurant();

            // when
            chef.updateRestaurant(
                    "부산시 해운대구",
                    Restaurant.Category.JAPANESE,
                    null,
                    null,
                    null
            );

            // then
            assertThat(chef.getRestaurant()).isNotNull();
            assertThat(chef.getRestaurant()).isNotSameAs(oldRestaurant);
            assertThat(chef.getRestaurant().getAddress()).isEqualTo("부산시 해운대구");
            assertThat(chef.getRestaurant().getCategory()).isEqualTo(Restaurant.Category.JAPANESE);
        }
    }

    @Nested
    @DisplayName("Chef의 이미지를 수정할 때")
    class UpdateChefImages {

        @Test
        @DisplayName("1개의 이미지로 수정이 성공한다.")
        void updateImages_withSingleImage() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    "요리천재",
                    Chef.Type.WHITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            Chef chef = Chef.of(registerCommand);
            List<String> newImages = List.of("https://example.com/new-image.jpg");

            // when
            chef.updateImages(newImages);

            // then
            assertThat(chef.getImages()).isNotNull();
            assertThat(chef.getImages().getImageUrls()).hasSize(1);
            assertThat(chef.getImages().getImageUrls()).containsExactly("https://example.com/new-image.jpg");
        }

        @ParameterizedTest
        @DisplayName("null 또는 빈 리스트로 이미지를 제거할 수 있다.")
        @NullAndEmptySource
        void updateImages_toNullOrEmpty(List<String> nullOrEmpty) {
            // given
            List<String> images = List.of(
                    "https://example.com/old1.jpg",
                    "https://example.com/old2.jpg"
            );
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    null,
                    Chef.Type.WHITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    images
            );
            Chef chef = Chef.of(registerCommand);

            // when
            chef.updateImages(nullOrEmpty);

            // then
            assertThat(chef.getImages()).isNotNull();
            assertThat(chef.getImages().getImageUrls()).isEmpty();
        }

        @Test
        @DisplayName("기존 이미지를 새로운 이미지로 교체할 수 있다.")
        void updateImages_replaceExistingImages() {
            // given
            List<String> images = List.of(
                    "https://example.com/old1.jpg",
                    "https://example.com/old2.jpg"
            );
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    null,
                    Chef.Type.WHITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    images
            );
            Chef chef = Chef.of(registerCommand);
            ChefImages oldImages = chef.getImages();
            List<String> newImages = List.of(
                    "https://example.com/new1.jpg",
                    "https://example.com/new2.jpg"
            );

            // when
            chef.updateImages(newImages);

            // then
            assertThat(chef.getImages()).isNotNull();
            assertThat(chef.getImages()).isNotSameAs(oldImages);
            assertThat(chef.getImages().getImageUrls()).hasSize(2);
            assertThat(chef.getImages().getImageUrls()).containsExactly(
                    "https://example.com/new1.jpg",
                    "https://example.com/new2.jpg"
            );
        }

        @Test
        @DisplayName("4개 이상의 이미지로 수정하면 예외가 발생한다.")
        void throwException_whenUpdateWithMoreThanThreeImages() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    "요리천재",
                    Chef.Type.WHITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            Chef chef = Chef.of(registerCommand);
            List<String> tooManyImages = List.of(
                    "https://example.com/image1.jpg",
                    "https://example.com/image2.jpg",
                    "https://example.com/image3.jpg",
                    "https://example.com/image4.jpg"
            );

            // when & then
            CoreException exception = assertThrows(CoreException.class,
                    () -> chef.updateImages(tooManyImages));

            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("최대 3개");
        }

        @Test
        @DisplayName("중복된 URL이 포함된 경우 중복이 제거된다.")
        void updateImages_removeDuplicateUrls() {
            // given
            PerformerCommand.RegisterChef registerCommand = new PerformerCommand.RegisterChef(
                    "손종원",
                    "요리천재",
                    Chef.Type.WHITE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            Chef chef = Chef.of(registerCommand);
            List<String> imagesWithDuplicates = List.of(
                    "https://example.com/image1.jpg",
                    "https://example.com/image2.jpg",
                    "https://example.com/image1.jpg"  // 중복
            );

            // when
            chef.updateImages(imagesWithDuplicates);

            // then
            assertThat(chef.getImages().getImageUrls()).hasSize(2);
            assertThat(chef.getImages().getImageUrls()).containsExactly(
                    "https://example.com/image1.jpg",
                    "https://example.com/image2.jpg"
            );
        }
    }
}
