package com.celuveat.common.query;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class DynamicQueryCondition {

    public static boolean notNull(Object... o) {
        return Arrays.stream(o)
                .allMatch(Objects::nonNull);
    }

    public static boolean notNullRecursive(Object o) {
        return Arrays.stream(o.getClass().getDeclaredFields())
                .map(it -> getFiledValue(o, it))
                .allMatch(Objects::nonNull);
    }

    private static Object getFiledValue(Object o, Field field) {
        try {
            field.setAccessible(true);
            return field.get(o);
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}

