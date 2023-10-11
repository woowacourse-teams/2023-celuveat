package com.celuveat.event.command.domain;

import static lombok.AccessLevel.PROTECTED;

import com.celuveat.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class EventImage extends BaseEntity {

    @Column(nullable = false)
    private String instagramId;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private String imageName;

    @Builder
    public EventImage(String instagramId, String restaurantName, String imageName) {
        this.instagramId = instagramId;
        this.restaurantName = restaurantName;
        this.imageName = imageName;
    }

    public String instagramId() {
        return instagramId;
    }

    public String restaurantName() {
        return restaurantName;
    }

    public String imageName() {
        return imageName;
    }
}
