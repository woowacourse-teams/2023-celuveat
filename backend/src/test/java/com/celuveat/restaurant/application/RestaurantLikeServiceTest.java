package com.celuveat.restaurant.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantLike;
import com.celuveat.restaurant.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.domain.RestaurantRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("RestaurantLikeService(음식점 좋아요 서비스) 은(는)")
class RestaurantLikeServiceTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OauthMemberRepository oauthMemberRepository;

    @Autowired
    private RestaurantLikeRepository restaurantLikeRepository;

    @Autowired
    private RestaurantLikeService restaurantLikeService;

    @Test
    void 좋아요를_누른다() {
        // given
        Restaurant 음식점 = 음식점("음식점");
        OauthMember 말랑 = 멤버("말랑");
        restaurantRepository.save(음식점);
        oauthMemberRepository.save(말랑);

        // when
        restaurantLikeService.like(음식점.id(), 말랑.id());
        Optional<RestaurantLike> result = restaurantLikeRepository.findByRestaurantAndMember(음식점, 말랑);

        // then
        assertThat(result).isPresent();
    }

    @Test
    void 좋아요가_이미_있으면_좋아요를_지운다() {
        // given
        Restaurant 음식점 = 음식점("음식점");
        OauthMember 말랑 = 멤버("말랑");
        restaurantRepository.save(음식점);
        oauthMemberRepository.save(말랑);
        restaurantLikeService.like(음식점.id(), 말랑.id());

        // when
        restaurantLikeService.like(음식점.id(), 말랑.id());
        Optional<RestaurantLike> result = restaurantLikeRepository.findByRestaurantAndMember(음식점, 말랑);

        // then
        assertThat(result).isEmpty();
    }
}
