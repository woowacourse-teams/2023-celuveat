package com.celuveat.auth.query;

import com.celuveat.auth.query.dao.MemberProfileResponseDao;
import com.celuveat.auth.query.dto.MemberProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberProfileResponseDao memberProfileResponseDao;

    public MemberProfileResponse getProfile(Long memberId) {
        return memberProfileResponseDao.find(memberId);
    }
}
