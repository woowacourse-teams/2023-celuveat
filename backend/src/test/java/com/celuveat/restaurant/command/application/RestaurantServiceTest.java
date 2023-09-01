package com.celuveat.restaurant.command.application;

import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("RestaurantService(음식점 서비스) 은(는)")
class RestaurantServiceTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void 조회수를_1_증가시킨다() {
        // given
        Restaurant 음식점 = 음식점("로이스 1호점");
        restaurantRepository.save(음식점);

        // when
        restaurantService.increaseViewCount(음식점.id());

        // then
        assertThat(음식점.viewCount()).isEqualTo(1);
    }
}
