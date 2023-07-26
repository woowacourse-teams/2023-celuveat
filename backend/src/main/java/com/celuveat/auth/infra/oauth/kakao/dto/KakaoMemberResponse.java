package com.celuveat.auth.infra.oauth.kakao.dto;

import static com.celuveat.auth.domain.OauthServer.KAKAO;

import com.celuveat.auth.domain.OauthId;
import com.celuveat.auth.domain.OauthMember;
import java.time.LocalDateTime;

public record KakaoMemberResponse(
        Long id,
        boolean has_signed_up,
        LocalDateTime connected_at,
        KakaoAccount kakao_account
) {

    public OauthMember toDomain() {
        return OauthMember.builder()
                .oauthId(new OauthId(String.valueOf(id), KAKAO))
                .nickname(kakao_account.profile.nickname)
                .profileImagePath(kakao_account.profile.profile_image_url)
                .build();
    }

    public record KakaoAccount(
            boolean profile_needs_agreement,
            boolean profile_nickname_needs_agreement,
            boolean profile_image_needs_agreement,
            Profile profile,
            boolean name_needs_agreement,
            String name,
            boolean email_needs_agreement,
            boolean is_email_valid,
            boolean is_email_verified,
            String email,
            boolean age_range_needs_agreement,
            String age_range,
            boolean birthyear_needs_agreement,
            String birthyear,
            boolean birthday_needs_agreement,
            String birthday,
            String birthday_type,
            boolean gender_needs_agreement,
            String gender,
            boolean phone_number_needs_agreement,
            String phone_number,
            boolean ci_needs_agreement,
            String ci,
            LocalDateTime ci_authenticated_at
    ) {
    }

    public record Profile(
            String nickname,
            String thumbnail_image_url,
            String profile_image_url,
            boolean is_default_image
    ) {
    }
}
