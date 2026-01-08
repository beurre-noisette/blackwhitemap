package com.blackwhitemap.blackwhitemap_back.domain.ranking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ChefRanking 도메인 단위 테스트")
class ChefRankingTest {

    @Nested
    @DisplayName("ChefRanking.Type enum은")
    class TypeEnum {

        @Test
        @DisplayName("WEEKLY 타입이 존재하고 '주간' 설명을 가진다")
        void weeklyType_exists() {
            // given
            ChefRanking.Type type = ChefRanking.Type.WEEKLY;

            // then
            assertThat(type).isNotNull();
            assertThat(type.getDescription()).isEqualTo("주간");
        }

        @Test
        @DisplayName("DAILY 타입이 존재하고 '일간' 설명을 가진다")
        void dailyType_exists() {
            // given
            ChefRanking.Type type = ChefRanking.Type.DAILY;

            // then
            assertThat(type).isNotNull();
            assertThat(type.getDescription()).isEqualTo("일간");
        }

        @ParameterizedTest
        @EnumSource(ChefRanking.Type.class)
        @DisplayName("모든 타입은 null이 아닌 description을 가진다")
        void allTypes_haveDescription(ChefRanking.Type type) {
            // then
            assertThat(type.getDescription()).isNotNull();
            assertThat(type.getDescription()).isNotBlank();
        }

        @Test
        @DisplayName("Type enum은 정확히 2개의 값(WEEKLY, DAILY)을 가진다")
        void typeEnum_hasExactlyTwoValues() {
            // when
            ChefRanking.Type[] values = ChefRanking.Type.values();

            // then
            assertThat(values).hasSize(2);
            assertThat(values).containsExactly(ChefRanking.Type.WEEKLY, ChefRanking.Type.DAILY);
        }
    }
}