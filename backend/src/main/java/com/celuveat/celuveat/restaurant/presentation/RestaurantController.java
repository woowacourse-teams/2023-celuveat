package com.celuveat.celuveat.restaurant.presentation;

import com.celuveat.celuveat.common.page.Page;
import com.celuveat.celuveat.common.page.PageCond;
import com.celuveat.celuveat.common.page.SliceResponse;
import com.celuveat.celuveat.restaurant.application.RestaurantQueryService;
import com.celuveat.celuveat.restaurant.application.dto.RestaurantSearchResponse;
import com.celuveat.celuveat.restaurant.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantQueryService restaurantQueryService;

    @GetMapping("/{id}")
    ResponseEntity<Restaurant> findById(@PathVariable Long id) {
        Restaurant result = restaurantQueryService.findById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    ResponseEntity<SliceResponse<RestaurantSearchResponse>> findById(
            @RequestParam(name = "celebId", required = true) Long celebId,
            @Page PageCond pageCond
    ) {
        SliceResponse<RestaurantSearchResponse> result =
                restaurantQueryService.findAllByCelebIdUploadDateDesc(celebId, pageCond);
        return ResponseEntity.ok(result);
    }
}
