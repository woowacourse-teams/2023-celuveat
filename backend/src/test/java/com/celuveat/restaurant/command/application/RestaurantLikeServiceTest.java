package com.celuveat.restaurant.command.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("음식점 좋아요 서비스(RestaurantLikeService) 은(는)")
class RestaurantLikeServiceTest extends IntegrationTest {

    @Test
    void 좋아요를_누른다() {
        // given
        Restaurant 음식점 = restaurantRepository.save(음식점("음식점"));
        OauthMember 말랑 = oauthMemberRepository.save(멤버("말랑"));

        // when
        restaurantLikeService.like(음식점.id(), 말랑.id());

        // then
        Optional<RestaurantLike> result = restaurantLikeRepository.findByRestaurantAndMember(음식점, 말랑);
        assertThat(result).isPresent();
    }

    @Test
    void 좋아요가_이미_있으면_좋아요를_지운다() {
        // given
        Restaurant 음식점 = restaurantRepository.save(음식점("음식점"));
        OauthMember 말랑 = oauthMemberRepository.save(멤버("말랑"));
        restaurantLikeService.like(음식점.id(), 말랑.id());

        // when
        restaurantLikeService.like(음식점.id(), 말랑.id());

        // then
        Optional<RestaurantLike> result = restaurantLikeRepository.findByRestaurantAndMember(음식점, 말랑);
        assertThat(result).isEmpty();
    }
}
