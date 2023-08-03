package com.celuveat.common.query;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DynamicQueryAssembler {

    private static final String WHERE = "WHERE ";
    private static final String AND = " AND ";

    public static String assemble(DynamicQuery... dynamicQueries) {
        String whereQuery = Arrays.stream(dynamicQueries)
                .filter(DynamicQuery::condition)
                .map(DynamicQuery::createQuery)
                .collect(Collectors.joining(AND));
        if (whereQuery.isBlank()) {
            return "";
        }
        return WHERE + whereQuery;
    }
}
