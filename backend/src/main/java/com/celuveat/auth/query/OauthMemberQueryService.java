package com.celuveat.auth.query;

import com.celuveat.auth.query.dao.OauthMemberProfileResponseDao;
import com.celuveat.auth.query.dto.OauthMemberProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthMemberQueryService {

    private final OauthMemberProfileResponseDao oauthMemberProfileResponseDao;

    public OauthMemberProfileResponse getProfile(Long memberId) {
        return oauthMemberProfileResponseDao.find(memberId);
    }
}
