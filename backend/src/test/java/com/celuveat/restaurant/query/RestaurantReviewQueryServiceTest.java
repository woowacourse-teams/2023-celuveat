package com.celuveat.restaurant.query;

import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantReviewImageFixture.리뷰의_사진들;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.IntegrationTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 리뷰 조회용 서비스(RestaurantReviewQueryService) 은(는)")
class RestaurantReviewQueryServiceTest extends IntegrationTest {

    // TODO : 해결해야 함..
    @Disabled
    @Test
    void 음식점_리뷰를_조회한다() {
        // given
//        Restaurant restaurant = 음식점("오도음식점");
//        restaurantRepository.save(restaurant);
//        OauthMember member1 = 멤버("도기");
//        oauthMemberRepository.save(member1);
//        OauthMember member2 = 멤버("말랑");
//        oauthMemberRepository.save(member2);
//        OauthMember member3 = 멤버("로이스");
//        oauthMemberRepository.save(member3);
//
//        RestaurantReview review1 = 음식점_리뷰(member1, restaurant);
//        RestaurantReview review2 = 음식점_리뷰(member2, restaurant);
//        RestaurantReview review3 = 음식점_리뷰(member3, restaurant);
//        RestaurantReview review4 = 음식점_리뷰(member3, restaurant);
//        restaurantReviewRepository.saveAll(List.of(review1, review2, review3, review4));
//        Map<Long, List<RestaurantReviewImage>> 리뷰_사진들 = Map.of(
//                review1.id(), 리뷰의_사진들(review1),
//                review2.id(), 리뷰의_사진들(review2),
//                review3.id(), 리뷰의_사진들(review3),
//                review4.id(), 리뷰의_사진들(review4)
//        );
//        RestaurantReviewsQueryResponse expected = RestaurantReviewsQueryResponse.from(List.of(
//                review4, review3, review2, review1
//        ), 리뷰_사진들, Map.of());
//
//        // when
//        RestaurantReviewsQueryResponse result =
//                restaurantReviewQueryService.findAllByRestaurantId(restaurant.id(), null);
//
//        // then
//        assertThat(result).isEqualTo(expected);
    }


    // TODO : 해결해야 함..
    @Disabled
    @Test
    void 음식점_리뷰를_조회_할때_좋아요_여부가_포함_된다() {
        // given
//        Restaurant restaurant = 음식점("오도음식점");
//        restaurantRepository.save(restaurant);
//        OauthMember member1 = 멤버("도기");
//        oauthMemberRepository.save(member1);
//        OauthMember member2 = 멤버("말랑");
//        oauthMemberRepository.save(member2);
//
//        RestaurantReview review1 = 음식점_리뷰(member1, restaurant);
//        RestaurantReview review2 = 음식점_리뷰(member2, restaurant);
//        restaurantReviewRepository.saveAll(List.of(review1, review2));
//        Map<Long, List<RestaurantReviewImage>> 리뷰_사진들 = Map.of(
//                review1.id(), restaurantReviewImageRepository.saveAll(리뷰의_사진들(review1)),
//                review2.id(), restaurantReviewImageRepository.saveAll(리뷰의_사진들(review2))
//        );
//        restaurantReviewLikeRepository.save(RestaurantReviewLike.create(review1, member1));
//        Map<Long, Boolean> 리뷰_좋아요_여부 = Map.of(
//                review1.id(), true,
//                review2.id(), false
//        );
//        RestaurantReviewsQueryResponse expected = RestaurantReviewsQueryResponse.from(List.of(
//                review2, review1
//        ), 리뷰_사진들, 리뷰_좋아요_여부);
//
//        // when
//        RestaurantReviewsQueryResponse result =
//                restaurantReviewQueryService.findAllByRestaurantId(restaurant.id(), member1.id());
//
//        // then
//        assertThat(result).isEqualTo(expected);
    }
}
