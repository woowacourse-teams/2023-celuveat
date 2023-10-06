package com.celuveat.restaurant.command.application;

import static com.celuveat.restaurant.fixture.RestaurantFixture.국민연금_구내식당;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.command.application.dto.SuggestCorrectionRequestCommand;
import com.celuveat.restaurant.command.domain.correction.RestaurantCorrection;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("음식점 수정 서비스(RestaurantCorrectionService) 은(는)")
class RestaurantCorrectionServiceTest extends IntegrationTest {

    @Test
    void 음식점_수정_요청을_생성한다() {
        // given
        Long restaurantId = restaurantRepository.save(국민연금_구내식당).id();
        SuggestCorrectionRequestCommand command = SuggestCorrectionRequestCommand.builder()
                .restaurantId(restaurantId)
                .contents(List.of("음식점 이름이 틀렸어요.", "지도도 틀렸어요. 일좀 똑바로하세요."))
                .build();

        // when
        restaurantCorrectionService.suggest(command);
        List<RestaurantCorrection> result = restaurantCorrectionRepository.findAll();

        // then
        assertThat(result)
                .extracting(RestaurantCorrection::content)
                .containsExactly("음식점 이름이 틀렸어요.", "지도도 틀렸어요. 일좀 똑바로하세요.");
        assertThat(result)
                .extracting(it -> it.restaurant().id())
                .containsExactly(restaurantId, restaurantId);
    }
}
