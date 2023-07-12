package com.celuveat.celuveat.admin.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminLoginRequest(
        @NotBlank(message = "사용자 이름을 입력해주세요")
        String name,
        @NotBlank(message = "비밀번호를 입력해주세요")
        String password
) {
}
