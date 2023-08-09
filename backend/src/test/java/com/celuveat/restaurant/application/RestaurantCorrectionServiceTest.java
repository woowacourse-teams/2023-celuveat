package com.celuveat.restaurant.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.application.dto.CreateRestaurantCorrectionCommand;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.restaurant.domain.correction.RestaurantCorrection;
import com.celuveat.restaurant.domain.correction.RestaurantCorrectionRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 수정 서비스(RestaurantCorrectionService) 은(는)")
class RestaurantCorrectionServiceTest {

    @Autowired
    private RestaurantCorrectionService restaurantCorrectionService;

    @Autowired
    private RestaurantCorrectionRepository restaurantCorrectionRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void 음식점_수정_요청을_생성한다() {
        // given
        Restaurant restaurant = restaurantRepository.save(Restaurant.builder()
                .name("틀린이름")
                .category("category")
                .roadAddress("road")
                .latitude(1.1)
                .longitude(1.1)
                .naverMapUrl("naver")
                .build()
        );
        CreateRestaurantCorrectionCommand command = CreateRestaurantCorrectionCommand.builder()
                .restaurantId(restaurant.id())
                .contents(List.of("음식점 이름이 틀렸어요.", "지도도 틀렸어요. 일좀 똑바로하세요."))
                .build();

        // when
        restaurantCorrectionService.create(command);

        // then
        assertThat(restaurantCorrectionRepository.findAll())
                .extracting(RestaurantCorrection::content)
                .containsExactly("음식점 이름이 틀렸어요.", "지도도 틀렸어요. 일좀 똑바로하세요.");
        assertThat(restaurantCorrectionRepository.findAll())
                .extracting(it -> it.restaurant().id())
                .containsExactly(restaurant.id(), restaurant.id());
    }
}
