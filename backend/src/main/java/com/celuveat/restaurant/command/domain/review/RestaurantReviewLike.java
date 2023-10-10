package com.celuveat.restaurant.command.domain.review;

import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.CAN_NOT_LIKE_MY_REVIEW;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.domain.BaseEntity;
import com.celuveat.restaurant.exception.RestaurantReviewException;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_restaurant_review_member",
                columnNames = {
                        "restaurant_review_id",
                        "member_id"
                }
        )
})
public class RestaurantReviewLike extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_review_id")
    private RestaurantReview restaurantReview;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private OauthMember member;

    private RestaurantReviewLike(RestaurantReview restaurantReview, OauthMember member) {
        this.restaurantReview = restaurantReview;
        this.member = member;
    }

    public static RestaurantReviewLike create(RestaurantReview review, OauthMember member) {
        if (review.member().equals(member)) {
            throw new RestaurantReviewException(CAN_NOT_LIKE_MY_REVIEW);
        }
        RestaurantReviewLike reviewLike = new RestaurantReviewLike(review, member);
        review.clickLike();
        return reviewLike;
    }

    public void cancel() {
        restaurantReview.cancelLike();
    }

    public RestaurantReview restaurantReview() {
        return restaurantReview;
    }

    public OauthMember member() {
        return member;
    }
}
