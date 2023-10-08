package com.celuveat.restaurant.query;

import static com.celuveat.celeb.fixture.CelebFixture.맛객리우;
import static com.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celeb.fixture.CelebFixture.쯔양;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static com.celuveat.restaurant.fixture.RestaurantFixture.하늘초밥;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.대성집_사진;
import static com.celuveat.restaurant.fixture.RestaurantImageFixture.하늘초밥_사진;
import static com.celuveat.restaurant.util.RestaurantQueryTestUtils.restaurantDetailQueryResponse;
import static com.celuveat.restaurant.util.RestaurantQueryTestUtils.restaurantSearchQueryResponse;
import static com.celuveat.restaurant.util.RestaurantQueryTestUtils.restaurantSearchWithoutDistanceResponse;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.query.dao.LikedRestaurantQueryResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantDetailQueryResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantSearchWithoutDistanceQueryResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantSearchWithoutDistanceQueryResponseDao.RegionCodeCond;
import com.celuveat.restaurant.query.dto.CelebQueryResponse;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchWithoutDistanceResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 조회 서비스(RestaurantQueryService) 은(는)")
class RestaurantQueryServiceTest {

    private final RestaurantDetailQueryResponseDao restaurantDetailQueryResponseDao =
            mock(RestaurantDetailQueryResponseDao.class);
    private final RestaurantSearchQueryResponseDao restaurantSearchQueryResponseDao =
            mock(RestaurantSearchQueryResponseDao.class);
    private final LikedRestaurantQueryResponseDao likedRestaurantQueryResponseDao =
            mock(LikedRestaurantQueryResponseDao.class);
    private final RestaurantSearchWithoutDistanceQueryResponseDao restaurantSearchWithoutDistanceQueryResponseDao =
            mock(RestaurantSearchWithoutDistanceQueryResponseDao.class);
    private final RestaurantQueryService restaurantQueryService = new RestaurantQueryService(
            restaurantDetailQueryResponseDao,
            restaurantSearchQueryResponseDao,
            likedRestaurantQueryResponseDao,
            restaurantSearchWithoutDistanceQueryResponseDao
    );

    @Test
    void 특정_음식점을_조회한다() {
        // given
        Celeb 성시경 = 성시경(1L);
        Celeb 쯔양 = 쯔양(2L);
        given(restaurantDetailQueryResponseDao.find(1L, null))
                .willReturn(restaurantDetailQueryResponse(
                        대성집(),
                        false,
                        3.5,
                        List.of(쯔양, 성시경),
                        List.of(대성집_사진(대성집(), 1))
                ));

        // when
        RestaurantDetailQueryResponse result = restaurantQueryService.findById(1L, 성시경.id(), null);

        // then
        assertThat(result)
                .satisfies(it -> {
                    assertThat(it.name()).isEqualTo("대성집");
                    assertThat(it.celebs())
                            .extracting(CelebQueryResponse::name)
                            .containsExactly("성시경", "쯔양");
                });
    }

    @Test
    void 검색_조건에_따라_음식점을_검색한다() {
        // given
        Celeb 성시경 = 성시경(1L);
        Celeb 쯔양 = 쯔양(2L);
        Celeb 맛객리우 = 맛객리우(3L);
        RestaurantSearchCond restaurantSearchCond = new RestaurantSearchCond(맛객리우.id(), null, null);
        LocationSearchCond locationSearchCond = new LocationSearchCond(36.5, 37.5, 126.5, 127.5);
        PageRequest pageRequest = PageRequest.of(0, 18);
        given(restaurantSearchQueryResponseDao.find(
                restaurantSearchCond,
                locationSearchCond,
                pageRequest, null
        )).willReturn(PageableExecutionUtils.getPage(List.of(
                restaurantSearchQueryResponse(
                        대성집(),
                        false, 3.5,
                        List.of(쯔양, 맛객리우),
                        List.of(대성집_사진(대성집(), 1))),
                restaurantSearchQueryResponse(
                        하늘초밥(),
                        false, 3.5,
                        List.of(성시경, 맛객리우),
                        List.of(하늘초밥_사진(하늘초밥(), 1)))
        ), pageRequest, () -> 2));

        // when
        List<RestaurantSearchQueryResponse> result = restaurantQueryService.find(
                restaurantSearchCond,
                locationSearchCond,
                pageRequest, null
        ).getContent();

        // then
        assertThat(result)
                .hasSize(2)
                .satisfies(it -> {
                    assertThat(it)
                            .extracting(RestaurantSearchQueryResponse::name)
                            .containsExactly("대성집", "하늘초밥");
                    assertThat(it)
                            .flatMap(RestaurantSearchQueryResponse::celebs)
                            .extracting(CelebQueryResponse::name)
                            .containsExactly("맛객리우", "쯔양", "맛객리우", "성시경");
                });
    }

