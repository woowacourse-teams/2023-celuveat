package com.celuveat.celuveat.celeb.application;

import com.celuveat.celuveat.celeb.application.dto.RegisterCelebCommand;
import com.celuveat.celuveat.celeb.infra.persistence.CelebDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CelebService {

    private final CelebDao celebDao;

    public Long register(RegisterCelebCommand command) {
        return celebDao.save(command.toDomain());
    }
}
