package com.celuveat.restaurant.command.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.CAN_NOT_LIKE_MY_REVIEW;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantReviewFixture.음식점_리뷰;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.IntegrationTest;
import com.celuveat.common.exception.BaseException;
import com.celuveat.common.exception.BaseExceptionType;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLike;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("음식점 리뷰 좋아요 서비스(RestaurantReviewLikeService) 은(는)")
class RestaurantReviewLikeServiceTest extends IntegrationTest {

    @Test
    void 좋아요를_누른다() {
        // given
        Restaurant 음식점 = restaurantRepository.save(음식점("음식점"));
        OauthMember 말랑 = oauthMemberRepository.save(멤버("말랑"));
        RestaurantReview 음식점_리뷰 = restaurantReviewRepository.save(음식점_리뷰(말랑, 음식점));
        OauthMember 로이스 = oauthMemberRepository.save(멤버("로이스"));

        // when
        restaurantReviewLikeService.like(음식점_리뷰.id(), 로이스.id());

        // then
        Optional<RestaurantReviewLike> result =
                restaurantReviewLikeRepository.findByRestaurantReviewAndMember(음식점_리뷰, 로이스);
        assertThat(result).isPresent();
    }

    @Test
    void 자신의_리뷰에_좋아요를_누를수없다() {
        // given
        Restaurant 음식점 = restaurantRepository.save(음식점("음식점"));
        OauthMember 말랑 = oauthMemberRepository.save(멤버("말랑"));
        RestaurantReview 음식점_리뷰 = restaurantReviewRepository.save(음식점_리뷰(말랑, 음식점));

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
        Restaurant 음식점 = restaurantRepository.save(음식점("음식점"));
        OauthMember 말랑 = oauthMemberRepository.save(멤버("말랑"));
        RestaurantReview 음식점_리뷰 = restaurantReviewRepository.save(음식점_리뷰(말랑, 음식점));
        OauthMember 로이스 = oauthMemberRepository.save(멤버("로이스"));
        restaurantReviewLikeService.like(음식점_리뷰.id(), 로이스.id());

        // when
        restaurantReviewLikeService.like(음식점_리뷰.id(), 로이스.id());

        // then
        Optional<RestaurantReviewLike> result =
                restaurantReviewLikeRepository.findByRestaurantReviewAndMember(음식점_리뷰, 로이스);
        assertThat(result).isEmpty();
    }

    @Test
    void 좋아요를_추가되면_리뷰의_좋아요_개수가_증가한다() {
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

        // then
        assertThat(음식점_리뷰.likeCount()).isEqualTo(1);
    }

    @Test
    void 좋아요를_삭제되면_리뷰의_좋아요_개수가_증가한다() {
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
        assertThat(음식점_리뷰.likeCount()).isEqualTo(1);

        // when
        restaurantReviewLikeService.like(음식점_리뷰.id(), 로이스.id());

        // then
        assertThat(음식점_리뷰.likeCount()).isEqualTo(0);
    }
}
