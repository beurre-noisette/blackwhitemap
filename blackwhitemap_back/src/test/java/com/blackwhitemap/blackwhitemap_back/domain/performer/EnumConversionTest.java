package com.blackwhitemap.blackwhitemap_back.domain.performer;

import com.blackwhitemap.blackwhitemap_back.support.error.CoreException;
import com.blackwhitemap.blackwhitemap_back.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Enum 변환 단위 테스트")
class EnumConversionTest {

    @Nested
    @DisplayName("Chef.Type.from() 메서드는")
    class ChefTypeFrom {

        @Test
        @DisplayName("대문자 'BLACK'을 BLACK enum으로 변환한다")
        void convertsUppercaseBlack() {
            // when
            Chef.Type result = Chef.Type.from("BLACK");

            // then
            assertThat(result).isEqualTo(Chef.Type.BLACK);
        }

        @Test
        @DisplayName("소문자 'black'을 BLACK enum으로 변환한다 (대소문자 무관)")
        void convertsLowercaseBlack() {
            // when
            Chef.Type result = Chef.Type.from("black");

            // then
            assertThat(result).isEqualTo(Chef.Type.BLACK);
        }

        @Test
        @DisplayName("공백이 포함된 ' WHITE '를 WHITE enum으로 변환한다")
        void convertsWithWhitespace() {
            // when
            Chef.Type result = Chef.Type.from(" WHITE ");

            // then
            assertThat(result).isEqualTo(Chef.Type.WHITE);
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        @DisplayName("null, 빈 문자열, 공백만 있는 경우 BAD_REQUEST 예외를 발생시킨다")
        void throwsException_whenNullOrBlank(String value) {
            // when
            CoreException exception = assertThrows(CoreException.class,
                () -> Chef.Type.from(value));

            // then
            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("셰프 타입은 필수입니다");
        }

        @Test
        @DisplayName("유효하지 않은 값 'YELLOW'는 BAD_REQUEST 예외를 발생시킨다")
        void throwsException_whenInvalidValue() {
            // when
            CoreException exception = assertThrows(CoreException.class,
                () -> Chef.Type.from("YELLOW"));

            // then
            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("유효하지 않은 셰프 타입입니다");
        }
    }

    @Nested
    @DisplayName("Restaurant.Category.fromNullable() 메서드는")
    class RestaurantCategoryFromNullable {

        @Test
        @DisplayName("'KOREAN'을 KOREAN enum으로 변환한다")
        void convertsKorean() {
            // when
            Restaurant.Category result = Restaurant.Category.fromNullable("KOREAN");

            // then
            assertThat(result).isEqualTo(Restaurant.Category.KOREAN);
        }

        @Test
        @DisplayName("소문자 'italian'을 ITALIAN enum으로 변환한다")
        void convertsLowercaseItalian() {
            // when
            Restaurant.Category result = Restaurant.Category.fromNullable("italian");

            // then
            assertThat(result).isEqualTo(Restaurant.Category.ITALIAN);
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t"})
        @DisplayName("null, 빈 문자열, 공백은 null을 반환한다 (선택적 필드)")
        void returnsNull_whenNullOrBlank(String value) {
            // when
            Restaurant.Category result = Restaurant.Category.fromNullable(value);

            // then
            assertThat(result).isNull();
        }

        @Test
        @DisplayName("유효하지 않은 값 'MEXICAN'은 BAD_REQUEST 예외를 발생시킨다")
        void throwsException_whenInvalidValue() {
            // when
            CoreException exception = assertThrows(CoreException.class,
                () -> Restaurant.Category.fromNullable("MEXICAN"));

            // then
            assertThat(exception.getErrorType()).isEqualTo(ErrorType.BAD_REQUEST);
            assertThat(exception.getCustomMessage()).contains("유효하지 않은 레스토랑 카테고리입니다");
        }
    }
}
