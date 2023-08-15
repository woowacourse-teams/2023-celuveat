package com.celuveat.restaurant.domain.review;

import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.PERMISSION_DENIED;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.RESTAURANT_REVIEW_MISMATCH;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.common.domain.BaseEntity;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.exception.RestaurantReviewException;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class RestaurantReview extends BaseEntity {

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private OauthMember oauthMember;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public void updateContent(String content, Long memberId, Long restaurantId) {
        checkMemberAndRestaurantMatched(memberId, restaurantId);
        this.content = content;
    }

    public void checkMemberAndRestaurantMatched(Long memberId, Long restaurantId) {
        if (!oauthMember.id().equals(memberId)) {
            throw new RestaurantReviewException(PERMISSION_DENIED);
        }
        if (!restaurant.id().equals(restaurantId)) {
            throw new RestaurantReviewException(RESTAURANT_REVIEW_MISMATCH);
        }
    }

    public String content() {
        return content;
    }

    public OauthMember oauthMember() {
        return oauthMember;
    }

    public Restaurant restaurant() {
        return restaurant;
    }
}
