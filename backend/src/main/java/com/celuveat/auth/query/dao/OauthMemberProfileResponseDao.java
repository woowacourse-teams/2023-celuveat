package com.celuveat.auth.query.dao;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.query.dto.OauthMemberProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OauthMemberProfileResponseDao {

    private final OauthMemberQueryDaoSupport oauthMemberQueryDaoSupport;

    public OauthMemberProfileResponse find(Long memberId) {
        OauthMember member = oauthMemberQueryDaoSupport.getById(memberId);
        return new OauthMemberProfileResponse(
                member.id(),
                member.nickname(),
                member.profileImageUrl(),
                member.oauthId().oauthServer().name()
        );
    }
}
