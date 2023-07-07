package com.celuveat.celuveat.video.infra.persistence;

import static com.celuveat.celuveat.video.fixture.VideoFixture.toFindAllVideoByRestaurantIdResponse;
import static org.mockito.Mockito.mock;

import com.celuveat.celuveat.celeb.infra.persistence.CelebDao;
import com.celuveat.celuveat.video.application.dto.FindAllVideoByRestaurantIdResponse;
import com.celuveat.celuveat.video.domain.Video;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

public class FakeVideoQueryDao extends VideoQueryDao {

    private final CelebDao celebDao;
    private final Map<Long, Video> store = new HashMap<>();
    private long id = 1L;

    public FakeVideoQueryDao(CelebDao celebDao) {
        super(mock(JdbcTemplate.class));
        this.celebDao = celebDao;
    }

    public Long save(Video video) {
        store.put(id, video);
        return id++;
    }

    @Override
    public List<FindAllVideoByRestaurantIdResponse> findAllByRestaurantId(Long restaurantId) {
        return store.values().stream()
                .map(video -> toFindAllVideoByRestaurantIdResponse(
                        video,
                        celebDao.getById(video.celebId()))
                ).toList();
    }
}
