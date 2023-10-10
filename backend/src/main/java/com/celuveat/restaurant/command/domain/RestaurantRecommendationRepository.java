package com.celuveat.restaurant.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRecommendationRepository extends JpaRepository<RestaurantRecommendation, Long> {
}
