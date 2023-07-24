package com.celuveat.common.util;

import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;

public class Base64Util {

    public static String encode(String input) {
        return Base64.encodeBase64URLSafeString(input.getBytes(StandardCharsets.UTF_8)).intern();
    }
}
