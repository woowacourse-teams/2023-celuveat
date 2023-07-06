package com.celuveat.celuveat.video.infra.persistence;

import static com.celuveat.celuveat.video.exception.VideoExceptionType.NOT_FOUND_VIDEO;

import com.celuveat.celuveat.common.annotation.Dao;
import com.celuveat.celuveat.video.domain.Video;
import com.celuveat.celuveat.video.exception.VideoException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Dao
public class VideoDao {

    private static final RowMapper<Video> mapper = new DataClassRowMapper<>(Video.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public VideoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("video")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(Video video) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(video);
        return simpleJdbcInsert.executeAndReturnKey(source)
                .longValue();
    }

    public Video getById(Long id) {
        String sql = """
                SELECT * 
                FROM video
                WHERE id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, mapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new VideoException(NOT_FOUND_VIDEO);
        }
    }
}
