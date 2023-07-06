package com.celuveat.celuveat.celeb.infra.persistence;

import static com.celuveat.celuveat.celeb.exception.CelebExceptionType.NOT_FOUND_CELEB;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.celeb.exception.CelebException;
import com.celuveat.celuveat.common.annotation.Dao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Dao
public class CelebDao {

    private static final RowMapper<Celeb> mapper = new DataClassRowMapper<>(Celeb.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CelebDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("celeb")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Celeb celeb) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(celeb);
        return simpleJdbcInsert.executeAndReturnKey(source)
                .longValue();
    }

    public Celeb getById(Long id) {
        String sql = """
                SELECT *
                FROM celeb
                WHERE id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, mapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new CelebException(NOT_FOUND_CELEB);
        }
    }
}
