package com.celuveat.auth.presentation;

import com.celuveat.auth.query.MemberQueryService;
import com.celuveat.auth.query.dto.MemberProfileResponse;
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

    private final MemberQueryService memberQueryService;

    @GetMapping("/my")
    public ResponseEntity<MemberProfileResponse> getMemberProfile(@Auth Long memberId) {
        return ResponseEntity.ok(memberQueryService.getProfile(memberId));
    }
}
