package com.celuveat.common.log.request;

import static java.util.Spliterator.ORDERED;
import static java.util.stream.Collectors.joining;
import static java.util.stream.StreamSupport.stream;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;
import java.util.stream.Stream;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

public class RequestInfoLogData {

    private final Map<String, Object> params = new LinkedHashMap<>();

    public RequestInfoLogData(String id, HttpServletRequest request) {
        params.put("ID", id);
        params.put("IP", request.getRemoteAddr());
        params.put("URI", request.getRequestURI());
        params.put("Method", request.getMethod());
        params.put("QueryString", request.getQueryString());
        if (request instanceof StandardMultipartHttpServletRequest multipartRequest) {
            params.put("Multipart Params", parseMultipartParams(multipartRequest));
        } else {
            params.put("Params", parseParams(request));
        }
    }

    public void put(String key, Object value) {
        params.put(key, value);
    }

    private String parseMultipartParams(StandardMultipartHttpServletRequest request) {
        MultiValueMap<String, MultipartFile> multipartFiles = request.getMultiFileMap();
        StringBuilder sb = new StringBuilder();
        sb.append("MultiParts: [");
        for (String fileName : multipartFiles.keySet()) {
            List<MultipartFile> files = multipartFiles.get(fileName);
            String collect = files.stream()
                    .map(MultipartFile::getOriginalFilename)
                    .collect(joining(", "));
            sb.append(",\t").append("(파라미터 이름: %s, 개수: %d, 파일명들: %s)".formatted(fileName, files.size(), collect));
        }
        sb.append("]");
        String multipartParams = sb.toString().replaceFirst(",\\t", "");
        Enumeration<String> parameterNames = request.getParameterNames();
        Stream<String> parameterStream = stream(
                Spliterators.spliteratorUnknownSize(parameterNames.asIterator(), ORDERED), false
        );
        return parameterStream.map(param -> "%s = %s".formatted(param, request.getParameter(param)))
                .collect(joining(", ",
                        "[",
                        ", " + multipartParams + "]"));
    }

    public String parseParams(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        Stream<String> parameterStream = stream(
                Spliterators.spliteratorUnknownSize(parameterNames.asIterator(), ORDERED), false
        );
        return parameterStream.map(param -> "%s = %s".formatted(param, request.getParameter(param)))
                .collect(joining(", ", "[", "]"));
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
