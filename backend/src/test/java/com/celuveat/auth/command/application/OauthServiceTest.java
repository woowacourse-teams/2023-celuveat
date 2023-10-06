package com.celuveat.auth.command.application;

import static com.celuveat.auth.command.domain.OauthServerType.KAKAO;
import static com.celuveat.auth.exception.AuthExceptionType.NOT_FOUND_MEMBER;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantLikeFixture.음식점_좋아요;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.client.OauthMemberClientComposite;
import com.celuveat.auth.exception.AuthException;
import com.celuveat.common.IntegrationTest;
import com.celuveat.common.exception.BaseExceptionType;
import com.celuveat.restaurant.command.domain.Restaurant;
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
        OauthMember 도기 = oauthMemberRepository.save(멤버("도기"));
        String authCode = "abcd";
        when(oauthMemberClientComposite.fetch(KAKAO, authCode)).thenReturn(도기);

        // when
        Long result = oauthService.login(KAKAO, authCode);

        // then
        assertThat(result).isEqualTo(도기.id());
    }

    @Test
    void 회원_탈퇴하면_회원의_좋아요와_정보가_모두_삭제된다() {
        // given
        Restaurant 새벽집 = restaurantRepository.save(음식점("새벽집"));
        OauthMember 도기 = oauthMemberRepository.save(멤버("도기"));
        restaurantLikeRepository.save(음식점_좋아요(새벽집, 도기));

        // when
        oauthService.withdraw(KAKAO, 도기.id());

        // then
        assertThat(restaurantLikeQueryDaoSupport.countByRestaurant(새벽집)).isEqualTo(0);
        BaseExceptionType exceptionType = assertThrows(AuthException.class,
                () -> oauthMemberRepository.getById(도기.id())).exceptionType();
        assertThat(exceptionType).isEqualTo(NOT_FOUND_MEMBER);
    }
}
