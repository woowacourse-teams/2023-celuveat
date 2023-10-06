package com.celuveat.restaurant.command.application;

import com.celuveat.restaurant.command.application.dto.SuggestCorrectionCommand;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.correction.RestaurantCorrection;
import com.celuveat.restaurant.command.domain.correction.RestaurantCorrectionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantCorrectionService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantCorrectionRepository restaurantCorrectionRepository;

    public void suggest(SuggestCorrectionCommand command) {
        Restaurant restaurant = restaurantRepository.getById(command.restaurantId());
        List<RestaurantCorrection> corrections = command.toDomains(restaurant);
        restaurantCorrectionRepository.saveAll(corrections);
    }
}
