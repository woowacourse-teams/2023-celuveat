package com.celuveat.celuveat.restaurant;

import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.음식점;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.common.annotation.DaoTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DaoTest
@DisplayName("RestaurantDao 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RestaurantDaoTest {

    @Autowired
    private RestaurantDao restaurantDao;

    @Test
    void 음식점을_저장한다() {
        // given
        Restaurant 맛집 = 음식점();

        // when
        Long savedId = restaurantDao.save(맛집);

        // then
        assertThat(savedId).isNotNull();
    }

    @Test
    void ID로_음식점을_찾는다() {
        // given
        Restaurant 맛집 = 음식점();
        Long savedId = restaurantDao.save(맛집);

        // when
        Restaurant result = restaurantDao.getById(savedId);

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(맛집);
    }
}
