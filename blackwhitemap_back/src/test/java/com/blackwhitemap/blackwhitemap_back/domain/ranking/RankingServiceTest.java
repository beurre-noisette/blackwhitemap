package com.blackwhitemap.blackwhitemap_back.domain.ranking;

import com.blackwhitemap.blackwhitemap_back.domain.performer.Chef;
import com.blackwhitemap.blackwhitemap_back.domain.performer.ChefRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("RankingService 단위 테스트")
@ExtendWith(MockitoExtension.class)
class RankingServiceTest {

    @Mock
    private ChefRankingRepository chefRankingRepository;

    @Mock
    private ChefRepository chefRepository;

    @InjectMocks
    private RankingService rankingService;

    @Nested
    class AddDailyScore {

        private LocalDate periodStart;
        private Long chefId;
        private Long score;

        @BeforeEach
        void setUp() {
            periodStart = LocalDate.of(2025, 1, 8);
            chefId = 1L;
            score = 5L;
        }

        @Test
        @DisplayName("기존 랭킹이 없으면 새로운 랭킹을 생성한다")
        void createNewRanking_whenNotExists() {
            // given
            RankingCommand.AddDailyScore command = new RankingCommand.AddDailyScore(
                    chefId,
                    periodStart,
                    score
            );

            given(chefRankingRepository.findByTypeAndPeriodStartAndChefId(
                    ChefRanking.Type.DAILY, periodStart, chefId
            )).willReturn(Optional.empty());

            given(chefRankingRepository.save(any(ChefRanking.class)))
                    .willAnswer(invocation -> invocation.getArgument(0));

            // when
            ChefRanking result = rankingService.addDailyScore(command);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getChefId()).isEqualTo(chefId);
            assertThat(result.getScore()).isEqualTo(score);
            assertThat(result.getType()).isEqualTo(ChefRanking.Type.DAILY);
            assertThat(result.getPeriodStart()).isEqualTo(periodStart);

            verify(chefRankingRepository).save(any(ChefRanking.class));
        }

