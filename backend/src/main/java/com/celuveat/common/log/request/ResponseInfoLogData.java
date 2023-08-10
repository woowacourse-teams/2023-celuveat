package com.celuveat.common.log.request;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseInfoLogData {

    private final Map<String, Object> params = new LinkedHashMap<>();

    public ResponseInfoLogData(String id, HttpServletResponse response) {
        params.put("ID", id);
        params.put("Status", response.getStatus());
        params.put("Headers", parseHeaders(response));
        params.put("Body", parseBody(response));
    }

    public String parseHeaders(HttpServletResponse response) {
        Collection<String> headerNames = response.getHeaderNames();
        return headerNames.stream().map(header -> "\t\t[%s] = [%s]".formatted(header, response.getHeaders(header)))
                .distinct()
                .collect(Collectors.joining("\n", "\n", "\n\t"));
    }

    public String parseBody(HttpServletResponse response) {
        String body = RequestBodyReader.readBody(response);
        if (!APPLICATION_JSON_VALUE.equals(response.getContentType())) {
            return body;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            Object jsonObject = mapper.readValue(body, Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            return Arrays.stream(prettyJson.split("\\n"))
                    .collect(Collectors.joining("\n\t\t", "\n\t\t", "\n\t"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
