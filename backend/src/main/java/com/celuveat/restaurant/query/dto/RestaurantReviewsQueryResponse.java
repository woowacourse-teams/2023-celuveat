package com.celuveat.restaurant.query.dto;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.util.Base64Util;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import java.util.List;
import java.util.Map;

public record RestaurantReviewsQueryResponse(
        int totalElementsCount,
        List<RestaurantReviewSingleQueryResponse> reviews
) {

    public static RestaurantReviewsQueryResponse from(
            List<RestaurantReview> restaurantReviews,
            Map<Long, List<RestaurantReviewImage>> restaurantReviewImages,
            Map<Long, Boolean> isLikedByReviewId
    ) {
        List<RestaurantReviewSingleQueryResponse> reviews = restaurantReviews.stream()
                .map(review -> RestaurantReviewSingleQueryResponse.of(
                        review,
                        restaurantReviewImages.getOrDefault(review.id(), List.of()),
                        isLikedByReviewId.getOrDefault(review.id(), false)
                )).toList();
        return new RestaurantReviewsQueryResponse(reviews.size(), reviews);
    }

    public record RestaurantReviewSingleQueryResponse(
            Long id,
            Long memberId,
            String nickname,
            String profileImageUrl,
            String content,
            String createdDate,
            int likeCount,
            boolean isLiked,
            double rating,
            List<String> images
    ) {

        public static RestaurantReviewSingleQueryResponse of(
                RestaurantReview restaurantReview,
                List<RestaurantReviewImage> images,
                boolean isLiked
        ) {
            OauthMember oauthMember = restaurantReview.member();
            return new RestaurantReviewSingleQueryResponse(
                    restaurantReview.id(),
                    oauthMember.id(),
                    oauthMember.nickname(),
                    oauthMember.profileImageUrl(),
                    restaurantReview.content(),
                    restaurantReview.createdDate().toLocalDate().toString(),
                    restaurantReview.likeCount(),
                    isLiked,
                    restaurantReview.rating(),
                    images.stream()
                            .map(RestaurantReviewImage::name)
                            .toList()
            );
        }

        @Override
        public List<String> images() {
            return images.stream()
                    .map(Base64Util::encode)
                    .toList();
        }
    }
}
