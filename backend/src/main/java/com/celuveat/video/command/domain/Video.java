package com.celuveat.video.command.domain;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.common.domain.BaseEntity;
import com.celuveat.restaurant.command.domain.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Video extends BaseEntity {

    @Column(nullable = false)
    private String youtubeUrl;

    @Column(nullable = false)
    private LocalDate uploadDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "celeb_id")
    private Celeb celeb;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public String youtubeUrl() {
        return youtubeUrl;
    }

    public LocalDate uploadDate() {
        return uploadDate;
    }

    public Celeb celeb() {
        return celeb;
    }

    public Restaurant restaurant() {
        return restaurant;
    }
}
