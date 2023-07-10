package com.celuveat.celuveat.video.infra.persistence;

import com.celuveat.celuveat.common.annotation.Dao;
import com.celuveat.celuveat.video.application.dto.FindAllVideoByRestaurantIdResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Dao
@RequiredArgsConstructor
public class VideoQueryDao {

    private static final RowMapper<FindAllVideoByRestaurantIdResponse> findAllVideoByRestaurantIdResponseRowMapper =
            new DataClassRowMapper<>(FindAllVideoByRestaurantIdResponse.class);

    private final JdbcTemplate jdbcTemplate;

    public List<FindAllVideoByRestaurantIdResponse> findAllByRestaurantId(Long restaurantId) {
        String sql = """
                SELECT 
                    v.id, 
                    v.title, 
                    v.view_count, 
                    v.url as video_url, 
                    v.upload_date as published_date, 
                    c.profile_image_url, 
                    c.id as celeb_id, 
                    c.name as celeb_name
                FROM video as v
                JOIN celeb as c
                ON c.id = v.celeb_id
                WHERE v.restaurant_id = ?;
                """;
        return jdbcTemplate.query(sql, findAllVideoByRestaurantIdResponseRowMapper, restaurantId);
    }
}
