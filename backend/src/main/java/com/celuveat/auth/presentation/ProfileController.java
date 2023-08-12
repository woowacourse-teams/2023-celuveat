package com.celuveat.auth.presentation;

import com.celuveat.auth.application.ProfileService;
import com.celuveat.auth.application.dto.ProfileResponse;
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

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(@Auth Long memberId) {
        return ResponseEntity.ok(profileService.getProfile(memberId));
    }
}
