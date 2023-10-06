package com.celuveat.restaurant.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.celuveat.common.auth.Auth;
import com.celuveat.common.auth.LooseAuth;
import com.celuveat.common.client.ImageUploadClient;
import com.celuveat.restaurant.command.application.RestaurantReviewLikeService;
import com.celuveat.restaurant.command.application.RestaurantReviewReportService;
import com.celuveat.restaurant.command.application.RestaurantReviewService;
import com.celuveat.restaurant.command.application.dto.DeleteReviewCommand;
import com.celuveat.restaurant.presentation.dto.ReportReviewRequest;
import com.celuveat.restaurant.presentation.dto.SaveReviewRequest;
import com.celuveat.restaurant.presentation.dto.UpdateReviewRequest;
import com.celuveat.restaurant.query.RestaurantReviewQueryService;
import com.celuveat.restaurant.query.dto.RestaurantReviewQueryResponse;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/reviews")
public class ReviewController {

    private final ImageUploadClient imageUploadClient;
    private final RestaurantReviewService restaurantReviewService;
    private final RestaurantReviewLikeService restaurantReviewLikeService;
    private final RestaurantReviewQueryService restaurantReviewQueryService;
    private final RestaurantReviewReportService restaurantReviewReportService;

    @GetMapping
    ResponseEntity<RestaurantReviewQueryResponse> findAllReviewsByRestaurantId(
            @LooseAuth Long memberId,
            @RequestParam Long restaurantId
    ) {
        return ResponseEntity.ok(restaurantReviewQueryService.findAllByRestaurantId(restaurantId, memberId));
    }

    @PostMapping
    ResponseEntity<Void> writeReview(
            @Auth Long memberId,
            @ModelAttribute SaveReviewRequest request
    ) {
        imageUploadClient.upload(request.images());
        restaurantReviewService.create(request.toCommand(memberId));
        return ResponseEntity.status(CREATED).build();
    }

    @PatchMapping("/{reviewId}")
    ResponseEntity<Void> updateReview(
            @Auth Long memberId,
            @RequestBody UpdateReviewRequest request,
            @PathVariable Long reviewId
    ) {
        restaurantReviewService.update(request.toCommand(reviewId, memberId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reviewId}")
    ResponseEntity<Void> deleteReview(
            @Auth Long memberId,
            @PathVariable Long reviewId
    ) {
        restaurantReviewService.delete(new DeleteReviewCommand(reviewId, memberId));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{reviewId}/like")
    ResponseEntity<Void> like(
            @Auth Long memberId,
            @PathVariable Long reviewId
    ) {
        restaurantReviewLikeService.like(reviewId, memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}/report")
    ResponseEntity<Void> report(
            @Auth Long memberId,
            @RequestBody ReportReviewRequest request,
            @PathVariable Long reviewId
    ) {
        restaurantReviewReportService.report(request.content(), reviewId, memberId);
        return ResponseEntity.ok().build();
    }
}
