package com.celuveat.celuveat.celeb.application;

import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import com.celuveat.celuveat.celeb.application.dto.FindCelebByIdResponse;
import com.celuveat.celuveat.celeb.infra.persistence.CelebQueryDao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CelebQueryService {

    private final CelebQueryDao celebQueryDao;

    public FindCelebByIdResponse findById(Long id) {
        return celebQueryDao.getById(id);
    }

    public List<FindAllCelebResponse> findAll() {
        return celebQueryDao.findAll();
    }
}
