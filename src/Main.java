import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        int threadsCount = 1000;
        List<Thread> threads = new ArrayList<>();

        Thread freqLogger = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    synchronized (sizeToFreq) {
                        sizeToFreq.wait(); // Ждём сигнала
                        // Поиск лидера
                        int mostFreq = 0, maxCount = 0;
                        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
                            if (entry.getValue() > maxCount) {
                                mostFreq = entry.getKey();
                                maxCount = entry.getValue();
                            }
                        }
                        System.out.println("Текущий лидер: " + mostFreq + " (встретилось " + maxCount + " раз)");
                    }
                }
            } catch (InterruptedException ignored) {
                // Завершение по interrupt()
            }
        });

        freqLogger.start();

        for (int i = 0; i < threadsCount; i++) {
            Thread thread = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int rCount = 0;
                for (char c : route.toCharArray()) {
                    if (c == 'R') rCount++;
                }
                synchronized (sizeToFreq) {
                    sizeToFreq.put(rCount, sizeToFreq.getOrDefault(rCount, 0) + 1);
                    sizeToFreq.notify(); // Сигнал печатающему потоку
                }
            });
            threads.add(thread);
            thread.start();
        }

        // Ждём завершения всех рабочих потоков
        for (Thread thread : threads) {
            thread.join();
        }

        // Останавливаем печатающий поток
        freqLogger.interrupt();
        synchronized (sizeToFreq) {
            sizeToFreq.notify(); // Избавляем от возможного зависания в wait
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}