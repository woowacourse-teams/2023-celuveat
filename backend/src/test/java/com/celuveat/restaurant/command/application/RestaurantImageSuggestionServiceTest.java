package com.celuveat.restaurant.command.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.command.application.dto.SuggestImagesCommand;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImageSuggestion;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("음식점 이미지 제안 서비스(RestaurantImageSuggestionService) 은(는)")
class RestaurantImageSuggestionServiceTest extends IntegrationTest {

    private Restaurant 대성집;
    private OauthMember 말랑;

    @BeforeEach
    void setUp() {
        대성집 = restaurantRepository.save(대성집());
        말랑 = oauthMemberRepository.save(말랑());
    }

    @Test
    void 사용자가_사진들을_제안한다() {
        // given
        List<String> imageNames = List.of("imageA", "imageB");
        SuggestImagesCommand command = new SuggestImagesCommand(대성집.id(), 말랑.id(), imageNames);

        // when
        restaurantImageSuggestionService.suggestImages(command);
        List<RestaurantImageSuggestion> imageSuggestions = restaurantImageSuggestionRepository.findAllByMember(말랑);

        // then
        assertThat(imageSuggestions).hasSize(2);
        assertThat(imageSuggestions).extracting("imageName")
                .containsExactlyInAnyOrder("imageA", "imageB");
    }
}
