package com.celuveat.celuveat.video.infra.persistence;

import com.celuveat.celuveat.common.annotation.Dao;
import com.celuveat.celuveat.video.application.dto.FindAllVideoHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.List;

@Dao
@RequiredArgsConstructor
public class VideoHistoryQueryDao {

    private static final RowMapper<FindAllVideoHistoryResponse> findAllVideoHistoryResponseRowMapper
            = new DataClassRowMapper<>(FindAllVideoHistoryResponse.class);

    private final JdbcTemplate jdbcTemplate;

    public List<FindAllVideoHistoryResponse> findAllVideoHistory() {
        String sql = """
                SELECT *
                FROM video_history
                """;
        return jdbcTemplate.query(sql, findAllVideoHistoryResponseRowMapper);
    }
}
