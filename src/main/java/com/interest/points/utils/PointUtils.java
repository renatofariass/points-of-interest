package com.interest.points.utils;

import com.interest.points.model.Poi;

import java.time.LocalTime;

public class PoiUtils {

    public static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static boolean isOpenAt(LocalTime now, String openingHours, String closingHours) {
        if (openingHours.equals("ALL DAY")) {
            return true;
        }

        LocalTime openingTime = LocalTime.parse(openingHours);
        LocalTime closingTime = LocalTime.parse(closingHours);

        if (closingTime.isBefore(openingTime)) {
            return !now.isBefore(openingTime) || !now.isAfter(closingTime.plusHours(24));
        }

        return !now.isBefore(openingTime) && !now.isAfter(closingTime);
    }
}
