package com.celuveat.celuveat.restaurant.application;

import static com.celuveat.celuveat.restaurant.exception.RestaurantExceptionType.NOT_FOUND_RESTAURANT;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.교촌치킨;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.celuveat.video.fixture.VideoFixture.영상;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.celuveat.common.exception.BaseExceptionType;
import com.celuveat.celuveat.common.page.PageCond;
import com.celuveat.celuveat.common.page.PageResponse;
import com.celuveat.celuveat.restaurant.application.dto.RestaurantSearchResponse;
import com.celuveat.celuveat.restaurant.domain.Restaurant;
import com.celuveat.celuveat.restaurant.exception.RestaurantException;
import com.celuveat.celuveat.restaurant.infra.persistence.FakeRestaurantDao;
import com.celuveat.celuveat.restaurant.infra.persistence.FakeRestaurantQueryDao;
import com.celuveat.celuveat.restaurant.infra.persistence.RestaurantDao;
import com.celuveat.celuveat.video.infra.persistence.FakeVideoDao;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("RestaurantQueryService 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RestaurantQueryServiceTest {

    private final FakeVideoDao videoDao = new FakeVideoDao();
    private final FakeRestaurantQueryDao restaurantQueryDao = new FakeRestaurantQueryDao(videoDao);
    private final RestaurantDao restaurantDao = new FakeRestaurantDao();
    private final RestaurantQueryService restaurantQueryService =
            new RestaurantQueryService(restaurantDao, restaurantQueryDao);

    @Nested
    class ID로_음식점_조회_시 {

        @Test
        void 음식점을_조회한다() {
            // given
            Restaurant 교촌치킨 = 교촌치킨();
            Long 교촌치킨_ID = restaurantDao.save(교촌치킨);

            // when
            Restaurant result = restaurantQueryService.findById(교촌치킨_ID);

            // then
            assertThat(result).isEqualTo(교촌치킨);
        }

        @Test
        void 음식점이_존재하지_않으면_오류이다() {
            // when
            BaseExceptionType baseExceptionType = assertThrows(RestaurantException.class, () ->
                    restaurantQueryService.findById(22L)
            ).exceptionType();

            // then
            assertThat(baseExceptionType).isEqualTo(NOT_FOUND_RESTAURANT);
        }
    }

    @Test
    void 셀럽_ID로_음식점들을_조회한다() {
        // given
        Long 셀럽_ID = 1L;
        List<Restaurant> expected = 영상을_N개_저장하고_영상의_음식점들을_반환한다(10, 셀럽_ID);
        영상을_N개_저장하고_영상의_음식점들을_반환한다(10, 2L);

        // when
        PageResponse<RestaurantSearchResponse> result =
                restaurantQueryService.findAllByCelebId(셀럽_ID, new PageCond(1, 20));

        // then
        assertThat(result.hasNextPage()).isFalse();
        assertThat(result.contents()).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expected);
    }

    private List<Restaurant> 영상을_N개_저장하고_영상의_음식점들을_반환한다(int count, Long 셀럽_ID) {
        List<Restaurant> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Restaurant 음식점 = 음식점("맛집" + i);
            Long 맛집_ID = restaurantQueryDao.save(음식점);
            videoDao.save(영상(셀럽_ID, 맛집_ID));
            result.add(음식점);
        }
        return result;
    }
}
