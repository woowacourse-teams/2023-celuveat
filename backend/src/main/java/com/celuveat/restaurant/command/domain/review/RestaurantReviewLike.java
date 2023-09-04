package com.celuveat.restaurant.command.domain.review;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
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

    public RestaurantReview restaurantReview() {
        return restaurantReview;
    }

    public OauthMember member() {
        return member;
    }
}
