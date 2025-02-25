package ru.netology.app;

import ru.netology.utils.YearUtils;
import java.util.Scanner;

public class App {
    private static final int MIN_YEAR = 1000;
    private static final int MAX_YEAR = 9999;

    public static void Run() {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        int year;
        int days;
        int userDays;
        boolean isRunning = true;

        System.out.println("Программа, которая рассчитывает количество дней в году");

        while (isRunning) {
            System.out.print("Введите год в формате yyyy: ");
            year = scanner.nextInt();

            if (year < MIN_YEAR || year > MAX_YEAR) {
                System.out.println("Ошибка! Неверно указан год.");
                continue;
            }

            System.out.print("Введите количество дней в этом году: ");
            userDays = scanner.nextInt();

            if (userDays < 0) {
                System.out.println("Ошибка! Количество дней не может быть отрицательным.");
                continue;
            }

            days = YearUtils.getDaysInYear(year);

            if (userDays == days) {
                System.out.println("Верно :)");
                score++;
            } else {
                System.out.println("Неверно! В этом году " + days + " дней!");
                isRunning = false;
            }
        }

        System.out.println("Набрано очков: " + score);

        System.out.println("Программа завершена");
    }
}
