package com.celuveat.auth.command.application;

import static com.celuveat.auth.command.domain.OauthServerType.KAKAO;
import static com.celuveat.auth.exception.AuthExceptionType.NOT_FOUND_MEMBER;
import static com.celuveat.auth.fixture.OauthMemberFixture.멤버;
import static com.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static com.celuveat.restaurant.fixture.RestaurantLikeFixture.음식점_좋아요;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.auth.command.domain.authcode.AuthCodeRequestUrlProviderComposite;
import com.celuveat.auth.command.domain.client.OauthMemberClient;
import com.celuveat.auth.command.domain.client.OauthMemberClientComposite;
import com.celuveat.auth.command.infra.kakao.FakeKakaoMemberClient;
import com.celuveat.auth.exception.AuthException;
import com.celuveat.common.IntegrationTest;
import com.celuveat.common.exception.BaseExceptionType;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import com.celuveat.restaurant.query.dao.support.RestaurantLikeQueryDaoSupport;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("Oauth 관련 서비스(OauthService) 은(는)")
class OauthServiceTest {

    private final OauthMemberClient memberClient = new FakeKakaoMemberClient();
    private final OauthMemberClientComposite clientComposite = new OauthMemberClientComposite(Set.of(memberClient));
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private OauthMemberRepository oauthMemberRepository;
    @Autowired
    private RestaurantLikeRepository restaurantLikeRepository;
    @Autowired
    private RestaurantLikeQueryDaoSupport restaurantLikeQueryDaoSupport;
    @Autowired
    private RestaurantReviewRepository restaurantReviewRepository;
    @Autowired
    private AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    private OauthService oauthService;

    @BeforeEach
    void setUp() {
        this.oauthService = new OauthService(
                oauthMemberRepository,
                restaurantLikeRepository,
                restaurantReviewRepository,
                clientComposite,
                authCodeRequestUrlProviderComposite
        );
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
