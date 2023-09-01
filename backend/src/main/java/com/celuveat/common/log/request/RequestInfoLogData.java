package com.celuveat.common.log.request;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class RequestInfoLogData {

    private final Map<String, Object> params = new LinkedHashMap<>();

    public RequestInfoLogData(String id, HttpServletRequest request) {
        params.put("ID", id);
        params.put("IP", request.getRemoteAddr());
        params.put("URI", request.getRequestURI());
        params.put("Method", request.getMethod());
        params.put("QueryString", request.getQueryString());
        params.put("Params", parseParams(request));
    }

    public void put(String key, Object value) {
        params.put(key, value);
    }

    public String parseParams(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Stream<String> parameterStream = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(parameterNames.asIterator(), Spliterator.ORDERED), false
        );
        return parameterStream.map(param -> "%s = %s".formatted(param, request.getParameter(param)))
                .collect(Collectors.joining(", ", "[","]"));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            sb.append("%s = %s, ".formatted(key, params.get(key)));
        }
        return sb.toString();
    }
}
