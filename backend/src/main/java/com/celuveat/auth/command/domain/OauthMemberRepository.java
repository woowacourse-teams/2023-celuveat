package com.celuveat.auth.command.domain;

import static com.celuveat.auth.exception.AuthExceptionType.NOT_FOUND_MEMBER;

import com.celuveat.auth.exception.AuthException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthMemberRepository extends JpaRepository<OauthMember, Long> {

    default OauthMember getById(Long id) {
        return findById(id).orElseThrow(() -> new AuthException(NOT_FOUND_MEMBER));
    }

    Optional<OauthMember> findByOauthId(OauthId oauthId);
}
