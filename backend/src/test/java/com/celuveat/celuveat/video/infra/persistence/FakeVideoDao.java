package com.celuveat.celuveat.video.infra.persistence;

import static com.celuveat.celuveat.video.exception.VideoExceptionType.NOT_FOUND_VIDEO;
import static org.mockito.Mockito.mock;

import com.celuveat.celuveat.video.domain.Video;
import com.celuveat.celuveat.video.exception.VideoException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;

public class FakeVideoDao extends VideoDao {

    private final Map<Long, Video> store = new HashMap<>();
    private long id = 1L;

    public FakeVideoDao() {
        super(mock(JdbcTemplate.class));
    }

    @Override
    public Long save(Video video) {
        store.put(id, video);
        return id++;
    }

    @Override
    public Video getById(Long id) {
        return Optional.ofNullable(store.get(id))
                .orElseThrow(() -> new VideoException(NOT_FOUND_VIDEO));
    }

    public int countByCelebId(Long id) {
        return (int) store.values()
                .stream()
                .filter(it -> it.celebId().equals(id))
                .count();
    }

    public List<Video> findAllByCelebId(Long celebId) {
        return store.values().stream()
                .filter(it -> it.celebId().equals(celebId))
                .toList();
    }
}
