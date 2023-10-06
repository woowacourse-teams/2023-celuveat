package com.celuveat.restaurant.command.domain.review;

import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.auth.fixture.OauthMemberFixture.오도;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.BAD_REVIEW_VALUE;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.PERMISSION_DENIED;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.exception.BaseExceptionType;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.exception.RestaurantReviewException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("음식점 리뷰(RestaurantReview) 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RestaurantReviewTest {

    private final Restaurant 대성집 = 대성집();
    private final OauthMember 말랑 = 말랑();
    private final OauthMember 오도 = 오도();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(대성집, "id", 1L);
        ReflectionTestUtils.setField(말랑, "id", 1L);
        ReflectionTestUtils.setField(오도, "id", 2L);
    }

    @Nested
    class 리뷰_작성_시 {

        @Test
        void 리뷰를_작성하면_음식점의_리뷰_수와_전체_평점이_증가한다() {
            // when
            RestaurantReview.create("리뷰1", 말랑, 대성집, 3.0);
            RestaurantReview.create("리뷰2", 말랑, 대성집, 2.5);

            // then
            assertThat(대성집.reviewCount()).isEqualTo(2);
            assertThat(대성집.totalRating()).isEqualTo(5.5);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-1.0, 0.0, 5.1})
        void 별점은_0점_이하거나_5점을_초과할_수_없다(double invalidRating) {
            // when
            BaseExceptionType ex = assertThrows(RestaurantReviewException.class, () ->
                    RestaurantReview.create("잘못된 리뷰", 말랑, 대성집, invalidRating)
            ).exceptionType();

            // then
            assertThat(ex).isEqualTo(BAD_REVIEW_VALUE);
        }
    }

    @Nested
    class 수정_시 {

        @Test
        void 자신의_리뷰는_수정할_수_있다() {
            // given
            RestaurantReview review = RestaurantReview.create("쏘쏘", 말랑, 대성집, 3.0);

            // when
            review.update("수정", 말랑.id(), 2.9);

            // then
            assertThat(review.content()).isEqualTo("수정");
            assertThat(review.rating()).isEqualTo(2.9);
        }

        @Test
        void 리뷰를_수정하면_음식점의_리뷰_수는_동일하고_전체_평점이_변경된다() {
            // given
            RestaurantReview review = RestaurantReview.create("리뷰1", 말랑, 대성집, 3.0);

            // when
            review.update("수정", 말랑.id(), 2.9);

            // then
            assertThat(대성집.reviewCount()).isEqualTo(1);
            assertThat(대성집.totalRating()).isEqualTo(2.9);
        }

        @Test
        void 자신의_리뷰가_아니면_수정할_수_없다() {
            // given
            RestaurantReview review = RestaurantReview.create("굳", 말랑, 대성집, 5.0);

            // when
            BaseExceptionType ex = assertThrows(RestaurantReviewException.class, () ->
                    review.update("수정", 오도.id(), 3.2)
            ).exceptionType();

            // then
            assertThat(ex).isEqualTo(PERMISSION_DENIED);
            assertThat(대성집.reviewCount()).isEqualTo(1);
            assertThat(대성집.totalRating()).isEqualTo(5.0);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-1.0, 0.0, 5.1})
        void 수정_시_별점은_0점_이하거나_5점을_초과할_수_없다(double invalidRating) {
            // given
            RestaurantReview review = RestaurantReview.create("굳", 말랑, 대성집, 5.0);

            // when
            BaseExceptionType ex = assertThrows(RestaurantReviewException.class, () ->
                    review.update("잘못된 리뷰", 말랑.id(), invalidRating)
            ).exceptionType();

            // then
            assertThat(ex).isEqualTo(BAD_REVIEW_VALUE);
            assertThat(대성집.reviewCount()).isEqualTo(1);
            assertThat(대성집.totalRating()).isEqualTo(5.0);
        }
    }

    @Nested
    class 제거_시 {

        @Test
        void 내_리뷰는_제거할_수_있다() {
            // given
            RestaurantReview review = RestaurantReview.create("굳", 말랑, 대성집, 5.0);

            // when & then
            assertDoesNotThrow(() -> {
                review.delete(말랑.id());
            });
        }

        @Test
        void 리뷰를_제거하면_음식점의_리뷰_수와_전체_평점이_감소된다() {
            // given
            RestaurantReview review1 = RestaurantReview.create("굳", 말랑, 대성집, 5.0);
            RestaurantReview review2 = RestaurantReview.create("쏘쏘", 말랑, 대성집, 3.0);

            // when
            review1.delete(말랑.id());

            // then
            assertThat(대성집.reviewCount()).isEqualTo(1);
            assertThat(대성집.totalRating()).isEqualTo(3.0);
        }

        @Test
        void 다른_사람의_리뷰는_제거할_수_없다() {
            // given
            RestaurantReview review = RestaurantReview.create("굳", 말랑, 대성집, 5.0);

            // when
            BaseExceptionType ex = assertThrows(RestaurantReviewException.class, () ->
                    review.delete(오도.id())
            ).exceptionType();

            // then
            assertThat(ex).isEqualTo(PERMISSION_DENIED);
            assertThat(대성집.reviewCount()).isEqualTo(1);
            assertThat(대성집.totalRating()).isEqualTo(5.0);
        }
    }
}
