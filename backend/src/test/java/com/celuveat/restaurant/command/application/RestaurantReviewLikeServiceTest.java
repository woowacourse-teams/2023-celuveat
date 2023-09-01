package com.celuveat.restaurant.command.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.CAN_NOT_LIKE_MY_REVIEW;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantReviewFixture.음식점_리뷰;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.common.IntegrationTest;
import com.celuveat.common.exception.BaseException;
import com.celuveat.common.exception.BaseExceptionType;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLike;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLikeRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 리뷰 좋아요 서비스(RestaurantReviewLikeService) 은(는)")
class RestaurantReviewLikeServiceTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantReviewRepository restaurantReviewRepository;

    @Autowired
    private OauthMemberRepository oauthMemberRepository;

    @Autowired
    private RestaurantReviewLikeRepository restaurantReviewLikeRepository;

    @Autowired
    private RestaurantReviewLikeService restaurantReviewLikeService;

    @Test
    void 좋아요를_누른다() {
        // given
        Restaurant 음식점 = 음식점("음식점");
        OauthMember 말랑 = 멤버("말랑");
        RestaurantReview 음식점_리뷰 = 음식점_리뷰(말랑, 음식점);
        OauthMember 로이스 = 멤버("로이스");
        restaurantRepository.save(음식점);
        oauthMemberRepository.save(말랑);
        restaurantReviewRepository.save(음식점_리뷰);
        oauthMemberRepository.save(로이스);

        // when
        restaurantReviewLikeService.like(음식점_리뷰.id(), 로이스.id());
        Optional<RestaurantReviewLike> result =
                restaurantReviewLikeRepository.findByRestaurantReviewAndMember(음식점_리뷰, 로이스);

        // then
        assertThat(result).isPresent();
    }

    @Test
    void 자신의_리뷰에_좋아요를_누를수없다() {
        // given
        Restaurant 음식점 = 음식점("음식점");
        OauthMember 말랑 = 멤버("말랑");
        RestaurantReview 음식점_리뷰 = 음식점_리뷰(말랑, 음식점);
        restaurantRepository.save(음식점);
        oauthMemberRepository.save(말랑);
        restaurantReviewRepository.save(음식점_리뷰);

        // when
        BaseExceptionType result = assertThrows(BaseException.class, () ->
                restaurantReviewLikeService.like(음식점_리뷰.id(), 말랑.id())
        ).exceptionType();

        // then
        assertThat(result).isEqualTo(CAN_NOT_LIKE_MY_REVIEW);
    }

    @Test
    void 좋아요가_이미_있으면_좋아요를_지운다() {
        // given
        Restaurant 음식점 = 음식점("음식점");
        OauthMember 말랑 = 멤버("말랑");
        RestaurantReview 음식점_리뷰 = 음식점_리뷰(말랑, 음식점);
        OauthMember 로이스 = 멤버("로이스");
        restaurantRepository.save(음식점);
        oauthMemberRepository.save(말랑);
        restaurantReviewRepository.save(음식점_리뷰);
        oauthMemberRepository.save(로이스);
        restaurantReviewLikeService.like(음식점_리뷰.id(), 로이스.id());

        // when
        restaurantReviewLikeService.like(음식점_리뷰.id(), 로이스.id());
        Optional<RestaurantReviewLike> result =
                restaurantReviewLikeRepository.findByRestaurantReviewAndMember(음식점_리뷰, 로이스);

        // then
        assertThat(result).isEmpty();
    }
}
