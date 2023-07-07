package com.celuveat.celuveat.celeb.infra.persistence;

import static com.celuveat.celuveat.celeb.exception.CelebExceptionType.NOT_FOUND_CELEB;
import static org.mockito.Mockito.mock;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.celeb.exception.CelebException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;

public class FakeCelebDao extends CelebDao {

    private final Map<Long, Celeb> store = new HashMap<>();
    private long id = 1L;

    public FakeCelebDao() {
        super(mock(JdbcTemplate.class));
    }

    @Override
    public Long save(Celeb celeb) {
        store.put(id, celeb);
        return id++;
    }

    @Override
    public Celeb getById(Long id) {
        return Optional.ofNullable(store.get(id))
                .orElseThrow(() -> new CelebException(NOT_FOUND_CELEB));
    }
}
