package com.celuveat.restaurant.presentation;

import com.celuveat.restaurant.application.RestaurantQueryService;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    ResponseEntity<List<RestaurantQueryResponse>> findAll(
            @ModelAttribute RestaurantSearchCond searchCond
    ) {
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll(searchCond);
        return ResponseEntity.ok(result);
    }
}
