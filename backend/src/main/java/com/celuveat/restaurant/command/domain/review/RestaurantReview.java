package com.celuveat.restaurant.command.domain.review;

import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.PERMISSION_DENIED;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.domain.BaseEntity;
import com.celuveat.restaurant.command.domain.Restaurant;
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
    private OauthMember member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private Double rating;

    private int likeCount;

    public RestaurantReview(String content, OauthMember member, Restaurant restaurant, Double rating) {
        this(content, member, restaurant, rating, 0);
    }

    public void updateContent(String content, Long memberId, Double rating) {
        checkOwner(memberId);
        this.content = content;
        this.rating = rating;
    }

    public void checkOwner(Long memberId) {
        if (!member.id().equals(memberId)) {
            throw new RestaurantReviewException(PERMISSION_DENIED);
        }
    }

    public void cancelLike() {
        this.likeCount -= 1;
    }

    public void clickLike() {
        this.likeCount += 1;
    }

    public String content() {
        return content;
    }

    public OauthMember member() {
        return member;
    }

    public Restaurant restaurant() {
        return restaurant;
    }

    public Double rating() {
        return rating;
    }

    public int likeCount() {
        return likeCount;
    }
}
