package com.celuveat.common.log.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.stream.Collectors;

public class RequestBodyReader {

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
}
