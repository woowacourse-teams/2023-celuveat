package com.celuveat.celuveat.restaurant;

import com.celuveat.celuveat.common.annotation.Dao;
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
        return jdbcTemplate.queryForObject(sql, mapper, id);
    }
}
