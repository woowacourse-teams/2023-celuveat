package com.celuveat.common.util;

import java.util.regex.Pattern;

public class StringUtil {

    private static final Pattern REPLACE_ALL_BLANK_PATTERN = Pattern.compile("\\s+");

    public static String replaceAllBlank(String input) {
        if (input == null) {
            return null;
        }
        return REPLACE_ALL_BLANK_PATTERN.matcher(input.strip()).replaceAll("");
    }
}
