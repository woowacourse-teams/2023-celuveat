package com.celuveat.auth.query.dao;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.query.dao.support.OauthMemberQueryDaoSupport;
import com.celuveat.auth.query.dto.OauthMemberProfileResponse;
import com.celuveat.common.dao.Dao;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Dao
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
