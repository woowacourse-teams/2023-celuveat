package com.celuveat.celuveat.celeb.infra.persistence;

import static com.celuveat.celuveat.celeb.exception.CelebExceptionType.NOT_FOUND_CELEB;

import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import com.celuveat.celuveat.celeb.application.dto.FindCelebByIdResponse;
import com.celuveat.celuveat.celeb.exception.CelebException;
import com.celuveat.celuveat.common.annotation.Dao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Dao
@RequiredArgsConstructor
public class CelebQueryDao {

    private final RowMapper<FindAllCelebResponse> findAllCelebResponseRowMapper =
            new DataClassRowMapper<>(FindAllCelebResponse.class);

    private final RowMapper<FindCelebByIdResponse> findCelebByIdResponseRowMapper =
            new DataClassRowMapper<>(FindCelebByIdResponse.class);

    private final JdbcTemplate jdbcTemplate;

    public FindCelebByIdResponse getById(Long id) {
        String sql = """
                SELECT 
                    id,
                    name,
                    youtube_channel_name,
                    subscriber_count,
                    youtube_channel_url,
                    profile_image_url,
                    (SELECT count(*) 
                     FROM video
                     WHERE video.celeb_id = celeb.id) AS restaurant_count
                FROM celeb
                WHERE celeb.id = ?
                """;
        try {
            return jdbcTemplate.queryForObject(sql, findCelebByIdResponseRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new CelebException(NOT_FOUND_CELEB);
        }
    }

    public List<FindAllCelebResponse> findAll() {
        String sql = """
                SELECT *
                FROM celeb
                """;
        return jdbcTemplate.query(sql, findAllCelebResponseRowMapper);
    }
}
