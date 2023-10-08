package com.celuveat.celeb.query;

import com.celuveat.celeb.query.dao.FindAllCelebResponseDao;
import com.celuveat.celeb.query.dto.FindAllCelebResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CelebQueryService {

    private final FindAllCelebResponseDao findAllCelebResponseDao;

    public List<FindAllCelebResponse> findAll() {
        return findAllCelebResponseDao.findAll();
    }
}
