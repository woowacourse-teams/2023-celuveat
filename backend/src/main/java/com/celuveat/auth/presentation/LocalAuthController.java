package com.celuveat.auth.presentation;

import static com.celuveat.auth.domain.OauthServerType.KAKAO;
import static com.celuveat.common.auth.AuthConstant.JSESSION_ID;

import com.celuveat.auth.domain.OauthId;
import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.auth.presentation.dto.SessionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("!prod")
@RequiredArgsConstructor
public class LocalAuthController {

    private final OauthMemberRepository oauthMemberRepository;

    @GetMapping("/login/local")
    ResponseEntity<SessionResponse> login(@RequestParam String id, HttpServletRequest request) {
        OauthId oauthId = new OauthId(id, KAKAO);
        OauthMember oauthMember = new OauthMember(oauthId, id, null);
        OauthMember savedMember = oauthMemberRepository.findByOauthId(oauthId)
                .orElseGet(() -> oauthMemberRepository.save(oauthMember));
        HttpSession session = request.getSession(true);
        session.setAttribute(JSESSION_ID, savedMember.id());
        return ResponseEntity.ok(new SessionResponse(session.getId()));
    }
}
