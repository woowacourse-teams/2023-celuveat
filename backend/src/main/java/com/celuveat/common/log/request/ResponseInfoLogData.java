package com.celuveat.common.log.request;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseInfoLogData {

    private final Map<String, Object> params = new LinkedHashMap<>();

    public ResponseInfoLogData(String id, HttpServletResponse response) {
        params.put("ID", id);
        params.put("Status", response.getStatus());
    }

    public void put(String key, Object value) {
        params.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            sb.append("\t[%s] = [%s]\n".formatted(key, params.get(key)));
        }
        return sb.toString();
    }
}
