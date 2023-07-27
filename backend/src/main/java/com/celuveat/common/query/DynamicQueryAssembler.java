package com.celuveat.common.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DynamicQueryAssembler {

    private static final String WHERE = "WHERE ";
    private static final String AND = " AND ";

    private final List<DynamicQuery> dynamicQueries = new ArrayList<>();

    public DynamicQueryAssembler(DynamicQuery... dynamicQueries) {
        this.dynamicQueries.addAll(Arrays.asList(dynamicQueries));
    }

    public String assemble() {
        String whereQuery = dynamicQueries.stream()
                .filter(DynamicQuery::condition)
                .map(DynamicQuery::createQuery)
                .collect(Collectors.joining(AND));
        if (whereQuery.isBlank()) {
            return "";
        }
        return WHERE + whereQuery;
    }
}
