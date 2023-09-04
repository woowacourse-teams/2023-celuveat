package com.celuveat.auth.query.dao;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.query.dto.MemberProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberProfileResponseDao {

    private final OauthMemberQueryDaoSupport oauthMemberQueryDaoSupport;

    public MemberProfileResponse find(Long memberId) {
        OauthMember member = oauthMemberQueryDaoSupport.getById(memberId);
        return new MemberProfileResponse(member.id(), member.nickname(), member.profileImageUrl());
    }
}
