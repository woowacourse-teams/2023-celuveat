package com.celuveat.restaurant.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.celuveat.common.PageResponse;
import com.celuveat.common.auth.Auth;
import com.celuveat.restaurant.application.RestaurantCorrectionService;
import com.celuveat.common.auth.LooseAuth;
import com.celuveat.common.optional.CustomOptional;
import com.celuveat.restaurant.application.RestaurantLikeService;
import com.celuveat.restaurant.application.RestaurantQueryFacade;
import com.celuveat.restaurant.application.RestaurantQueryService;
import com.celuveat.restaurant.application.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.RestaurantSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.SuggestCorrectionRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private static final int DEFAULT_SIZE = 18;
    private static final int NEARBY_DEFAULT_SIZE = 4;
    private static final String DEFAULT_DISTANCE = "3000";

    private final RestaurantLikeService restaurantLikeService;
    private final RestaurantQueryFacade restaurantQueryFacade;
    private final RestaurantQueryService restaurantQueryService;
    private final RestaurantCorrectionService restaurantCorrectionService;

    @GetMapping
    ResponseEntity<PageResponse<RestaurantQueryResponse>> findAll(
            @PageableDefault(size = DEFAULT_SIZE) Pageable pageable,
            @ModelAttribute RestaurantSearchCondRequest searchCondRequest,
            @Valid @ModelAttribute LocationSearchCondRequest locationSearchCondRequest,
            @LooseAuth CustomOptional<Long> memberId
    ) {
        RestaurantSearchCond restaurantSearchCond = searchCondRequest.toCondition();
        LocationSearchCond locationSearchCond = locationSearchCondRequest.toCondition();
        return ResponseEntity.ok(PageResponse.from(
                restaurantQueryFacade.findAll(restaurantSearchCond, locationSearchCond, pageable, memberId)
        ));
    }

    @PostMapping("/{restaurantId}/like")
    ResponseEntity<Void> like(@PathVariable Long restaurantId, @Auth Long memberId) {
        restaurantLikeService.like(restaurantId, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/like")
    ResponseEntity<List<RestaurantLikeQueryResponse>> getLikedRestaurants(@Auth Long memberId) {
        return ResponseEntity.ok(restaurantQueryService.findAllByMemberId(memberId));
    }
  
    @GetMapping("/{restaurantId}")
    ResponseEntity<RestaurantDetailQueryResponse> getRestaurantDetail(
            @PathVariable Long restaurantId,
            @RequestParam Long celebId
    ) {
        return ResponseEntity.ok(restaurantQueryFacade.findRestaurantDetailById(restaurantId, celebId));
    }
  
    @PostMapping("/{restaurantId}/correction")
    ResponseEntity<Void> suggestCorrection(
            @PathVariable Long restaurantId,
            @RequestBody SuggestCorrectionRequest request
    ) {
        restaurantCorrectionService.suggest(request.toCommand(restaurantId));
        return ResponseEntity.status(CREATED).build();
    }
  
    @GetMapping("/{restaurantId}/nearby")
    ResponseEntity<PageResponse<RestaurantQueryResponse>> findAllNearbyDistance(
            @PageableDefault(size = NEARBY_DEFAULT_SIZE) Pageable pageable,
            @PathVariable Long restaurantId,
            @RequestParam(required = false, defaultValue = DEFAULT_DISTANCE) Integer distance
    ) {
        Page<RestaurantQueryResponse> result = restaurantQueryService.findAllNearByDistanceWithoutSpecificRestaurant(
                distance,
                restaurantId,
                pageable
        );
        return ResponseEntity.ok(PageResponse.from(result));
    }
}
