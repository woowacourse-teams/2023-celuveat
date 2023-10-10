package com.celuveat.restaurant.command.application;

import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.command.domain.Restaurant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RestaurantService(음식점 서비스) 은(는)")
class RestaurantServiceTest extends IntegrationTest {

    @Test
    void 조회수를_1_증가시킨다() {
        // given
        Restaurant 대성집 = restaurantRepository.save(대성집());

        // when
        restaurantService.increaseViewCount(대성집.id());

        // then
        assertThat(대성집.viewCount()).isEqualTo(1);
    }
}
