import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    // Статические счетчики для разных длин
    static AtomicInteger beautifulCountLen3 = new AtomicInteger(0);
    static AtomicInteger beautifulCountLen4 = new AtomicInteger(0);
    static AtomicInteger beautifulCountLen5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        // Генерация слов
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        // Запуск трех потоков для разных критериев "красоты"
        Thread palindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    countByLength(text);
                }
            }
        });

        Thread singleLetterThread = new Thread(() -> {
            for (String text : texts) {
                if (isSingleLetter(text)) {
                    countByLength(text);
                }
            }
        });

        Thread ascendingThread = new Thread(() -> {
            for (String text : texts) {
                if (isAscending(text)) {
                    countByLength(text);
                }
            }
        });

        palindromeThread.start();
        singleLetterThread.start();
        ascendingThread.start();

        palindromeThread.join();
        singleLetterThread.join();
        ascendingThread.join();

        System.out.println("Красивых слов с длиной 3: " + beautifulCountLen3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + beautifulCountLen4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + beautifulCountLen5.get() + " шт");
    }

    // --- Вспомогательные методы ---

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    // Проверка: слово - палиндром
    static boolean isPalindrome(String text) {
        int left = 0, right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left++) != text.charAt(right--)) return false;
        }
        return true;
    }

    // Проверка: все буквы одинаковые
    static boolean isSingleLetter(String text) {
        char c = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != c) return false;
        }
        return true;
    }

    // Проверка: буквы идут по возрастанию
    static boolean isAscending(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i-1)) return false;
        }
        return true;
    }

    // Инкремент правильного счетчика по длине слова
    static void countByLength(String text) {
        switch (text.length()) {
            case 3:
                beautifulCountLen3.incrementAndGet();
                break;
            case 4:
                beautifulCountLen4.incrementAndGet();
                break;
            case 5:
                beautifulCountLen5.incrementAndGet();
                break;
        }
    }
}