    @Test
    void 특정_음식점_근처의_음식점들을_조회한다() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 18);
        given(restaurantSearchQueryResponseDao.findNearBy(
                1L,
                1000,
                pageRequest, null
        )).willReturn(PageableExecutionUtils.getPage(List.of(
                restaurantSearchQueryResponse(
                        대성집(),
                        false, 3.5,
                        700,
                        emptyList(),
                        emptyList()),
                restaurantSearchQueryResponse(
                        하늘초밥(),
                        false, 3.5,
                        1000,
                        emptyList(),
                        emptyList())
        ), pageRequest, () -> 2));

        // when
        List<RestaurantSearchQueryResponse> result = restaurantQueryService.findNearBy(
                1L,
                1000,
                pageRequest,
                null
        ).getContent();

        // then
        assertThat(result)
                .hasSize(2)
                .extracting(RestaurantSearchQueryResponse::name)
                .containsExactly("대성집", "하늘초밥");
    }

    @Test
    void 행정동에_포함된_음식점들을_조회한다() {
        // given
        RegionCodeCond regionCodeCond = new RegionCodeCond(List.of("1100000000"));
        PageRequest pageRequest = PageRequest.of(0, 18);
        given(restaurantSearchWithoutDistanceQueryResponseDao.findByRegionCode(
                regionCodeCond,
                pageRequest, null
        )).willReturn(PageableExecutionUtils.getPage(List.of(
                restaurantSearchWithoutDistanceResponse(
                        대성집(),
                        false, 3.5,
                        emptyList(),
                        emptyList()),
                restaurantSearchWithoutDistanceResponse(
                        하늘초밥(),
                        false, 3.5,
                        emptyList(),
                        emptyList())
        ), pageRequest, () -> 2));

        // when
        List<RestaurantSearchWithoutDistanceResponse> result = restaurantQueryService.findByRegionCode(
                regionCodeCond,
                pageRequest,
                null
        ).getContent();

        // then
        assertThat(result)
                .hasSize(2)
                .extracting(RestaurantSearchWithoutDistanceResponse::getName)
                .containsExactly("대성집", "하늘초밥");
    }

    @Test
    void 최근_생성된_음식점들을_조회한다() {
        // given
        given(restaurantSearchWithoutDistanceQueryResponseDao.findLatest(null))
                .willReturn(List.of(
                        restaurantSearchWithoutDistanceResponse(
                                대성집(),
                                false, 3.5,
                                emptyList(),
                                emptyList()),
                        restaurantSearchWithoutDistanceResponse(
                                하늘초밥(),
                                false, 3.5,
                                emptyList(),
                                emptyList())
                ));

        // when
        List<RestaurantSearchWithoutDistanceResponse> result = restaurantQueryService.findLatest(null);

        // then
        assertThat(result)
                .hasSize(2)
                .extracting(RestaurantSearchWithoutDistanceResponse::getName)
                .containsExactly("대성집", "하늘초밥");
    }

    @Test
    void 특정_회원이_좋아요를_누를_음식점들을_조회한다() {
        // given
        given(likedRestaurantQueryResponseDao.findLikedByMemberId(1L))
                .willReturn(List.of(
                        new LikedRestaurantQueryResponse(
                                1L,
                                "말랑집",
                                "한식",
                                "말랑시 말랑구 말랑동",
                                37.0, 126.0,
                                "010-1234-1234",
                                "naver",
                                emptyList(),
                                emptyList()
                        )
                ));

        // when
        List<LikedRestaurantQueryResponse> result = restaurantQueryService.findLikedByMemberId(
                1L
        );

        // then
        assertThat(result)
                .hasSize(1)
                .extracting(LikedRestaurantQueryResponse::name)
                .containsExactly("말랑집");
    }
}
