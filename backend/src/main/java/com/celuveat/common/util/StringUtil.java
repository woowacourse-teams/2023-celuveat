package com.celuveat.common.util;

import java.util.regex.Pattern;

public class StringUtil {

    private static final Pattern BLANK_PATTERN = Pattern.compile("\\s+");

    public static String removeAllBlank(String input) {
        if (input == null) {
            return null;
        }
        return BLANK_PATTERN.matcher(input.strip()).replaceAll("");
    }
}
