package com.celuveat.restaurant.presentation;

import com.celuveat.common.PageResponse;
import com.celuveat.common.auth.LooseAuth;
import com.celuveat.restaurant.presentation.dto.RegionCodeCondRequest;
import com.celuveat.restaurant.query.RestaurantQueryService;
import com.celuveat.restaurant.query.dto.RestaurantSearchWithoutDistanceResponse;
import java.util.List;
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
@RequestMapping("/main-page")
public class MainPageController {

    private final RestaurantQueryService restaurantQueryService;

    @GetMapping("/region")
    ResponseEntity<PageResponse<RestaurantSearchWithoutDistanceResponse>> findAllByRegionCode(
            @LooseAuth Long memberId,
            @ModelAttribute RegionCodeCondRequest regionCodeCondRequest,
            @PageableDefault(size = 18) Pageable pageable
    ) {
        Page<RestaurantSearchWithoutDistanceResponse> result = restaurantQueryService.findByRegionCode(
                regionCodeCondRequest.toCondition(), pageable, memberId
        );
        return ResponseEntity.ok(PageResponse.from(result));
    }

    @GetMapping("/latest")
    ResponseEntity<List<RestaurantSearchWithoutDistanceResponse>> findLatest(
            @LooseAuth Long memberId
    ) {
        List<RestaurantSearchWithoutDistanceResponse> result = restaurantQueryService.findLatest(memberId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/recommendation")
    ResponseEntity<List<RestaurantSearchWithoutDistanceResponse>> findRecommendation(
            @LooseAuth Long memberId
    ) {
        List<RestaurantSearchWithoutDistanceResponse> result = restaurantQueryService.findRecommendation(memberId);
        return ResponseEntity.ok(result);
    }
}
