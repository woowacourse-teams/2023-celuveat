package com.celuveat.administrativedistrict.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.geolatte.geom.Geometry;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class AdministrativeDistrict {

    @Id
    private Long id;

    @Column(nullable = false)
    private Geometry<?> polygon;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String englishName;

    @Column(nullable = false)
    private String koreanName;

    public Long id() {
        return id;
    }

    public Geometry<?> polygon() {
        return polygon;
    }

    public String code() {
        return code;
    }

    public String englishName() {
        return englishName;
    }

    public String koreanName() {
        return koreanName;
    }
}
