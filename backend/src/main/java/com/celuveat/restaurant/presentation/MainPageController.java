package com.celuveat.restaurant.presentation;

import com.celuveat.common.PageResponse;
import com.celuveat.common.auth.LooseAuth;
import com.celuveat.restaurant.presentation.dto.RegionCodeCondRequest;
import com.celuveat.restaurant.query.RestaurantQueryService;
import com.celuveat.restaurant.query.dto.RestaurantByRegionCodeQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final RestaurantQueryService restaurantQueryService;

    @GetMapping("/address")
    ResponseEntity<PageResponse<RestaurantByRegionCodeQueryResponse>> findAllByRegionCode(
            @LooseAuth Long memberId,
            @ModelAttribute RegionCodeCondRequest regionCodeCondRequest,
            @PageableDefault(size = 18) Pageable pageable
    ) {
        Page<RestaurantByRegionCodeQueryResponse> result = restaurantQueryService.findAllByRegionCode(
                regionCodeCondRequest.toCondition(), pageable, memberId
        );
        return ResponseEntity.ok(PageResponse.from(result));
    }
}
