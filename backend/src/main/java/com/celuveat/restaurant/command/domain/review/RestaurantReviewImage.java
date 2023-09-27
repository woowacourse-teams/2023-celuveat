package com.celuveat.restaurant.command.domain.review;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.celuveat.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class RestaurantReviewImage extends BaseEntity {

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_review_id")
    private RestaurantReview restaurantReview;

    public String name() {
        return name;
    }

    public RestaurantReview restaurantReview() {
        return restaurantReview;
    }
}
