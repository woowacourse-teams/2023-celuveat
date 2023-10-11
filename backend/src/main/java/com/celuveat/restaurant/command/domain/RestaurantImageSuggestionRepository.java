package com.celuveat.restaurant.command.domain;

import com.celuveat.auth.command.domain.OauthMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantImageSuggestionRepository extends JpaRepository<RestaurantImageSuggestion, Long> {

    List<RestaurantImageSuggestion> findAllByMember(OauthMember member);
}
