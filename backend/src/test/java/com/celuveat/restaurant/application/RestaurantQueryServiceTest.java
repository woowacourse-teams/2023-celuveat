package com.celuveat.restaurant.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.SeedData;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@DisplayName("RestaurantQueryService 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RestaurantQueryServiceTest {

    @Autowired
    private SeedData seedData;

    @Autowired
    private RestaurantQueryService restaurantQueryService;

    @Test
    void 전체_음식점_조회_테스트() {
        // given
        List<RestaurantQueryResponse> expected = seedData.insertSeedData();

        // when
        List<RestaurantQueryResponse> result = restaurantQueryService.findAll();

        // then
        assertThat(result).usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
