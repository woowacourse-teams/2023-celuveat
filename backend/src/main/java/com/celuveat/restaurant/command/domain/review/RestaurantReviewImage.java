package com.celuveat.restaurant.command.domain.review;

import static lombok.AccessLevel.PROTECTED;

import com.celuveat.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class RestaurantReviewImage extends BaseEntity {

    private String name;

    public String name() {
        return name;
    }
}
