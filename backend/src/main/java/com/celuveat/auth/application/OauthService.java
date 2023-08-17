package com.celuveat.auth.application;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.auth.domain.OauthServerType;
import com.celuveat.auth.domain.authcode.AuthCodeRequestUrlProviderComposite;
import com.celuveat.auth.domain.client.OauthMemberClientComposite;
import com.celuveat.restaurant.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.domain.review.RestaurantReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final OauthMemberRepository oauthMemberRepository;
    private final RestaurantLikeRepository restaurantLikeRepository;
    private final RestaurantReviewRepository restaurantReviewRepository;
    private final OauthMemberClientComposite oauthMemberClientComposite;
    private final AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;

    public String getAuthCodeRequestUrl(OauthServerType oauthServerType) {
        return authCodeRequestUrlProviderComposite.provide(oauthServerType);
    }

    @Transactional
    public Long login(OauthServerType oauthServerType, String authCode) {
        OauthMember oauthMember = oauthMemberClientComposite.fetch(oauthServerType, authCode);
        OauthMember saved = oauthMemberRepository.findByOauthId(oauthMember.oauthId())
                .orElseGet(() -> oauthMemberRepository.save(oauthMember));
        return saved.id();
    }

    public void logout(OauthServerType oauthServerType, Long oauthMemberId) {
        OauthMember oauthMember = oauthMemberRepository.getById(oauthMemberId);
        oauthMemberClientComposite.logout(oauthServerType, oauthMember.oauthId());
    }

    @Transactional
    public void withdraw(OauthServerType oauthServerType, Long oauthMemberId) {
        OauthMember oauthMember = oauthMemberRepository.getById(oauthMemberId);
        oauthMemberClientComposite.withdraw(oauthServerType, oauthMember.oauthId());
        restaurantLikeRepository.deleteAllByMemberId(oauthMemberId);
        restaurantReviewRepository.deleteAllByMemberId(oauthMemberId);
        oauthMemberRepository.deleteById(oauthMemberId);
    }
}
