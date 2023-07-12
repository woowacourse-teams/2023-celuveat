package com.celuveat.celuveat.admin.presentation;

import static com.celuveat.celuveat.common.auth.AuthSession.ADMIN_AUTH_SESSION_KEY;

import com.celuveat.celuveat.admin.application.AdminService;
import com.celuveat.celuveat.admin.application.dto.AdminLoginResponse;
import com.celuveat.celuveat.admin.presentation.dto.AdminLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminService adminService;

    @PostMapping("/login")
    ResponseEntity<AdminLoginResponse> login(
            @RequestBody @Valid AdminLoginRequest request,
            HttpServletRequest httpRequest
    ) {
        Long adminId = adminService.login(request.username(), request.password());
        String sessionId = setAuthSession(httpRequest, adminId);
        AdminLoginResponse response = new AdminLoginResponse(sessionId);
        return ResponseEntity.ok(response);
    }

    private String setAuthSession(HttpServletRequest httpRequest, Long adminId) {
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(ADMIN_AUTH_SESSION_KEY, adminId);
        return session.getId();
    }
}
