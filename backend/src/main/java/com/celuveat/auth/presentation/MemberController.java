package com.celuveat.auth.presentation;

import com.celuveat.auth.query.OauthMemberQueryService;
import com.celuveat.auth.query.dto.OauthMemberProfileResponse;
import com.celuveat.common.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final OauthMemberQueryService OAuthMemberQueryService;

    @GetMapping("/my")
    public ResponseEntity<OauthMemberProfileResponse> getMemberProfile(@Auth Long memberId) {
        return ResponseEntity.ok(OAuthMemberQueryService.getProfile(memberId));
    }
}
