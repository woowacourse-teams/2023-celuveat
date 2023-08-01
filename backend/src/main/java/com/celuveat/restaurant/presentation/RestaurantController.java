package com.celuveat.restaurant.presentation;

import com.celuveat.common.PageResponse;
import com.celuveat.common.auth.Auth;
import com.celuveat.restaurant.application.RestaurantLikeService;
import com.celuveat.restaurant.application.RestaurantQueryService;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.RestaurantSearchCondRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private static final int DEFAULT_SIZE = 18;

    private final RestaurantQueryService restaurantQueryService;
    private final RestaurantLikeService restaurantLikeService;

    @GetMapping
    ResponseEntity<PageResponse<RestaurantQueryResponse>> findAll(
            @PageableDefault(size = DEFAULT_SIZE) Pageable pageable,
            @ModelAttribute RestaurantSearchCondRequest searchCondRequest,
            @Valid @ModelAttribute LocationSearchCondRequest locationSearchCondRequest
    ) {
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(
                searchCondRequest.toCondition(),
                locationSearchCondRequest.toCondition(),
                pageable
        );
        return ResponseEntity.ok(PageResponse.from(result));
    }

    @PostMapping("/{restaurantId}/like")
    ResponseEntity<Void> like(@PathVariable Long restaurantId, @Auth Long memberId) {
        restaurantLikeService.like(restaurantId, memberId);
        return ResponseEntity.ok().build();
    }
}
