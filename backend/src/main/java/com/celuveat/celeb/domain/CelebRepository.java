package com.celuveat.celeb.domain;

import static com.celuveat.admin.exception.AdminExceptionType.NOT_EXISTS_CELEB;

import com.celuveat.admin.exception.AdminException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebRepository extends JpaRepository<Celeb, Long> {

    Optional<Celeb> findByYoutubeChannelName(String youtubeChannelName);

    default Celeb getByYoutubeChannelName(String youtubeChannelName) {
        return findByYoutubeChannelName(youtubeChannelName)
                .orElseThrow(() -> new AdminException(NOT_EXISTS_CELEB));
    }
}
