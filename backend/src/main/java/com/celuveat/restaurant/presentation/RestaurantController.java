package com.celuveat.restaurant.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.celuveat.common.PageResponse;
import com.celuveat.common.auth.Auth;
import com.celuveat.common.auth.LooseAuth;
import com.celuveat.restaurant.command.application.RestaurantCorrectionService;
import com.celuveat.restaurant.command.application.RestaurantLikeService;
import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.RestaurantSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.SuggestCorrectionRequest;
import com.celuveat.restaurant.query.RestaurantQueryFacade;
import com.celuveat.restaurant.query.RestaurantQueryService;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    private final RestaurantLikeService restaurantLikeService;
    private final RestaurantQueryFacade restaurantQueryFacade;
    private final RestaurantQueryService restaurantQueryService;
    private final RestaurantCorrectionService restaurantCorrectionService;

    @GetMapping("/{restaurantId}")
    ResponseEntity<RestaurantDetailResponse> getRestaurantDetail(
            @PathVariable Long restaurantId,
            @RequestParam Long celebId,
            @LooseAuth Long memberId
    ) {
        RestaurantDetailResponse result = restaurantQueryFacade.findRestaurantDetailById(
                restaurantId, celebId, memberId
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping
    ResponseEntity<PageResponse<RestaurantSimpleResponse>> findAll(
            @LooseAuth Long memberId,
            @ModelAttribute RestaurantSearchCondRequest searchCondRequest,
            @Valid @ModelAttribute LocationSearchCondRequest locationSearchCondRequest,
            @PageableDefault(size = 18) Pageable pageable
    ) {
        RestaurantSearchCond restaurantSearchCond = searchCondRequest.toCondition();
        LocationSearchCond locationSearchCond = locationSearchCondRequest.toCondition();
        Page<RestaurantSimpleResponse> result = restaurantQueryFacade.findAll(
                restaurantSearchCond, locationSearchCond, pageable, memberId
        );
        return ResponseEntity.ok(PageResponse.from(result));
    }

    @GetMapping("/like")
    ResponseEntity<List<LikedRestaurantQueryResponse>> getLikedRestaurants(@Auth Long memberId) {
        List<LikedRestaurantQueryResponse> result = restaurantQueryService.findAllLikedRestaurantByMemberId(
                memberId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{restaurantId}/like")
    ResponseEntity<Void> like(@PathVariable Long restaurantId, @Auth Long memberId) {
        restaurantLikeService.like(restaurantId, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{restaurantId}/nearby")
    ResponseEntity<PageResponse<RestaurantSimpleResponse>> findAllNearbyDistance(
            @PathVariable Long restaurantId,
            @RequestParam(required = false, defaultValue = "3000") Integer distance,
            @LooseAuth Long memberId,
            @PageableDefault(size = 4) Pageable pageable
    ) {
        Page<RestaurantSimpleResponse> result =
                restaurantQueryService.findAllNearByDistanceWithoutSpecificRestaurant(
                        restaurantId,
                        distance,
                        memberId,
                        pageable
                );
        return ResponseEntity.ok(PageResponse.from(result));
    }

    @PostMapping("/{restaurantId}/correction")
    ResponseEntity<Void> suggestCorrection(
            @PathVariable Long restaurantId,
            @RequestBody SuggestCorrectionRequest request
    ) {
        restaurantCorrectionService.suggest(request.toCommand(restaurantId));
        return ResponseEntity.status(CREATED).build();
    }
}
