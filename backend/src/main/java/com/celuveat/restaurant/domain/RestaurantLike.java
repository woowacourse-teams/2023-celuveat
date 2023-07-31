package com.celuveat.restaurant.domain;

import static jakarta.persistence.FetchType.LAZY;

import com.celuveat.common.domain.BaseEntity;
import com.celuveat.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RestaurantLike extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Restaurant restaurant() {
        return restaurant;
    }

    public Member member() {
        return member;
    }
}
