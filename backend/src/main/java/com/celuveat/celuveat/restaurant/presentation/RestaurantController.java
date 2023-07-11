package com.celuveat.celuveat.restaurant.presentation;

import com.celuveat.celuveat.restaurant.application.RestaurantQueryService;
import com.celuveat.celuveat.restaurant.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