        @Test
        @DisplayName("기존 랭킹이 있으면 점수를 누적한다")
        void accumulateScore_whenExists() {
            // given
            Long existingScore = 10L;
            Long additionalScore = 5L;

            RankingCommand.AddDailyScore command = new RankingCommand.AddDailyScore(
                    chefId,
                    periodStart,
                    additionalScore
            );

            ChefRanking existingRanking = ChefRanking.ofDailyWithScore(
                    new RankingCommand.AddDailyScore(
                            chefId,
                            periodStart,
                            existingScore
                    )
            );

            given(chefRankingRepository.findByTypeAndPeriodStartAndChefId(
                    ChefRanking.Type.DAILY,
                    periodStart,
                    chefId
            )).willReturn(Optional.of(existingRanking));

            given(chefRankingRepository.save(any(ChefRanking.class)))
                    .willAnswer(invocation -> invocation.getArgument(0));

            // when
            ChefRanking result = rankingService.addDailyScore(command);

            // then
            assertThat(result.getScore()).isEqualTo(existingScore + additionalScore);

            verify(chefRankingRepository).save(existingRanking);
        }
    }

    @Nested
    class RecalculateDailyRanks {

        private LocalDate periodStart;

        @BeforeEach
        void setUp() {
            periodStart = LocalDate.of(2025, 1, 8);
        }

        @Test
        @DisplayName("랭킹이 없으면 아무 작업도 하지 않는다")
        void doNothing_whenNoRankings() {
            // given
            given(chefRankingRepository.findAllByTypeAndPeriodStart(
                    ChefRanking.Type.DAILY,
                    periodStart)
            )
                    .willReturn(List.of());

            // when
            rankingService.recalculateDailyRanks(periodStart);

            // then
            verify(chefRankingRepository, never()).save(any());
            verify(chefRepository, never()).findAllByIdIn(any());
        }

        @Test
        @DisplayName("점수가 다른 경우 점수 내림차순으로 순위를 부여한다")
        void assignRanks_byScoreDescending() {
            // given
            ChefRanking ranking1 = createRankingWithScore(1L, 10L);
            ChefRanking ranking2 = createRankingWithScore(2L, 30L);
            ChefRanking ranking3 = createRankingWithScore(3L, 20L);

            List<ChefRanking> rankings = new ArrayList<>(List.of(ranking1, ranking2, ranking3));

            Chef chef1 = createChef(1L, "김셰프", Chef.Type.WHITE, "서울시 강남구");
            Chef chef2 = createChef(2L, "이셰프", Chef.Type.WHITE, "서울시 서초구");
            Chef chef3 = createChef(3L, "박셰프", Chef.Type.WHITE, "서울시 송파구");

            given(chefRankingRepository.findAllByTypeAndPeriodStart(ChefRanking.Type.DAILY, periodStart))
                    .willReturn(rankings);
            given(chefRepository.findAllByIdIn(any()))
                    .willReturn(List.of(chef1, chef2, chef3));
            given(chefRankingRepository.save(any(ChefRanking.class)))
                    .willAnswer(invocation -> invocation.getArgument(0));

            // when
            rankingService.recalculateDailyRanks(periodStart);

            // then
            ArgumentCaptor<ChefRanking> captor = ArgumentCaptor.forClass(ChefRanking.class);
            verify(chefRankingRepository, times(3)).save(captor.capture());

            List<ChefRanking> savedRankings = captor.getAllValues();

            // 점수 30(chefId=2) -> 1위, 점수 20(chefId=3) -> 2위, 점수 10(chefId=1) -> 3위
            ChefRanking firstPlace = savedRankings.stream()
                    .filter(r -> r.getChefId().equals(2L)).findFirst().orElseThrow();
            ChefRanking secondPlace = savedRankings.stream()
                    .filter(r -> r.getChefId().equals(3L)).findFirst().orElseThrow();
            ChefRanking thirdPlace = savedRankings.stream()
                    .filter(r -> r.getChefId().equals(1L)).findFirst().orElseThrow();

            assertThat(firstPlace.getRank()).isEqualTo(1);
            assertThat(secondPlace.getRank()).isEqualTo(2);
            assertThat(thirdPlace.getRank()).isEqualTo(3);
        }

        @Test
        @DisplayName("동점자는 address 유무, chefType, 이름순으로 정렬된다")
        void sortTiedRankings_byTiebreaker() {
            // given
            Long sameScore = 10L;

            // 동점자 3명 (점수 동일)
            ChefRanking rankingWithoutAddress = createRankingWithScore(1L, sameScore);
            ChefRanking rankingBlackWithAddress = createRankingWithScore(2L, sameScore);
            ChefRanking rankingWhiteWithAddress = createRankingWithScore(3L, sameScore);

            List<ChefRanking> rankings = new ArrayList<>(List.of(
                    rankingWithoutAddress,
                    rankingBlackWithAddress,
                    rankingWhiteWithAddress
            ));

            // chef1: address 없음
            Chef chefWithoutAddress = createChef(1L, "김셰프", Chef.Type.WHITE, null);
            // chef2: address 있음, BLACK
            Chef chefBlackWithAddress = createChef(2L, "이셰프", Chef.Type.BLACK, "서울시 강남구");
            // chef3: address 있음, WHITE
            Chef chefWhiteWithAddress = createChef(3L, "박셰프", Chef.Type.WHITE, "서울시 서초구");

            given(chefRankingRepository.findAllByTypeAndPeriodStart(ChefRanking.Type.DAILY, periodStart))
                    .willReturn(rankings);
            given(chefRepository.findAllByIdIn(any()))
                    .willReturn(List.of(chefWithoutAddress, chefBlackWithAddress, chefWhiteWithAddress));
            given(chefRankingRepository.save(any(ChefRanking.class)))
                    .willAnswer(invocation -> invocation.getArgument(0));

            // when
            rankingService.recalculateDailyRanks(periodStart);

            // then
            ArgumentCaptor<ChefRanking> captor = ArgumentCaptor.forClass(ChefRanking.class);
            verify(chefRankingRepository, times(3)).save(captor.capture());

            List<ChefRanking> savedRankings = captor.getAllValues();

            // 정렬 순서: address 있음 > WHITE 우선 > 이름순
            // 1위: chefId=3 (address 있음, WHITE)
            // 2위: chefId=2 (address 있음, BLACK)
            // 3위: chefId=1 (address 없음)

            ChefRanking first = savedRankings.stream()
                    .filter(r -> r.getChefId().equals(3L)).findFirst().orElseThrow();
            ChefRanking second = savedRankings.stream()
                    .filter(r -> r.getChefId().equals(2L)).findFirst().orElseThrow();
            ChefRanking third = savedRankings.stream()
                    .filter(r -> r.getChefId().equals(1L)).findFirst().orElseThrow();

            assertThat(first.getRank()).isEqualTo(1);
            assertThat(second.getRank()).isEqualTo(2);
            assertThat(third.getRank()).isEqualTo(3);
        }

        private ChefRanking createRankingWithScore(Long chefId, Long score) {
            return ChefRanking.ofDailyWithScore(
                    new RankingCommand.AddDailyScore(chefId, periodStart, score)
            );
        }

        private Chef createChef(Long id, String name, Chef.Type type, String address) {
            return ChefTestFixture.createChef(id, name, null, type, address);
        }
    }
}