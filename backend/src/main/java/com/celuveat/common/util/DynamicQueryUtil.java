package com.celuveat.common.util;

import java.util.List;

public class DynamicQueryUtil {

    public static void appendQueryIfTrue(
            List<String> appendedQuery,
            boolean condition,
            String format,
            Object... param
    ) {
        if (condition) {
            appendedQuery.add(format.formatted(param));
        }
    }

    public static boolean notNull(Object t) {
        return t != null;
    }
}
