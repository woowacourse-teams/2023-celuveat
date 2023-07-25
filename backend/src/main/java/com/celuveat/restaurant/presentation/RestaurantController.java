package com.celuveat.restaurant.presentation;

import com.celuveat.common.PageResponse;
import com.celuveat.restaurant.application.RestaurantQueryService;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantQueryService restaurantQueryService;

    @GetMapping
    ResponseEntity<PageResponse<RestaurantQueryResponse>> findAll(
            @PageableDefault(size = 18) Pageable pageable,
            @ModelAttribute RestaurantSearchCond searchCond,
            @ModelAttribute LocationSearchCond locationSearchCond
    ) {
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAll(searchCond, locationSearchCond, pageable);
        return ResponseEntity.ok(PageResponse.from(result));
    }
}
