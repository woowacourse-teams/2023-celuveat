package com.celuveat.auth.infra.kakao.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        LocalDateTime connected_at
) {
}
