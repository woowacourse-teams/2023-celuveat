package com.celuveat.celeb.command.domain;

import static com.celuveat.celeb.exception.CelebExceptionType.NOT_FOUND_CELEB;

import com.celuveat.celeb.exception.CelebException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebRepository extends JpaRepository<Celeb, Long> {

    Optional<Celeb> findByYoutubeChannelName(String youtubeChannelName);

    default Celeb getByYoutubeChannelName(String youtubeChannelName) {
        return findByYoutubeChannelName(youtubeChannelName)
                .orElseThrow(() -> new CelebException(NOT_FOUND_CELEB));
    }
}
