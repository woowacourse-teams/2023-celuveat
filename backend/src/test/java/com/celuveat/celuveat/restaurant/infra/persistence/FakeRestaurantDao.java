package com.celuveat.celuveat.restaurant.infra.persistence;

import static com.celuveat.celuveat.restaurant.exception.RestaurantExceptionType.NOT_FOUND_RESTAURANT;
import static org.mockito.Mockito.mock;

import com.celuveat.celuveat.restaurant.domain.Restaurant;
import com.celuveat.celuveat.restaurant.exception.RestaurantException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;

public class FakeRestaurantDao extends RestaurantDao {

    private final Map<Long, Restaurant> store = new HashMap<>();
    private long id = 1L;

    public FakeRestaurantDao() {
        super(mock(JdbcTemplate.class));
    }

    @Override
    public Long save(Restaurant restaurant) {
        store.put(id, restaurant);
        return id++;
    }

    @Override
    public Restaurant getById(Long id) {
        return Optional.ofNullable(store.get(id))
                .orElseThrow(() -> new RestaurantException(NOT_FOUND_RESTAURANT));
    }
}
