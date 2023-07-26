package com.celuveat.auth.presentation;

import com.celuveat.auth.domain.OauthServer;
import org.springframework.core.convert.converter.Converter;

public class OauthServerConverter implements Converter<String, OauthServer> {

    @Override
    public OauthServer convert(String source) {
        return OauthServer.fromName(source);
    }
}
