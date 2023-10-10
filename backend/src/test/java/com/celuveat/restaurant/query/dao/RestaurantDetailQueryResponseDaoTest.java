package com.celuveat.restaurant.query.dao;

import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.auth.fixture.OauthMemberFixture.오도;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.DaoTest;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.query.dto.RestaurantDetailQueryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("음식점 상세조회 DAO(RestaurantDetailQueryResponseDao) 은(는)")
class RestaurantDetailQueryResponseDaoTest extends DaoTest {

    private final OauthMember 말랑 = 말랑();
    private final OauthMember 오도 = 오도();
    private final Restaurant 대성집 = Restaurant.builder()
            .name("대성집")
            .category("곰탕,설렁탕")
            .roadAddress("서울 종로구 행촌동 209-35")
            .latitude(37.5727172)
            .longitude(126.9609577)
            .phoneNumber("02-735-4259")
            .naverMapUrl("https://map.naver.com/v5/entry/place/13517178?c=15,0,0,0,dh")
            .build();
    private final Restaurant 좋아요_눌린_음식점 = Restaurant.builder()
            .name("좋아요 눌린 음식점")
            .category("한식")
            .roadAddress("말랑시 말랑구 말랑동 123-45")
            .latitude(38.5727172)
            .longitude(127.9409577)
            .phoneNumber("01-2345-6789")
            .naverMapUrl("https://map.naver.com/v5/entry/place/mallang")
            .build();
    private final RestaurantReview 대성집_리뷰_1 = RestaurantReview.create(대성집, 말랑, "음...", 4.0);
    private final RestaurantReview 대성집_리뷰_2 = RestaurantReview.create(대성집, 오도, "흠...", 2.0);
    @Autowired
    private RestaurantDetailQueryResponseDao restaurantDetailQueryResponseDao;

    @Override
    protected void prepareTestData() {
        testData.addMembers(말랑, 오도);
        testData.addRestaurants(대성집, 좋아요_눌린_음식점);
        testData.addRestaurantLikes(RestaurantLike.create(좋아요_눌린_음식점, 말랑));
        testData.addRestaurantReviews(대성집_리뷰_1, 대성집_리뷰_2);
    }

    @Test
    void 음식점을_상세조회한다() {
        // when
        RestaurantDetailQueryResponse result = restaurantDetailQueryResponseDao.find(대성집.id(), null);

        // then
        assertThat(result.name()).isEqualTo("대성집");
        assertThat(result.category()).isEqualTo("곰탕,설렁탕");
        assertThat(result.roadAddress()).isEqualTo("서울 종로구 행촌동 209-35");
        assertThat(result.latitude()).isEqualTo(37.5727172);
        assertThat(result.longitude()).isEqualTo(126.9609577);
        assertThat(result.phoneNumber()).isEqualTo("02-735-4259");
        assertThat(result.naverMapUrl()).isEqualTo("https://map.naver.com/v5/entry/place/13517178?c=15,0,0,0,dh");
        assertThat(result.likeCount()).isEqualTo(0);
        assertThat(result.isLiked()).isFalse();
    }

    @Test
    void 리뷰를_통해_산풀된_평점도_보여준다() {
        // when
        RestaurantDetailQueryResponse result = restaurantDetailQueryResponseDao.find(대성집.id(), null);

        // then
        assertThat(result.name()).isEqualTo("대성집");
        assertThat(result.rating()).isEqualTo(3.0);
    }

    @Test
    void 좋아요_누른_음식점을_조회한다() {
        // when
        RestaurantDetailQueryResponse result = restaurantDetailQueryResponseDao.find(
                좋아요_눌린_음식점.id(), 말랑.id()
        );

        // then
        assertThat(result.name()).isEqualTo("좋아요 눌린 음식점");
        assertThat(result.category()).isEqualTo("한식");
        assertThat(result.roadAddress()).isEqualTo("말랑시 말랑구 말랑동 123-45");
        assertThat(result.latitude()).isEqualTo(38.5727172);
        assertThat(result.longitude()).isEqualTo(127.9409577);
        assertThat(result.phoneNumber()).isEqualTo("01-2345-6789");
        assertThat(result.naverMapUrl()).isEqualTo("https://map.naver.com/v5/entry/place/mallang");
        assertThat(result.likeCount()).isEqualTo(1);
        assertThat(result.isLiked()).isTrue();
    }
}
