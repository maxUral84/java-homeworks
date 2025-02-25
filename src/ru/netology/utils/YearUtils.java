package ru.netology.utils;

public class YearUtils {
    private static final int LEAP_YEAR_DAYS = 366;
    private static final int COMMON_YEAR_DAYS = 365;
    private static final int DIVISIBLE_BY_4 = 4;
    private static final int DIVISIBLE_BY_100 = 100;
    private static final int DIVISIBLE_BY_400 = 400;

    public static int getDaysInYear(int year) {
        return ((year % DIVISIBLE_BY_400 == 0 || (year % DIVISIBLE_BY_4 == 0 && year % DIVISIBLE_BY_100 != 0))
                ? LEAP_YEAR_DAYS : COMMON_YEAR_DAYS);
    }
}
