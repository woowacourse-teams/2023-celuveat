package com.celuveat.common.log.request;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class RequestInfoMap {

    private static final RequestParser requestParser = new RequestParser();
    private final Map<String, Object> params = new LinkedHashMap<>();

    public RequestInfoMap(String id, HttpServletRequest request) {
        params.put("ID", id);
        params.put("IP", request.getRemoteAddr());
        params.put("Remote Host", request.getRemoteHost());
        params.put("Headers", requestParser.parseHeaders(request));
        params.put("Method", request.getMethod());
        params.put("URL", request.getRequestURL());
        params.put("QueryString", request.getQueryString());
        params.put("Params", requestParser.parseParams(request));
        params.put("Body", requestParser.parseBody(request));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            sb.append("\t[%s] = [%s]\n".formatted(key, params.get(key)));
        }
        return sb.toString();
    }

    private static class RequestParser {

        public String parseHeaders(HttpServletRequest request) {
            Enumeration<String> parameterNames = request.getHeaderNames();
            Stream<String> parameterStream = StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(parameterNames.asIterator(), Spliterator.ORDERED), false
            );
            return parameterStream.map(header -> "\t\t[%s] = [%s]".formatted(header, request.getHeader(header)))
                    .collect(Collectors.joining("\n", "\n", "\n\t"));
        }

        public String parseParams(HttpServletRequest request) {
            Enumeration<String> parameterNames = request.getParameterNames();
            Stream<String> parameterStream = StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(parameterNames.asIterator(), Spliterator.ORDERED), false
            );
            return parameterStream.map(param -> "\t\t[%s] = [%s]".formatted(param, request.getParameter(param)))
                    .collect(Collectors.joining("\n", "\n", "\n\t"));
        }

        public String parseBody(HttpServletRequest request) {
            String body = RequestBodyReader.readBody(request);
            return switch (request.getContentType()) {
                case TEXT_PLAIN_VALUE -> "\n" + body;
                case APPLICATION_JSON_VALUE -> Arrays.stream(body.split("\\n"))
                        .collect(Collectors.joining("\n\t\t", "\n\t\t", "\n\t"));
                case APPLICATION_FORM_URLENCODED_VALUE, MULTIPART_FORM_DATA_VALUE -> Arrays.stream(body.split("&"))
                        .map(it -> {
                            String[] split = it.split("=");
                            String value = null;
                            if (split.length >= 2) {
                                value = it.substring(it.indexOf("=") + 1);
                            }
                            return "[%s] = [%s]".formatted(split[0], value);
                        })
                        .collect(Collectors.joining("\n\t\t", "\n\t\t", "\n\t"));
                default -> body;
            };
        }
    }
}
