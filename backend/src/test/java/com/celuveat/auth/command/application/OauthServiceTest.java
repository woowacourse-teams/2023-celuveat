package com.celuveat.auth.command.application;

import static com.celuveat.auth.command.domain.OauthServerType.KAKAO;
import static com.celuveat.auth.fixture.OauthMemberFixture.말랑;
import static com.celuveat.restaurant.fixture.RestaurantFixture.대성집;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.client.OauthMemberClientComposite;
import com.celuveat.common.IntegrationTest;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Oauth 관련 서비스(OauthService) 은(는)")
class OauthServiceTest extends IntegrationTest {

    private OauthMemberClientComposite oauthMemberClientComposite;
    private OauthService oauthService;

    @BeforeEach
    void setUp() {
        oauthMemberClientComposite = mock(OauthMemberClientComposite.class);
        oauthService = new OauthService(
                oauthMemberRepository,
                restaurantLikeRepository,
                restaurantReviewRepository,
                oauthMemberClientComposite,
                authCodeRequestUrlProviderComposite
        );
    }

    @Test
    void 로그인하면_회원의_아이디가_반환된다() {
        // given
        OauthMember 말랑 = oauthMemberRepository.save(말랑());
        String authCode = "말랑 auth code";
        when(oauthMemberClientComposite.fetch(KAKAO, authCode)).thenReturn(말랑);

        // when
        Long memberId = oauthService.login(KAKAO, authCode);

        // then
        assertThat(memberId).isEqualTo(말랑.id());
    }

    @Test
    void 회원_탈퇴하면_회원의_좋아요와_정보가_모두_삭제된다() {
        // given
        Restaurant 대성집 = restaurantRepository.save(대성집());
        OauthMember 말랑 = oauthMemberRepository.save(말랑());
        restaurantLikeRepository.save(RestaurantLike.create(대성집, 말랑));

        // when
        oauthService.withdraw(KAKAO, 말랑.id());

        // then
        Optional<RestaurantLike> byRestaurantAndMember = restaurantLikeRepository.
                findByRestaurantAndMember(대성집, 말랑);
        assertThat(byRestaurantAndMember).isEmpty();
        Optional<OauthMember> 말랑_조회_결과 = oauthMemberRepository.findById(말랑.id());
        assertThat(말랑_조회_결과).isEmpty();
    }
}
