package com.xh.microservice.common.utils;


public final class ConvertUtils {

    private static final String NULL_STR = "null";

    public static int toInt(String val, int defaultValue) {
        if (NULL_STR.equalsIgnoreCase(val)) {
            return defaultValue;
        }
        if (StringUtils.isBlank(val)) {
            return defaultValue;
        }
        return Integer.parseInt(val);
    }

    public static long toLong(String val, long defaultValue) {
        if (StringUtils.isBlank(val)) {
            return defaultValue;
        }
        return Long.parseLong(val);
    }

    public static boolean toBool(String val, boolean defaultValue) {
        if (StringUtils.isBlank(val)) {
            return defaultValue;
        }
        return Boolean.parseBoolean(val);
    }

}
