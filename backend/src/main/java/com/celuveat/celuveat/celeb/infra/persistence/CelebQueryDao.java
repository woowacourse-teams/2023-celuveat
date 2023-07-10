package com.celuveat.celuveat.celeb.infra.persistence;

import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import com.celuveat.celuveat.common.annotation.Dao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Dao
@RequiredArgsConstructor
public class CelebQueryDao {

    private final RowMapper<FindAllCelebResponse> findAllCelebResponseRowMapper =
            new DataClassRowMapper<>(FindAllCelebResponse.class);

    private final JdbcTemplate jdbcTemplate;

    public List<FindAllCelebResponse> findAll() {
        String sql = """
                SELECT 
                  c.id as id,
                  c.name as name,
                  c.youtube_channel_name as youtubeId,
                  c.subscriber_count as subscriberCount,
                  c.profile_image_url as profileImageUrl
                FROM celeb as c
                """;
        return jdbcTemplate.query(sql, findAllCelebResponseRowMapper);
    }
}
