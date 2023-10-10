package com.celuveat.common.util;

public class FileNameUtil {

    private static final char EXTENSION_FLAG = '.';
    private static final String WEBP_EXTENSION = ".webp";

    public static String removeExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(EXTENSION_FLAG);
        if (lastDotIndex != -1) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }

    public static String attachWebpExtension(String rawFileName) {
        return rawFileName + WEBP_EXTENSION;
    }
}
