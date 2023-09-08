package com.celuveat.restaurant.query;

import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantReviewFixture.음식점_리뷰;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import com.celuveat.restaurant.query.dto.RestaurantReviewQueryResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("음식점 리뷰 조회용 서비스(RestaurantReviewQueryService) 은(는)")
class RestaurantReviewQueryServiceTest {

    @Autowired
    private RestaurantReviewQueryService restaurantReviewQueryService;

    @Autowired
    private RestaurantReviewRepository restaurantReviewRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OauthMemberRepository oauthMemberRepository;

    @Test
    void 음식점_리뷰를_조회한다() {
        // given
        Restaurant restaurant = 음식점("오도음식점");
        restaurantRepository.save(restaurant);
        OauthMember member1 = 멤버("도기");
        oauthMemberRepository.save(member1);
        OauthMember member2 = 멤버("말랑");
        oauthMemberRepository.save(member2);
        OauthMember member3 = 멤버("로이스");
        oauthMemberRepository.save(member3);

        RestaurantReview review1 = 음식점_리뷰(member1, restaurant);
        RestaurantReview review2 = 음식점_리뷰(member2, restaurant);
        RestaurantReview review3 = 음식점_리뷰(member3, restaurant);
        RestaurantReview review4 = 음식점_리뷰(member3, restaurant);
        restaurantReviewRepository.saveAll(List.of(review1, review2, review3, review4));

        RestaurantReviewQueryResponse expected = RestaurantReviewQueryResponse.from(List.of(
                review4, review3, review2, review1
        ));

        // when
        RestaurantReviewQueryResponse result =
                restaurantReviewQueryService.findAllByRestaurantId(restaurant.id());

        // then
        assertThat(result).isEqualTo(expected);
    }
}
