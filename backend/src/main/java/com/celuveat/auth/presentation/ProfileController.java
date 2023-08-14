package com.celuveat.auth.presentation;

import com.celuveat.auth.application.MemberQueryService;
import com.celuveat.auth.application.dto.MemberQueryResponse;
import com.celuveat.common.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final MemberQueryService memberQueryService;

    @GetMapping
    public ResponseEntity<MemberQueryResponse> getProfile(@Auth Long memberId) {
        return ResponseEntity.ok(memberQueryService.getProfile(memberId));
    }
}