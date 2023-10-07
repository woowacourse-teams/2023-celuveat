package com.celuveat.restaurant.command.domain.review;

import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.BAD_REVIEW_VALUE;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.PERMISSION_DENIED;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.domain.BaseEntity;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.exception.RestaurantReviewException;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class RestaurantReview extends BaseEntity {

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private OauthMember member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private double rating;

    private int likeCount;

    @OneToMany(orphanRemoval = true, cascade = {PERSIST, REMOVE})
    @JoinColumn(name = "restaurant_review_id", updatable = false, nullable = false)
    private List<RestaurantReviewImage> images = new ArrayList<>();

    private RestaurantReview(
            Restaurant restaurant, OauthMember member,
            String content, double rating,
            List<RestaurantReviewImage> images
    ) {
        validateRating(rating);
        this.restaurant = restaurant;
        this.member = member;
        this.content = content;
        this.rating = rating;
        this.images = images;
    }

    private void validateRating(double rating) {
        if (0.0 >= rating || rating > 5.0) {
            throw new RestaurantReviewException(BAD_REVIEW_VALUE);
        }
    }

    public static RestaurantReview create(
            Restaurant restaurant, OauthMember member,
            String content, double rating
    ) {
        return create(restaurant, member, content, rating, Collections.emptyList());
    }

    public static RestaurantReview create(
            Restaurant restaurant, OauthMember member,
            String content, double rating,
            List<String> imageNames
    ) {
        List<RestaurantReviewImage> images = imageNames.stream()
                .map(RestaurantReviewImage::new)
                .toList();
        RestaurantReview review = new RestaurantReview(restaurant, member, content, rating, images);
        restaurant.addReviewRating(rating);
        return review;
    }

    public void update(Long memberId, String content, double updateRating) {
        checkOwner(memberId);
        validateRating(updateRating);
        restaurant.deleteReviewRating(rating);
        restaurant.addReviewRating(updateRating);
        this.content = content;
        this.rating = updateRating;
    }

    private void checkOwner(Long memberId) {
        if (!member.id().equals(memberId)) {
            throw new RestaurantReviewException(PERMISSION_DENIED);
        }
    }

    public void delete(Long memberId) {
        checkOwner(memberId);
        restaurant.deleteReviewRating(rating);
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

    public double rating() {
        return rating;
    }

    public int likeCount() {
        return likeCount;
    }

    public List<RestaurantReviewImage> images() {
        return images;
    }
}
