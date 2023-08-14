package com.celuveat.auth.application;

import com.celuveat.auth.application.dto.MemberQueryResponse;
import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final OauthMemberRepository oauthMemberRepository;

    public MemberQueryResponse getProfile(Long memberId) {
        OauthMember member = oauthMemberRepository.getById(memberId);
        return new MemberQueryResponse(member.nickname(), member.profileImageUrl());
    }
}
