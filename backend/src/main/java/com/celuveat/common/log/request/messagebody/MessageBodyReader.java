package com.celuveat.common.log.request.messagebody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.stream.Collectors;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class MessageBodyReader {

    @NotNull
    public static String readBody(HttpServletRequest request) {
        if (request.getContentLength() == 0) {
            return "";
        }
        try {
            return request.getReader().lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public static String readBody(HttpServletResponse response) {
        return new String(((ContentCachingResponseWrapper) response).getContentAsByteArray());
    }
}
