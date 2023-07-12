package com.celuveat.celuveat.admin.infra.persistence;

import static com.celuveat.celuveat.admin.exception.AdminExceptionType.NOT_FOUND_ADMIN;

import com.celuveat.celuveat.admin.domain.Admin;
import com.celuveat.celuveat.admin.exception.AdminException;
import com.celuveat.celuveat.common.annotation.Dao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Dao
public class AdminDao {

    private static final RowMapper<Admin> mapper = new DataClassRowMapper<>(Admin.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public AdminDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("admin")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Admin admin) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(admin);
        return simpleJdbcInsert.executeAndReturnKey(source)
                .longValue();
    }

    public Admin getByUsername(String username) {
        String sql = """
                SELECT *
                FROM admin
                WHERE username = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, mapper, username);
        } catch (EmptyResultDataAccessException e) {
            throw new AdminException(NOT_FOUND_ADMIN);
        }
    }
}
