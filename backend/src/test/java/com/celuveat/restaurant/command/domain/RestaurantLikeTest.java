package com.celuveat.restaurant.command.domain;

import static com.celuveat.auth.fixture.OauthMemberFixture.로이스;
import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("음식점 좋아요(RestaurantLike) 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RestaurantLikeTest {

    private final Restaurant 대성집 = 대성집();
    private final OauthMember 말랑 = 말랑();
    private final OauthMember 로이스 = 로이스();

    @Test
    void 생성_시_음식점의_좋아요_수를_증가시킨다() {
        // when
        RestaurantLike.create(대성집, 로이스);
        RestaurantLike.create(대성집, 말랑);

        // then
        assertThat(대성집.likeCount()).isEqualTo(2);
    }

    @Test
    void 취소_시_음식점의_좋아요_수를_감소시킨다() {
        // given
        RestaurantLike like = RestaurantLike.create(대성집, 로이스);
        RestaurantLike.create(대성집, 말랑);

        // when
        like.cancel();

        // then
        assertThat(대성집.likeCount()).isEqualTo(1);
    }
}
