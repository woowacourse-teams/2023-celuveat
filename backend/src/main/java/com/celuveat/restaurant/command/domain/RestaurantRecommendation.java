package com.celuveat.restaurant.command.domain;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.celuveat.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class RestaurantRecommendation extends BaseEntity {

    @OneToOne(fetch = LAZY, optional = false)
    private Restaurant restaurant;

    public RestaurantRecommendation(final Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant restaurant() {
        return restaurant;
    }
}
