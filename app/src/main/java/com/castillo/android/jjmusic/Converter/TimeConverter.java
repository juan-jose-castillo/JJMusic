package com.castillo.android.jjmusic.Converter;

/**
 * Created by juanjosecastillo on 29/9/17.
 */

public class TimeConverter {
    private static final String SEPARATOR = ":";
    private static final String DEFAULT_VALUE = "0:0";

    public static String getDuration(long durationInSeconds) {
        if (durationInSeconds <= 0) {
            return DEFAULT_VALUE;
        }
        long minutes = durationInSeconds / 60;
        long seconds = durationInSeconds % 60;
        return Long.toString(minutes) + SEPARATOR + Long.toString(seconds);
    }
}

