package com.celuveat.restaurant.command.domain;

import static lombok.AccessLevel.PROTECTED;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Restaurant extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String phoneNumber;

    @Column(nullable = false)
    private String naverMapUrl;

    private int viewCount;

    private int likeCount;

    public RestaurantLike clickLike(OauthMember member) {
        this.likeCount += 1;
        return new RestaurantLike(this, member);
    }

    public void cancelLike() {
        this.likeCount -= 1;
    }

    public void increaseViewCount() {
        this.viewCount += 1;
    }

    public String name() {
        return name;
    }

    public String category() {
        return category;
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
}
