package com.celuveat.celuveat.restaurant.application;

import static com.celuveat.celuveat.restaurant.exception.RestaurantExceptionType.NOT_FOUND_RESTAURANT;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.교촌치킨;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.celuveat.common.exception.BaseExceptionType;
import com.celuveat.celuveat.restaurant.domain.Restaurant;
import com.celuveat.celuveat.restaurant.exception.RestaurantException;
import com.celuveat.celuveat.restaurant.infra.persistence.FakeRestaurantDao;
import com.celuveat.celuveat.restaurant.infra.persistence.RestaurantDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("RestaurantQueryService 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RestaurantQueryServiceTest {

    private final RestaurantDao restaurantDao = new FakeRestaurantDao();
    private final RestaurantQueryService restaurantQueryService = new RestaurantQueryService(restaurantDao);

    @Nested
    class ID로_음식점_조회_시 {

        @Test
        void 음식점을_조회한다() {
            // given
            Restaurant 교촌치킨 = 교촌치킨();
            Long id = restaurantDao.save(교촌치킨);

            // when
            Restaurant result = restaurantQueryService.findById(id);

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
}
