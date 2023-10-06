package com.celuveat.celeb.query.dao;

import com.celuveat.celeb.query.dao.support.CelebQueryDaoSupport;
import com.celuveat.celeb.query.dto.FindAllCelebResponse;
import com.celuveat.common.dao.Dao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Dao
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindAllCelebResponseDao {

    private final CelebQueryDaoSupport celebQueryDaoSupport;

    public List<FindAllCelebResponse> findAll() {
        return celebQueryDaoSupport.findAll()
                .stream()
                .map(FindAllCelebResponse::from)
                .toList();
    }
}
