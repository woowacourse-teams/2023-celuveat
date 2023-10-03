package com.celuveat.restaurant.presentation;

import com.celuveat.common.PageResponse;
import com.celuveat.common.auth.LooseAuth;
import com.celuveat.restaurant.presentation.dto.AddressSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.DistrictCodeCondRequest;
import com.celuveat.restaurant.query.RestaurantQueryService;
import com.celuveat.restaurant.query.dto.RestaurantByAddressResponse;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: 임시방편임, 제거되어야 함
 */
@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final RestaurantQueryService restaurantQueryService;

    @GetMapping("/address")
    ResponseEntity<PageResponse<RestaurantSimpleResponse>> findByAddress(
            @LooseAuth Long memberId,
            @ModelAttribute AddressSearchCondRequest addressSearchCondRequest,
            @PageableDefault(size = 18) Pageable pageable
    ) {
        Page<RestaurantSimpleResponse> result = restaurantQueryService.findAllByAddress(
                addressSearchCondRequest.toCondition(), pageable, memberId
        );
        return ResponseEntity.ok(PageResponse.from(result));
    }

    @GetMapping("/address/v2")
    ResponseEntity<PageResponse<RestaurantByAddressResponse>> findByAddress(
            @ModelAttribute DistrictCodeCondRequest districtCodeCondRequest,
            @PageableDefault(size = 18) Pageable pageable
    ) {
        Page<RestaurantByAddressResponse> result = restaurantQueryService.findAllByAddressV2(
                districtCodeCondRequest.toCondition(), pageable
        );
        return ResponseEntity.ok(PageResponse.from(result));
    }
}
