package com.celuveat.auth.application;

import com.celuveat.auth.application.dto.ProfileResponse;
import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final OauthMemberRepository oauthMemberRepository;

    public ProfileResponse getProfile(Long memberId) {
        OauthMember member = oauthMemberRepository.getById(memberId);
        return new ProfileResponse(member.nickname(), member.profileImageUrl());
    }
}
