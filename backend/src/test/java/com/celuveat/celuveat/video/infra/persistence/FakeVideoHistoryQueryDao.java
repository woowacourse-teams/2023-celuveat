package com.celuveat.celuveat.video.infra.persistence;

import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.toFindAllVideoHistoryResponse;
import static org.mockito.Mockito.mock;

import com.celuveat.celuveat.video.application.dto.FindAllVideoHistoryResponse;
import com.celuveat.celuveat.video.domain.VideoHistory;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FakeVideoHistoryQueryDao extends VideoHistoryQueryDao {

    private final Map<Long, VideoHistory> store = new LinkedHashMap<>();
    private long id = 1L;

    public FakeVideoHistoryQueryDao() {
        super(mock(JdbcTemplate.class));
    }

    public Long save(VideoHistory videoHistory) {
        store.put(id, videoHistory);
        return id++;
    }

    @Override
    public List<FindAllVideoHistoryResponse> findAllVideoHistory() {
        return store.values().stream()
                .map(videoHistory -> toFindAllVideoHistoryResponse(videoHistory.id(), videoHistory.celebId()))
                .collect(Collectors.toList());
    }
}
