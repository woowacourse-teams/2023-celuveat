package com.celuveat.restaurant.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.celuveat.common.PageResponse;
import com.celuveat.common.auth.Auth;
import com.celuveat.common.auth.LooseAuth;
import com.celuveat.restaurant.application.RestaurantCorrectionService;
import com.celuveat.restaurant.application.RestaurantLikeService;
import com.celuveat.restaurant.application.RestaurantQueryFacade;
import com.celuveat.restaurant.application.RestaurantQueryService;
import com.celuveat.restaurant.application.RestaurantReviewQueryService;
import com.celuveat.restaurant.application.RestaurantReviewService;
import com.celuveat.restaurant.application.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantReviewQueryResponse;
import com.celuveat.restaurant.application.dto.UpdateReviewRequest;
import com.celuveat.restaurant.application.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.application.dto.RestaurantSimpleResponse;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import com.celuveat.restaurant.presentation.dto.LocationSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.RestaurantSearchCondRequest;
import com.celuveat.restaurant.presentation.dto.SaveReviewRequest;
import com.celuveat.restaurant.presentation.dto.SuggestCorrectionRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final RestaurantReviewQueryService restaurantReviewQueryService;
    private final RestaurantReviewService restaurantReviewService;

    @GetMapping("/{restaurantId}")
    ResponseEntity<RestaurantDetailResponse> getRestaurantDetail(
            @PathVariable Long restaurantId,
            @RequestParam Long celebId
    ) {
        RestaurantDetailResponse result = restaurantQueryFacade.findRestaurantDetailById(
                restaurantId, celebId
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping
    ResponseEntity<PageResponse<RestaurantSimpleResponse>> findAll(
            @LooseAuth Optional<Long> memberId,
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
    ResponseEntity<List<RestaurantLikeQueryResponse>> getLikedRestaurants(@Auth Long memberId) {
        List<RestaurantLikeQueryResponse> result = restaurantQueryService.findAllLikedRestaurantByMemberId(
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
            @PageableDefault(size = 4) Pageable pageable
    ) {
        Page<RestaurantSimpleResponse> result =
                restaurantQueryService.findAllNearByDistanceWithoutSpecificRestaurant(
                        distance, restaurantId, pageable
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

    @GetMapping("/{restaurantId}/reviews")
    ResponseEntity<RestaurantReviewQueryResponse> findAllReviewsByRestaurantId(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantReviewQueryService.findAllByRestaurantId(restaurantId));
    }

    @PostMapping("/{restaurantId}/reviews")
    ResponseEntity<Void> writeReview(
            @RequestBody SaveReviewRequest request,
            @PathVariable Long restaurantId,
            @Auth Long memberId
    ) {
        restaurantReviewService.create(request.toCommand(memberId, restaurantId));
        return ResponseEntity.status(CREATED).build();
    }

    @PatchMapping("/{restaurantId}/reviews/{reviewId}")
    ResponseEntity<Void> updateReview(
            @RequestBody UpdateReviewRequest request,
            @PathVariable Long restaurantId,
            @PathVariable Long reviewId,
            @Auth Long memberId
    ) {
        restaurantReviewService.updateReview(request, reviewId, memberId, restaurantId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{restaurantId}/reviews/{reviewId}")
    ResponseEntity<Void> deleteReview(
            @PathVariable Long restaurantId,
            @PathVariable Long reviewId,
            @Auth Long memberId
    ) {
        restaurantReviewService.deleteReview(reviewId, memberId, restaurantId);
        return ResponseEntity.noContent().build();
    }
}
