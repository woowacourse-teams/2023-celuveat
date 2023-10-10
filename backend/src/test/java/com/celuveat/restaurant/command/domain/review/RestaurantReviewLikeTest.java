package com.celuveat.restaurant.command.domain.review;

import static com.celuveat.auth.fixture.OauthMemberFixture.로이스;
import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.CAN_NOT_LIKE_MY_REVIEW;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.exception.BaseExceptionType;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.exception.RestaurantReviewException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 리뷰 좋아요(RestaurantReviewLike) 은(는)")
class RestaurantReviewLikeTest {

    private final Restaurant 대성집 = 대성집();
    private final OauthMember 말랑 = 말랑();
    private final OauthMember 로이스 = 로이스();

    @Test
    void 다른_회원의_리뷰에_좋아요를_누를_수_있다() {
        // given
        RestaurantReview review = RestaurantReview.create(대성집, 말랑, "좋아요", 5.0);
        assertThat(review.likeCount()).isEqualTo(0);

        // when
        RestaurantReviewLike.create(review, 로이스);

        // then
        assertThat(review.likeCount()).isEqualTo(1);
    }

    @Test
    void 좋아요를_취소할_수_있다() {
        RestaurantReview review = RestaurantReview.create(대성집, 말랑, "좋아요", 5.0);
        assertThat(review.likeCount()).isEqualTo(0);
        RestaurantReviewLike restaurantReviewLike = RestaurantReviewLike.create(review, 로이스);

        // when
        restaurantReviewLike.cancel();

        // then
        assertThat(review.likeCount()).isEqualTo(0);
    }

    @Test
    void 내_리뷰에는_좋아요를_누를_수_없다() {
        // given
        RestaurantReview review = RestaurantReview.create(대성집, 말랑, "좋아요", 5.0);
        assertThat(review.likeCount()).isEqualTo(0);

        // when
        BaseExceptionType ex = assertThrows(RestaurantReviewException.class, () ->
                RestaurantReviewLike.create(review, 말랑)
        ).exceptionType();

        // then
        assertThat(review.likeCount()).isEqualTo(0);
        assertThat(ex).isEqualTo(CAN_NOT_LIKE_MY_REVIEW);
    }
}
