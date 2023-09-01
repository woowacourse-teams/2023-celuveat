package com.celuveat.celeb.command.domain;

import static lombok.AccessLevel.PROTECTED;

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
public class Celeb extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String youtubeChannelName;

    @Column(nullable = false)
    private String profileImageUrl;

    public String name() {
        return name;
    }

    public String youtubeChannelName() {
        return youtubeChannelName;
    }

    public String profileImageUrl() {
        return profileImageUrl;
    }
}
