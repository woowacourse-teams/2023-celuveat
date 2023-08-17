package com.celuveat.restaurant.application;

import com.celuveat.restaurant.application.dto.SuggestCorrectionRequestCommand;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.restaurant.domain.correction.RestaurantCorrection;
import com.celuveat.restaurant.domain.correction.RestaurantCorrectionRepository;
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

    public void suggest(SuggestCorrectionRequestCommand command) {
        Restaurant restaurant = restaurantRepository.getById(command.restaurantId());
        List<RestaurantCorrection> corrections = command.toDomains(restaurant);
        restaurantCorrectionRepository.saveAll(corrections);
    }
}
