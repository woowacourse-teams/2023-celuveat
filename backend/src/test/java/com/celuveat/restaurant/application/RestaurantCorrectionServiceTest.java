package com.celuveat.restaurant.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.application.dto.CreateRestaurantCorrectionCommand;
import com.celuveat.restaurant.domain.correction.RestaurantCorrection;
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
    private RestaurantServiceTestHelper restaurantServiceTestHelper;

    @Test
    void 음식점_수정_요청을_생성한다() {
        // given
        Long restaurantId = restaurantServiceTestHelper.음식점을_저장한다("틀린이름");
        CreateRestaurantCorrectionCommand command = CreateRestaurantCorrectionCommand.builder()
                .restaurantId(restaurantId)
                .contents(List.of("음식점 이름이 틀렸어요.", "지도도 틀렸어요. 일좀 똑바로하세요."))
                .build();

        // when
        restaurantCorrectionService.create(command);

        // then
        List<RestaurantCorrection> restaurantCorrections = restaurantServiceTestHelper.음식점_정보_수정_제안을_모두_조회한다();
        assertThat(restaurantCorrections)
                .extracting(RestaurantCorrection::content)
                .containsExactly("음식점 이름이 틀렸어요.", "지도도 틀렸어요. 일좀 똑바로하세요.");
        assertThat(restaurantCorrections)
                .extracting(it -> it.restaurant().id())
                .containsExactly(restaurantId, restaurantId);
    }
}
