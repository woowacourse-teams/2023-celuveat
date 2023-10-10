package com.celuveat.restaurant.command.domain;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.domain.BaseEntity;
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
                name = "restaurant_member_unique",
                columnNames = {
                        "restaurant_id",
                        "member_id"
                }
        )
})
public class RestaurantLike extends BaseEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private OauthMember member;

    private RestaurantLike(Restaurant restaurant, OauthMember member) {
        this.restaurant = restaurant;
        this.member = member;
    }

    public static RestaurantLike create(Restaurant restaurant, OauthMember member) {
        restaurant.clickLike();
        return new RestaurantLike(restaurant, member);
    }

    public void cancel() {
        this.restaurant.cancelLike();
    }

    public Restaurant restaurant() {
        return restaurant;
    }

    public OauthMember member() {
        return member;
    }
}
