package com.celuveat.restaurant.application;

import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.restaurant.domain.correction.RestaurantCorrection;
import com.celuveat.restaurant.domain.correction.RestaurantCorrectionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
@RequiredArgsConstructor
public class RestaurantServiceTestHelper {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantCorrectionRepository restaurantCorrectionRepository;

    public Long 음식점을_저장한다(String 이름) {
        return restaurantRepository.save(Restaurant.builder()
                .name(이름)
                .category(이름)
                .roadAddress(이름)
                .latitude(1.1)
                .longitude(1.1)
                .naverMapUrl("naver" + 이름)
                .phoneNumber("010-" + 이름)
                .build()).id();
    }

    public Restaurant 음식점을_조회한다(Long id) {
        return restaurantRepository.getById(id);
    }

    public List<RestaurantCorrection> 음식점_정보_수정_제안을_모두_조회한다() {
        return restaurantCorrectionRepository.findAll();
    }
}
