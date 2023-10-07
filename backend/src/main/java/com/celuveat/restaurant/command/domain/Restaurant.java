package com.celuveat.restaurant.command.domain;

import static lombok.AccessLevel.PROTECTED;
import static org.geolatte.geom.builder.DSL.g;
import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

import com.celuveat.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.geolatte.geom.Point;
import org.geolatte.geom.builder.DSL;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Restaurant extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String superCategory;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String phoneNumber;

    @Column(nullable = false)
    private String naverMapUrl;

    private Point<?> point;

    private int viewCount;

    private int likeCount;

    private int reviewCount;

    private double totalRating;

    @Builder
    public Restaurant(
            String name,
            String category,
            String superCategory,
            String roadAddress,
            Double latitude,
            Double longitude,
            String phoneNumber,
            String naverMapUrl
    ) {
        this.name = name;
        this.category = category;
        this.superCategory = superCategory;
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.naverMapUrl = naverMapUrl;
        this.point = DSL.point(WGS84, g(longitude, latitude));
    }

    public void clickLike() {
        this.likeCount += 1;
    }

    public void cancelLike() {
        this.likeCount -= 1;
    }

    public void increaseViewCount() {
        this.viewCount += 1;
    }

    public void addReviewRating(double rating) {
        this.totalRating += rating;
        this.reviewCount += 1;
    }

    public void deleteReviewRating(double rating) {
        this.totalRating -= rating;
        this.reviewCount -= 1;
    }

    public String name() {
        return name;
    }

    public String category() {
        return category;
    }

    public String superCategory() {
        return superCategory;
    }

    public String roadAddress() {
        return roadAddress;
    }

    public Double latitude() {
        return latitude;
    }

    public Double longitude() {
        return longitude;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public String naverMapUrl() {
        return naverMapUrl;
    }

    public Integer viewCount() {
        return viewCount;
    }

    public int likeCount() {
        return likeCount;
    }

    public Point<?> point() {
        return point;
    }

    public int reviewCount() {
        return reviewCount;
    }

    public double totalRating() {
        return totalRating;
    }
}
