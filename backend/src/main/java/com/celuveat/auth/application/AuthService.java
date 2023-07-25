package com.celuveat.auth.application;

import com.celuveat.auth.domain.OAuthClient;
import com.celuveat.member.domain.Member;
import com.celuveat.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public Member login(String code, OAuthClient oAuthClient) {
        Long oAuthId = oAuthClient.login(code);
        return memberRepository.findByoAuthId(oAuthId)
                .orElseGet(() -> register(oAuthId));
    }

    private Member register(Long oAuthId) {
        Member member = new Member(oAuthId);
        memberRepository.save(member);
        return member;
    }
}
