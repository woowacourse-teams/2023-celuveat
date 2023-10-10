package com.celuveat.auth.query.dao;

import static com.celuveat.auth.command.domain.OauthServerType.KAKAO;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.auth.command.domain.OauthId;
import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.query.dto.OauthMemberProfileResponse;
import com.celuveat.common.DaoTest;
import com.celuveat.common.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("회원 프로필 조회 DAO(OauthMemberProfileResponseDao) 은(는)")
class OauthMemberProfileResponseDaoTest extends DaoTest {

    @Autowired
    private OauthMemberProfileResponseDao oauthMemberProfileResponseDao;

    private OauthMember 말랑;

    @Override
    protected TestData prepareTestData() {
        TestData testData = new TestData();
        말랑 = OauthMember.builder()
                .oauthId(new OauthId("mallang", KAKAO))
                .profileImageUrl("https://mallang.jpg")
                .nickname("말랑")
                .build();
        testData.addMembers(말랑);
        return testData;
    }

    @Test
    void 회원의_프로필을_조회한다() {
        // when
        OauthMemberProfileResponse oauthMemberProfileResponse = oauthMemberProfileResponseDao.find(말랑.id());

        // then
        assertThat(oauthMemberProfileResponse)
                .usingRecursiveComparison()
                .isEqualTo(new OauthMemberProfileResponse(
                        말랑.id(),
                        "말랑",
                        "https://mallang.jpg",
                        "KAKAO"
                ));
    }
}
