package com.celuveat.celuveat.celeb.infra.persistence;

import static com.celuveat.celuveat.celeb.exception.CelebExceptionType.NOT_FOUND_CELEB;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.toFindCelebByIdResponse;

import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import com.celuveat.celuveat.celeb.application.dto.FindCelebByIdResponse;
import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.celeb.exception.CelebException;
import com.celuveat.celuveat.celeb.fixture.CelebFixture;
import com.celuveat.celuveat.video.infra.persistence.FakeVideoDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeCelebQueryDao extends CelebQueryDao {

    private final FakeVideoDao videoDao;
    private final Map<Long, Celeb> store = new HashMap<>();
    private long id = 1L;

    public FakeCelebQueryDao(FakeVideoDao videoDao) {
        super(null);
        this.videoDao = videoDao;
    }

    public Long save(Celeb celeb) {
        store.put(id, celeb);
        return id++;
    }

    @Override
    public FindCelebByIdResponse getById(Long id) {
        return Optional.ofNullable(store.get(id))
                .map(it -> toFindCelebByIdResponse(it, videoDao.countByCelebId(id)))
                .orElseThrow(() -> new CelebException(NOT_FOUND_CELEB));
    }

    @Override
    public List<FindAllCelebResponse> findAll() {
        return store.values().stream()
                .map(CelebFixture::toFindAllCelebResponse)
                .toList();
    }
}
