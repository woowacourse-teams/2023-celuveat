package com.celuveat.celuveat.restaurant.infra.persistence;

import com.celuveat.celuveat.common.annotation.Dao;
import com.celuveat.celuveat.common.page.PageCond;
import com.celuveat.celuveat.common.page.PageResponse;
import com.celuveat.celuveat.restaurant.application.dto.RestaurantSearchResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Dao
@RequiredArgsConstructor
public class RestaurantQueryDao {

    private static final RowMapper<RestaurantSearchResponse> mapper =
            new DataClassRowMapper<>(RestaurantSearchResponse.class);

    private final JdbcTemplate jdbcTemplate;

    public PageResponse<RestaurantSearchResponse> findAllByCelebId(Long celebId, PageCond cond) {
        String sql = """
                SELECT 
                    r.id,
                    r.image_url,
                    r.name,
                    r.category,
                    r.road_address,
                    r.address_lot_number,
                    r.zip_code,
                    r.latitude,
                    r.longitude,
                    r.phone_number,
                    video.ads AS is_ads,
                FROM restaurant AS r
                JOIN video ON video.restaurant_id = r.id
                WHERE video.celeb_id = ?
                ORDER BY video.upload_date DESC
                LIMIT ?
                OFFSET ?
                """;
        List<RestaurantSearchResponse> response = jdbcTemplate.query(sql, mapper,
                celebId,
                cond.limit() + 1,
                cond.offset());
        return new PageResponse<>(
                hasNextPage(cond, response),
                pagedList(cond, response)
        );
    }

    private boolean hasNextPage(PageCond cond, List<RestaurantSearchResponse> response) {
        return response.size() == cond.limit() + 1;
    }

    private List<RestaurantSearchResponse> pagedList(
            PageCond cond,
            List<RestaurantSearchResponse> response
    ) {
        return response.subList(0, Math.min(cond.size(), response.size()));
    }
}
