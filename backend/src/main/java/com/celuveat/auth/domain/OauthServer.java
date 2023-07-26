package com.celuveat.auth.domain;

import static com.celuveat.auth.exception.AuthExceptionType.UNSUPPORTED_OAUTH_TYPE;
import static java.util.Arrays.stream;
import static java.util.Locale.ENGLISH;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.celuveat.auth.exception.AuthException;
import java.util.Map;
import java.util.Optional;

public enum OauthServer {

    KAKAO,
    NAVER,
    ;

    private static final Map<String, OauthServer> typeMap;

    static {
        typeMap = stream(values())
                .collect(toMap(
                        it -> it.name().toLowerCase(ENGLISH),
                        identity()
                ));
    }

    public static OauthServer fromName(String type) {
        return Optional.ofNullable(typeMap.get(type.toLowerCase(ENGLISH)))
                .orElseThrow(() -> new AuthException(UNSUPPORTED_OAUTH_TYPE));
    }
}
