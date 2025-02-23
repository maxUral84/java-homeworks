package ru.netology.service;

public class CustomsService {
    private static final int PRICE_RATE = 100;
    private static final int WEIGHT_RATE = 100;

    public static int calculateCustoms(int price, int weight) {
        int dutyFromPrice = price / PRICE_RATE;
        int dutyFromWeight = weight * WEIGHT_RATE;

        return dutyFromPrice + dutyFromWeight;
    }
}
