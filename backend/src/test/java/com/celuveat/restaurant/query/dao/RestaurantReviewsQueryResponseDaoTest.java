package com.celuveat.restaurant.query.dao;

import static com.celuveat.auth.fixture.OauthMemberFixture.로이스;
import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.auth.fixture.OauthMemberFixture.오도;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static com.celuveat.restaurant.fixture.RestaurantFixture.좋아요_누르지_않음;
import static com.celuveat.restaurant.fixture.RestaurantFixture.좋아요_누름;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.DaoTest;
import com.celuveat.common.TestData;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLike;
import com.celuveat.restaurant.query.dto.RestaurantReviewsQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantReviewsQueryResponse.RestaurantReviewSingleQueryResponse;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("음식점 리뷰 조회 DAO(RestaurantReviewsQueryResponseDao) 은(는)")
class RestaurantReviewsQueryResponseDaoTest extends DaoTest {

    @Autowired
    private RestaurantReviewsQueryResponseDao restaurantReviewsQueryResponseDao;

    private final OauthMember 말랑 = 말랑();
    private final OauthMember 로이스 = 로이스();
    private final OauthMember 오도 = 오도();
    private final Restaurant 대성집 = 대성집();
    private final RestaurantReview 대성집_리뷰_1 = RestaurantReview.create(
            대성집, 말랑,
            "음...", 1.0,
            List.of("사진1", "사진2")
    );
    private final RestaurantReview 대성집_리뷰_2 = RestaurantReview.create(
            대성집, 오도,
            "흠...", 3.0
    );
    private final RestaurantReviewLike 리뷰_1에_대한_로이스의_좋아요 = RestaurantReviewLike.create(대성집_리뷰_1, 로이스);

    @Override
    protected void prepareTestData() {
        testData.addMembers(말랑, 로이스, 오도);
        testData.addRestaurants(대성집);
        testData.addRestaurantReviews(대성집_리뷰_1, 대성집_리뷰_2);
        testData.addRestaurantReviewLikes(리뷰_1에_대한_로이스의_좋아요);
    }

    @Test
    void 음식점_리뷰와_리뷰의_이미지를_조회한다() {
        // given
        List<RestaurantReviewSingleQueryResponse> expected = List.of(
                new RestaurantReviewSingleQueryResponse(
                        대성집_리뷰_2.id(),
                        오도.id(),
                        오도.nickname(),
                        오도.profileImageUrl(),
                        "흠...",
                        null,
                        0,
                        좋아요_누르지_않음,
                        3.0,
                        Collections.emptyList()
                ),
                new RestaurantReviewSingleQueryResponse(
                        대성집_리뷰_1.id(),
                        말랑.id(),
                        말랑.nickname(),
                        말랑.profileImageUrl(),
                        "음...",
                        null,
                        1,
                        좋아요_누르지_않음,
                        1.0,
                        List.of("사진1", "사진2")
                )
        );

        // when
        RestaurantReviewsQueryResponse reviews = restaurantReviewsQueryResponseDao
                .findByRestaurantId(대성집.id(), 말랑.id());

        // then
        assertThat(reviews.totalElementsCount()).isEqualTo(2);
        List<RestaurantReviewSingleQueryResponse> result = reviews.reviews();
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expected);
    }

    @Test
    void 좋아요한_음식점_리뷰를_조회한다() {
        // given
        List<RestaurantReviewSingleQueryResponse> expected = List.of(
                new RestaurantReviewSingleQueryResponse(
                        대성집_리뷰_2.id(),
                        오도.id(),
                        오도.nickname(),
                        오도.profileImageUrl(),
                        "흠...",
                        null,
                        0,
                        좋아요_누르지_않음,
                        3.0,
                        Collections.emptyList()
                ),
                new RestaurantReviewSingleQueryResponse(
                        대성집_리뷰_1.id(),
                        말랑.id(),
                        말랑.nickname(),
                        말랑.profileImageUrl(),
                        "음...",
                        null,
                        1,
                        좋아요_누름,
                        1.0,
                        List.of("사진1", "사진2")
                )
        );

        // when
        RestaurantReviewsQueryResponse reviews = restaurantReviewsQueryResponseDao
                .findByRestaurantId(대성집.id(), 로이스.id());

        // then
        assertThat(reviews.totalElementsCount()).isEqualTo(2);
        List<RestaurantReviewSingleQueryResponse> result = reviews.reviews();
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(expected);
    }
}
