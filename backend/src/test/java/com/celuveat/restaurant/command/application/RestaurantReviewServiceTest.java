package com.celuveat.restaurant.command.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.도기;
import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.BAD_REVIEW_VALUE;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.PERMISSION_DENIED;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.IntegrationTest;
import com.celuveat.common.exception.BaseException;
import com.celuveat.common.exception.BaseExceptionType;
import com.celuveat.restaurant.command.application.dto.DeleteReviewCommand;
import com.celuveat.restaurant.command.application.dto.SaveReviewRequestCommand;
import com.celuveat.restaurant.command.application.dto.UpdateReviewRequestCommand;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLike;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewReport;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("음식점 리뷰 서비스(RestaurantReviewService) 은(는)")
class RestaurantReviewServiceTest extends IntegrationTest {

    private Restaurant 대성집;
    private OauthMember 말랑;
    private OauthMember 도기;

    @BeforeEach
    void setUp() {
        대성집 = restaurantRepository.save(대성집());
        말랑 = oauthMemberRepository.save(말랑());
        도기 = oauthMemberRepository.save(도기());
    }

    @Nested
    class 리뷰_작성_시 {

        @Test
        void 리뷰를_작성하면_음식점의_리뷰_수와_전체_평점이_증가한다() {
            // when
            Long reviewId = restaurantReviewService.create(
                    new SaveReviewRequestCommand("정말 맛있어요", 말랑.id(), 대성집.id(), 5.0)
            );

            // then
            RestaurantReview review = restaurantReviewRepository.getById(reviewId);
            assertThat(review.content()).isEqualTo("정말 맛있어요");
            assertThat(review.member()).isEqualTo(말랑);
            assertThat(review.restaurant()).isEqualTo(대성집);
            assertThat(review.rating()).isEqualTo(5.0);
            assertThat(대성집.reviewCount()).isEqualTo(1);
            assertThat(대성집.totalRating()).isEqualTo(5.0);
        }

        @Test
        void 리뷰를_사진과_함께_작성한다() {
            // given
            List<String> imageNames = List.of("imageA", "imageB");

            // when
            Long reviewId = restaurantReviewService.create(
                    new SaveReviewRequestCommand("정말 맛있어요", 말랑.id(), 대성집.id(), 5.0, imageNames)
            );

            // then
            RestaurantReview review = restaurantReviewRepository.getById(reviewId);
            List<RestaurantReviewImage> reviewImages = review.images();
            assertThat(review.content()).isEqualTo("정말 맛있어요");
            assertThat(review.member()).isEqualTo(말랑);
            assertThat(review.restaurant()).isEqualTo(대성집);
            assertThat(review.rating()).isEqualTo(5.0);
            assertThat(reviewImages).hasSize(2);
            assertThat(reviewImages)
                    .extracting(RestaurantReviewImage::name)
                    .containsExactlyInAnyOrder("imageA", "imageB");
        }

        @ParameterizedTest
        @ValueSource(doubles = {-1.0, 0.0, 5.1})
        void 별점은_0점_이하거나_5점을_초과할_수_없다(double invalidRating) {
            // when
            BaseExceptionType result = assertThrows(BaseException.class, () ->
                    restaurantReviewService.create(
                            new SaveReviewRequestCommand("잘못된 리뷰", 말랑.id(), 대성집.id(), invalidRating)
                    )
            ).exceptionType();

            // then
            assertThat(result).isEqualTo(BAD_REVIEW_VALUE);
            assertThat(대성집.reviewCount()).isEqualTo(0);
            assertThat(대성집.totalRating()).isEqualTo(0.0);
        }
    }

    @Nested
    class 리뷰_수정_시 {

        @Test
        void 자신의_리뷰는_수정할_수_있다() {
            // given
            Long reviewId = restaurantReviewService.create(
                    new SaveReviewRequestCommand("정말 맛있어요", 말랑.id(), 대성집.id(), 5.0)
            );

            // when
            restaurantReviewService.update(
                    new UpdateReviewRequestCommand("사장님이 초심을 잃었어요!", reviewId, 말랑.id(), 1.0)
            );

            // then
            RestaurantReview review = restaurantReviewRepository.getById(reviewId);
            assertThat(review.content()).isEqualTo("사장님이 초심을 잃었어요!");
            assertThat(review.member()).isEqualTo(말랑);
            assertThat(review.restaurant()).isEqualTo(대성집);
            assertThat(review.rating()).isEqualTo(1.0);
        }

        @Test
        void 리뷰를_수정하면_음식점의_리뷰_수는_동일하고_전체_평점이_변경된다() {
            // given
            restaurantReviewService.create(
                    new SaveReviewRequestCommand("쏘쏘", 말랑.id(), 대성집.id(), 3.0)
            );
            Long reviewId = restaurantReviewService.create(
                    new SaveReviewRequestCommand("정말 맛있어요", 말랑.id(), 대성집.id(), 5.0)
            );

            // when
            restaurantReviewService.update(
                    new UpdateReviewRequestCommand("사장님이 초심을 잃었어요!", reviewId, 말랑.id(), 1.0)
            );

            // then
            RestaurantReview review = restaurantReviewRepository.getById(reviewId);
            assertThat(review.content()).isEqualTo("사장님이 초심을 잃었어요!");
            assertThat(review.member()).isEqualTo(말랑);
            assertThat(review.restaurant()).isEqualTo(대성집);
            assertThat(review.rating()).isEqualTo(1.0);
            assertThat(대성집.reviewCount()).isEqualTo(2);
            assertThat(대성집.totalRating()).isEqualTo(4.0);
        }

