package com.celuveat.celuveat.restaurant.infra.persistence;

import static com.celuveat.celuveat.restaurant.exception.RestaurantExceptionType.NOT_FOUND_RESTAURANT;

import com.celuveat.celuveat.common.annotation.Dao;
import com.celuveat.celuveat.restaurant.domain.Restaurant;
import com.celuveat.celuveat.restaurant.exception.RestaurantException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Dao
public class RestaurantDao {

    private static final RowMapper<Restaurant> mapper = new DataClassRowMapper<>(Restaurant.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public RestaurantDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("restaurant")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Restaurant restaurant) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(restaurant);
        return simpleJdbcInsert.executeAndReturnKey(source)
                .longValue();
    }

    public Restaurant getById(Long id) {
        String sql = """
                SELECT *
                FROM restaurant
                WHERE id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, mapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new RestaurantException(NOT_FOUND_RESTAURANT);
        }
    }
}
