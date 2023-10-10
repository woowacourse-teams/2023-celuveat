package com.celuveat.celeb.query.dao.support;

import com.celuveat.celeb.command.domain.Celeb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebQueryDaoSupport extends JpaRepository<Celeb, Long> {
}
