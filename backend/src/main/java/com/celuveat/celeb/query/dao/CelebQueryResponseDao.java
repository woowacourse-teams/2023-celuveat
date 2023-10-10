package com.celuveat.celeb.query.dao;

import com.celuveat.celeb.query.dao.support.CelebQueryDaoSupport;
import com.celuveat.celeb.query.dto.CelebQueryResponse;
import com.celuveat.common.dao.Dao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Dao
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CelebQueryResponseDao {

    private final CelebQueryDaoSupport celebQueryDaoSupport;

    public List<CelebQueryResponse> find() {
        return celebQueryDaoSupport.findAll()
                .stream()
                .map(CelebQueryResponse::from)
                .toList();
    }
}
