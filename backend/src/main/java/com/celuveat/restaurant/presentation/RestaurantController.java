package com.celuveat.restaurant.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.celuveat.common.PageResponse;
import com.celuveat.common.auth.Auth;
import com.celuveat.common.auth.LooseAuth;
import com.celuveat.restaurant.command.application.RestaurantCorrectionService;
import com.celuveat.restaurant.command.application.RestaurantLikeService;
import com.celuveat.restaurant.command.application.RestaurantService;
import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.RestaurantSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.SuggestCorrectionRequest;
import com.celuveat.restaurant.query.RestaurantQueryService;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchResponse;
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

    private final RestaurantService restaurantService;
    private final RestaurantLikeService restaurantLikeService;
    private final RestaurantQueryService restaurantQueryService;
    private final RestaurantCorrectionService restaurantCorrectionService;

    @GetMapping("/{restaurantId}")
    ResponseEntity<RestaurantDetailResponse> getRestaurantDetail(
            @LooseAuth Long memberId,
            @PathVariable Long restaurantId,
            @RequestParam Long celebId
    ) {
        RestaurantDetailResponse result = restaurantQueryService.findRestaurantDetailById(
                restaurantId, celebId, memberId
        );
        restaurantService.increaseViewCount(restaurantId);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    ResponseEntity<PageResponse<RestaurantSearchResponse>> findAll(
            @LooseAuth Long memberId,
            @ModelAttribute RestaurantSearchCondRequest searchCondRequest,
            @Valid @ModelAttribute LocationSearchCondRequest locationSearchCondRequest,
            @PageableDefault(size = 18) Pageable pageable
    ) {
        RestaurantSearchCond restaurantSearchCond = searchCondRequest.toCondition();
        LocationSearchCond locationSearchCond = locationSearchCondRequest.toCondition();
        Page<RestaurantSearchResponse> result = restaurantQueryService.findAllWithMemberLiked(
                restaurantSearchCond, locationSearchCond, pageable, memberId
        );
        return ResponseEntity.ok(PageResponse.from(result));
    }

    @GetMapping("/like")
    ResponseEntity<List<LikedRestaurantQueryResponse>> getLikedRestaurants(@Auth Long memberId) {
        List<LikedRestaurantQueryResponse> result = restaurantQueryService.findAllLikedRestaurantByMemberId(memberId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{restaurantId}/like")
    ResponseEntity<Void> like(@PathVariable Long restaurantId, @Auth Long memberId) {
        restaurantLikeService.like(restaurantId, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{restaurantId}/nearby")
    ResponseEntity<PageResponse<RestaurantSearchResponse>> findAllNearbyDistance(
            @LooseAuth Long memberId,
            @PathVariable Long restaurantId,
            @RequestParam(required = false, defaultValue = "3000") Integer distance,
            @PageableDefault(size = 4) Pageable pageable
    ) {
        Page<RestaurantSearchResponse> result =
                restaurantQueryService.findAllNearByDistanceWithoutSpecificRestaurant(
                        restaurantId,
                        distance,
                        pageable,
                        memberId
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

    @GetMapping("/latest")
    ResponseEntity<List<RestaurantSearchResponse>> findLatest(
            @LooseAuth Long memberId
    ) {
        List<RestaurantSearchResponse> result = restaurantQueryService.findLatest(memberId);
        return ResponseEntity.ok(result);
    }
}