        @Test
        void 자신의_리뷰가_아니면_수정할_수_없다() {
            // given
            Long 말랑의_대성집_리뷰_ID = restaurantReviewService.create(
                    new SaveReviewRequestCommand("맛있어요", 말랑.id(), 대성집.id(), 4.0)
            );

            // when
            BaseExceptionType result = assertThrows(BaseException.class, () ->
                    restaurantReviewService.update(
                            new UpdateReviewRequestCommand("더 맛있어졌어요", 말랑의_대성집_리뷰_ID, 도기.id(), 5.0)
                    )
            ).exceptionType();

            // then
            assertThat(result).isEqualTo(PERMISSION_DENIED);
            RestaurantReview review = restaurantReviewRepository.getById(말랑의_대성집_리뷰_ID);
            assertThat(review.content()).isEqualTo("맛있어요");
            assertThat(review.member()).isEqualTo(말랑);
            assertThat(review.restaurant()).isEqualTo(대성집);
            assertThat(review.rating()).isEqualTo(4.0);
            assertThat(대성집.reviewCount()).isEqualTo(1);
            assertThat(대성집.totalRating()).isEqualTo(4.0);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-1.0, 0.0, 5.1})
        void 별점은_0점_이하거나_5점을_초과할_수_수_없다(double invalidRating) {
            // given
            Long reviewId = restaurantReviewService.create(
                    new SaveReviewRequestCommand("맛있어요", 말랑.id(), 대성집.id(), 4.0)
            );

            // when
            BaseExceptionType result = assertThrows(BaseException.class, () ->
                    restaurantReviewService.update(
                            new UpdateReviewRequestCommand("잘못된 리뷰", reviewId, 말랑.id(), invalidRating)
                    )
            ).exceptionType();

            // then
            assertThat(result).isEqualTo(BAD_REVIEW_VALUE);
            RestaurantReview review = restaurantReviewRepository.getById(reviewId);
            assertThat(review.content()).isEqualTo("맛있어요");
            assertThat(review.member()).isEqualTo(말랑);
            assertThat(review.restaurant()).isEqualTo(대성집);
            assertThat(review.rating()).isEqualTo(4.0);
            assertThat(대성집.reviewCount()).isEqualTo(1);
            assertThat(대성집.totalRating()).isEqualTo(4.0);
        }
    }

    @Nested
    class 리뷰_삭제_시 {

        @Test
        void 내_리뷰는_제거할_수_있다() {
            // given
            Long reviewId = restaurantReviewService.create(
                    new SaveReviewRequestCommand("정말 맛있어요", 말랑.id(), 대성집.id(), 5.0)
            );

            // when
            restaurantReviewService.delete(
                    new DeleteReviewCommand(reviewId, 말랑.id())
            );

            // then
            Optional<RestaurantReview> result = restaurantReviewRepository.findById(reviewId);
            assertThat(result).isEmpty();
        }

        @Test
        void 리뷰를_제거하면_음식점의_리뷰_수와_전체_평점이_감소된다() {
            // given
            Long reviewId = restaurantReviewService.create(
                    new SaveReviewRequestCommand("정말 맛있어요", 말랑.id(), 대성집.id(), 5.0)
            );

            // when
            restaurantReviewService.delete(new DeleteReviewCommand(reviewId, 말랑.id()));

            // then
            assertThat(대성집.reviewCount()).isEqualTo(0);
            assertThat(대성집.totalRating()).isEqualTo(0.0);
        }

        @Test
        void 다른_사람의_리뷰는_제거할_수_없다() {
            // given
            Long reviewId = restaurantReviewService.create(
                    new SaveReviewRequestCommand("정말 맛있어요", 말랑.id(), 대성집.id(), 5.0)
            );

            // when
            BaseExceptionType result = assertThrows(BaseException.class, () ->
                    restaurantReviewService.delete(
                            new DeleteReviewCommand(reviewId, 도기.id())
                    )
            ).exceptionType();

            // then
            assertThat(result).isEqualTo(PERMISSION_DENIED);
            RestaurantReview review = restaurantReviewRepository.getById(reviewId);
            assertThat(review.content()).isEqualTo("정말 맛있어요");
            assertThat(review.member()).isEqualTo(말랑);
            assertThat(review.restaurant()).isEqualTo(대성집);
            assertThat(review.rating()).isEqualTo(5.0);
            assertThat(대성집.reviewCount()).isEqualTo(1);
            assertThat(대성집.totalRating()).isEqualTo(5.0);
        }

        @Test
        void 리뷰를_제거하면_리뷰_좋아요와_리뷰_신고가_모두_삭제된다() {
            // given
            RestaurantReview restaurantReview = restaurantReviewRepository.save(
                    RestaurantReview.create(대성집, 말랑, "좋아요", 5.0)
            );
            RestaurantReviewLike 리뷰_좋아요 = restaurantReviewLikeRepository.save(
                    RestaurantReviewLike.create(restaurantReview, 도기)
            );
            RestaurantReviewReport 리뷰_신고 = restaurantReviewReportRepository.save(
                    new RestaurantReviewReport("신고", restaurantReview, 도기)
            );

            // when
            restaurantReviewService.delete(new DeleteReviewCommand(restaurantReview.id(), 말랑.id()));

            // then
            assertThat(대성집.reviewCount()).isEqualTo(0);
            assertThat(대성집.totalRating()).isEqualTo(0.0);
            assertThat(restaurantReviewReportRepository.findById(리뷰_신고.id())).isEmpty();
            assertThat(restaurantReviewLikeRepository.findById(리뷰_좋아요.id())).isEmpty();
        }
    }
}
