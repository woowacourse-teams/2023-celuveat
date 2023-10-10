package com.celuveat.restaurant.query.dao;

import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.auth.fixture.OauthMemberFixture.오도;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static com.celuveat.restaurant.fixture.RestaurantFixture.모던샤브하우스;
import static com.celuveat.restaurant.fixture.RestaurantFixture.하늘초밥;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.DaoTest;
import com.celuveat.common.TestData;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("좋아요한 음식점 조회 DAO(LikedRestaurantQueryResponseDao) 은(는)")
class LikedRestaurantQueryResponseDaoTest extends DaoTest {

    @Autowired
    private LikedRestaurantQueryResponseDao restaurantQueryResponseDao;

    private final OauthMember 말랑 = 말랑();
    private final OauthMember 오도 = 오도();
    private final Restaurant 대성집 = 대성집();
    private final Restaurant 하늘초밥 = 하늘초밥();
    private final Restaurant 모던샤브하우스 = 모던샤브하우스();

    @Override
    protected void prepareTestData() {
        testData.addMembers(말랑, 오도);
        testData.addRestaurants(대성집, 하늘초밥, 모던샤브하우스);
        testData.addRestaurantLikes(
                RestaurantLike.create(대성집, 말랑),
                RestaurantLike.create(하늘초밥, 말랑),
                RestaurantLike.create(모던샤브하우스, 오도)
        );
    }

    @Test
    void 회원이_좋아요한_음식점을_모두_조회한다() {
        // when
        List<LikedRestaurantQueryResponse> liked = restaurantQueryResponseDao.findLikedByMemberId(말랑.id());

        // then
        assertThat(liked)
                .extracting(LikedRestaurantQueryResponse::name)
                .containsExactly("하늘초밥", "대성집");
    }
}
