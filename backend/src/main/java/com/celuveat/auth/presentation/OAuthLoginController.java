package com.celuveat.auth.presentation;

import com.celuveat.auth.application.AuthService;
import com.celuveat.auth.infra.kakao.KakaoOAuthClient;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthLoginController {

    private final AuthService authService;
    private final KakaoOAuthClient kakaoOAuthClient;

    @GetMapping("/kakao")
    void loginWithKakao(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:3000");
        authService.login(code, kakaoOAuthClient);
    }
}
