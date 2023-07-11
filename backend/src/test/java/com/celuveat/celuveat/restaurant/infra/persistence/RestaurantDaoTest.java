package com.celuveat.celuveat.restaurant.infra.persistence;

import static com.celuveat.celuveat.restaurant.exception.RestaurantExceptionType.NOT_FOUND_RESTAURANT;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.맥도날드;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.celuveat.common.annotation.DaoTest;
import com.celuveat.celuveat.common.exception.BaseExceptionType;
import com.celuveat.celuveat.restaurant.domain.Restaurant;
import com.celuveat.celuveat.restaurant.exception.RestaurantException;
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
        Restaurant 맛집 = 맥도날드();

        // when
        Long savedId = restaurantDao.save(맛집);

        // then
        assertThat(savedId).isNotNull();
    }

    @Test
    void ID로_음식점을_찾는다() {
        // given
        Restaurant 맛집 = 맥도날드();
        Long savedId = restaurantDao.save(맛집);

        // when
        Restaurant result = restaurantDao.getById(savedId);

        // then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(맛집);
    }

    @Test
    void 음식점이_없는_경우_예외가_발생한다() {
        // when
        BaseExceptionType exceptionType = assertThrows(RestaurantException.class, () ->
                restaurantDao.getById(1L)
        ).exceptionType();

        // then
        assertThat(exceptionType).isEqualTo(NOT_FOUND_RESTAURANT);
    }
}
