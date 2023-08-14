package com.celuveat.restaurant.application;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.PERMISSION_DENIED;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.RESTAURANT_REVIEW_MISMATCH;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.common.IntegrationTest;
import com.celuveat.common.exception.BaseException;
import com.celuveat.common.exception.BaseExceptionType;
import com.celuveat.restaurant.application.dto.SaveReviewRequest;
import com.celuveat.restaurant.application.dto.UpdateReviewRequest;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.restaurant.domain.review.RestaurantReview;
import com.celuveat.restaurant.domain.review.RestaurantReviewRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 리뷰 서비스(RestaurantReviewService) 은(는)")
class RestaurantReviewServiceTest {

    @Autowired
    private RestaurantReviewService restaurantReviewService;

    @Autowired
    private RestaurantReviewRepository restaurantReviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OauthMemberRepository oauthMemberRepository;

    private Restaurant restaurant;
    private Restaurant otherRestaurant;
    private OauthMember member;
    private OauthMember otherMember;

    @BeforeEach
    void setUp() {
        restaurant = 음식점("로이스음식점");
        restaurantRepository.save(restaurant);
        otherRestaurant = 음식점("오도음식점");
        restaurantRepository.save(otherRestaurant);
        member = 멤버("도기");
        oauthMemberRepository.save(member);
        otherMember = 멤버("말랑");
        oauthMemberRepository.save(otherMember);
    }

    @Test
    void 리뷰를_저장한다() {
        // given
        SaveReviewRequest request = new SaveReviewRequest("정말 맛있어요");
        RestaurantReview expected = new RestaurantReview("정말 맛있어요", member, restaurant);

        // when
        Long savedId = restaurantReviewService.saveReview(request, member.id(), restaurant.id());
        RestaurantReview result = restaurantReviewRepository.getById(savedId);

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "createdDate")
                .isEqualTo(expected);
    }

    @Test
    void 다른_사람의_리뷰를_수정하려하면_예외가_발생한다() {
        // given
        SaveReviewRequest request = new SaveReviewRequest("정말 맛있어요");
        Long reviewId = restaurantReviewService.saveReview(request, member.id(), restaurant.id());
        UpdateReviewRequest updateRequest = new UpdateReviewRequest("더 맛있어졌어요");

        // when
        BaseExceptionType result = assertThrows(BaseException.class, () ->
                restaurantReviewService.updateReview(updateRequest, reviewId, otherMember.id(), restaurant.id())
        ).exceptionType();

        // then
        assertThat(result).isEqualTo(PERMISSION_DENIED);
    }

    @Test
    void 잘못된_음식점아이디로_요청을_보내_수정하려하면_예외가_발생한다() {
        // given
        SaveReviewRequest request = new SaveReviewRequest("정말 맛있어요");
        Long reviewId = restaurantReviewService.saveReview(request, member.id(), restaurant.id());
        UpdateReviewRequest updateRequest = new UpdateReviewRequest("더 맛있어졌어요");

        // when
        BaseExceptionType result = assertThrows(BaseException.class, () ->
                restaurantReviewService.updateReview(updateRequest, reviewId, member.id(), otherRestaurant.id())
        ).exceptionType();

        // then
        assertThat(result).isEqualTo(RESTAURANT_REVIEW_MISMATCH);
    }

    @Test
    void 리뷰를_수정한다() {
        // given
        SaveReviewRequest request = new SaveReviewRequest("정말 맛있어요");
        Long reviewId = restaurantReviewService.saveReview(request, member.id(), restaurant.id());
        UpdateReviewRequest updateRequest = new UpdateReviewRequest("사장님이 초심을 잃었어요!");
        RestaurantReview expected = new RestaurantReview("사장님이 초심을 잃었어요!", member, restaurant);

        // when
        restaurantReviewService.updateReview(updateRequest, reviewId, member.id(), restaurant.id());
        RestaurantReview result = restaurantReviewRepository.getById(reviewId);

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id", "createdDate")
                .isEqualTo(expected);
    }

    @Test
    void 다른_사람의_리뷰를_삭제하려하면_예외가_발생한다() {
        // given
        SaveReviewRequest request = new SaveReviewRequest("정말 맛있어요");
        Long reviewId = restaurantReviewService.saveReview(request, member.id(), restaurant.id());

        // when
        BaseExceptionType result = assertThrows(BaseException.class, () ->
                restaurantReviewService.deleteReview(reviewId, otherMember.id(), restaurant.id())
        ).exceptionType();

        // then
        assertThat(result).isEqualTo(PERMISSION_DENIED);
    }

    @Test
    void 잘못된_음식점아이디로_요청을_보내_삭제하려하면_예외가_발생한다() {
        // given
        SaveReviewRequest request = new SaveReviewRequest("정말 맛있어요");
        Long reviewId = restaurantReviewService.saveReview(request, member.id(), restaurant.id());

        // when
        BaseExceptionType result = assertThrows(BaseException.class, () ->
                restaurantReviewService.deleteReview(reviewId, member.id(), otherRestaurant.id())
        ).exceptionType();

        // then
        assertThat(result).isEqualTo(RESTAURANT_REVIEW_MISMATCH);
    }

    @Test
    void 리뷰를_삭제한다() {
        // given
        SaveReviewRequest request = new SaveReviewRequest("정말 맛있어요");
        Long reviewId = restaurantReviewService.saveReview(request, member.id(), restaurant.id());

        // when
        restaurantReviewService.deleteReview(reviewId, member.id(), restaurant.id());
        Optional<RestaurantReview> result = restaurantReviewRepository.findById(reviewId);

        // then
        assertThat(result).isEmpty();
    }
}
