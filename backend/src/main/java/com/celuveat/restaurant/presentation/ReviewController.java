package com.celuveat.restaurant.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.celuveat.common.auth.Auth;
import com.celuveat.restaurant.application.RestaurantReviewQueryService;
import com.celuveat.restaurant.application.RestaurantReviewService;
import com.celuveat.restaurant.application.dto.DeleteReviewCommand;
import com.celuveat.restaurant.application.dto.RestaurantReviewQueryResponse;
import com.celuveat.restaurant.presentation.dto.SaveReviewRequest;
import com.celuveat.restaurant.presentation.dto.UpdateReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final RestaurantReviewService restaurantReviewService;
    private final RestaurantReviewQueryService restaurantReviewQueryService;

    @GetMapping
    ResponseEntity<RestaurantReviewQueryResponse> findAllReviewsByRestaurantId(
            @RequestParam("restaurantId") Long restaurantId
    ) {
        return ResponseEntity.ok(restaurantReviewQueryService.findAllByRestaurantId(restaurantId));
    }

    @PostMapping
    ResponseEntity<Void> writeReview(
            @RequestBody SaveReviewRequest request,
            @Auth Long memberId
    ) {
        restaurantReviewService.create(request.toCommand(memberId));
        return ResponseEntity.status(CREATED).build();
    }

    @PatchMapping("/{reviewId}")
    ResponseEntity<Void> updateReview(
            @RequestBody UpdateReviewRequest request,
            @PathVariable Long reviewId,
            @Auth Long memberId
    ) {
        restaurantReviewService.update(request.toCommand(reviewId, memberId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reviewId}")
    ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @Auth Long memberId
    ) {
        restaurantReviewService.delete(new DeleteReviewCommand(reviewId, memberId));
        return ResponseEntity.noContent().build();
    }
}
