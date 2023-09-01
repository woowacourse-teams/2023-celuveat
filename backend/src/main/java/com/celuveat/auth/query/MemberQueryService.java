package com.celuveat.auth.query;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.query.dto.MemberProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final OauthMemberQueryRepository oauthMemberQueryRepository;

    public MemberProfileResponse getProfile(Long memberId) {
        OauthMember member = oauthMemberQueryRepository.getById(memberId);
        return new MemberProfileResponse(member.id(), member.nickname(), member.profileImageUrl());
    }
}
