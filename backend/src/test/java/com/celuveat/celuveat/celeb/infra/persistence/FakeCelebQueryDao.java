package com.celuveat.celuveat.celeb.infra.persistence;

import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.celeb.fixture.CelebFixture;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeCelebQueryDao extends CelebQueryDao {

    private final Map<Long, Celeb> store = new HashMap<>();
    private long id = 1L;

    public FakeCelebQueryDao() {
        super(null);
    }

    public Long save(Celeb celeb) {
        store.put(id, celeb);
        return id++;
    }

    @Override
    public List<FindAllCelebResponse> findAll() {
        return store.values().stream()
                .map(CelebFixture::toFindAllCelebResponse)
                .toList();
    }
}
