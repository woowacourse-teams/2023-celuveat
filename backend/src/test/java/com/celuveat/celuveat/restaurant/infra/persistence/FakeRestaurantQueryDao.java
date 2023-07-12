package com.celuveat.celuveat.restaurant.infra.persistence;

import static org.mockito.Mockito.mock;

import com.celuveat.celuveat.common.page.PageCond;
import com.celuveat.celuveat.common.page.SliceResponse;
import com.celuveat.celuveat.restaurant.application.dto.RestaurantSearchResponse;
import com.celuveat.celuveat.restaurant.domain.Restaurant;
import com.celuveat.celuveat.restaurant.fixture.RestaurantFixture;
import com.celuveat.celuveat.video.domain.Video;
import com.celuveat.celuveat.video.infra.persistence.FakeVideoDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;

public class FakeRestaurantQueryDao extends RestaurantQueryDao {

    private final FakeVideoDao videoDao;
    private final Map<Long, Restaurant> store = new HashMap<>();
    private long id = 1L;

    public FakeRestaurantQueryDao(FakeVideoDao videoDao) {
        super(mock(JdbcTemplate.class));
        this.videoDao = videoDao;
    }

    public Long save(Restaurant restaurant) {
        store.put(id, restaurant);
        return id++;
    }

    @Override
    public SliceResponse<RestaurantSearchResponse> findAllByCelebIdUploadDateDesc(Long celebId, PageCond cond) {
        List<Video> videos = videoDao.findAllByCelebId(celebId);
        List<Long> restaurantIds = getAllRestaurantIdByCelebId(celebId, videos);
        List<RestaurantSearchResponse> list = getAllRestaurantByIdIn(restaurantIds);
        List<RestaurantSearchResponse> paging = slicing(cond, list);
        return new SliceResponse<>(
                hasNextPage(cond, paging),
                pagedList(cond, paging)
        );
    }

    private List<Long> getAllRestaurantIdByCelebId(Long celebId, List<Video> videos) {
        return videos.stream()
                .filter(it -> it.celebId().equals(celebId))
                .map(Video::restaurantId)
                .toList();
    }

    private List<RestaurantSearchResponse> getAllRestaurantByIdIn(List<Long> restaurantIds) {
        return store.keySet().stream()
                .filter(restaurantIds::contains)
                .map(store::get)
                .map(RestaurantFixture::toRestaurantSearchResponse)
                .collect(Collectors.toList());
    }

    private List<RestaurantSearchResponse> slicing(PageCond cond, List<RestaurantSearchResponse> list) {
        return list.subList(cond.offset(), Math.min(cond.offset() + cond.limit() + 1, list.size()));
    }

    private boolean hasNextPage(PageCond cond, List<RestaurantSearchResponse> response) {
        return response.size() == cond.limit() + 1;
    }

    private List<RestaurantSearchResponse> pagedList(PageCond cond, List<RestaurantSearchResponse> paging) {
        return paging.subList(0, Math.min(cond.size(), paging.size()));
    }
}
