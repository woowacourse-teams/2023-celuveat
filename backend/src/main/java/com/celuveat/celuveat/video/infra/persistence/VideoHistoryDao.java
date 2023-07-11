package com.celuveat.celuveat.video.infra.persistence;

import static com.celuveat.celuveat.video.exception.VideoHistoryExceptionType.NOT_FOUND_VIDEO_HISTORY;

import com.celuveat.celuveat.common.annotation.Dao;
import com.celuveat.celuveat.video.domain.VideoHistory;
import com.celuveat.celuveat.video.exception.VideoHistoryException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@Dao
public class VideoHistoryDao {

    private static final RowMapper<VideoHistory> mapper = new DataClassRowMapper<>(VideoHistory.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public VideoHistoryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("video_history")
                .usingGeneratedKeyColumns("id");
    }

    public Long save(VideoHistory videoHistory) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(videoHistory);
        return simpleJdbcInsert.executeAndReturnKey(source)
                .longValue();
    }

    public VideoHistory getById(Long id) {
        String sql = """
                SELECT * 
                FROM video_history
                WHERE id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, mapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new VideoHistoryException(NOT_FOUND_VIDEO_HISTORY);
        }
    }
}
