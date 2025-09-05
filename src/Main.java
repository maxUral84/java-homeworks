import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    // --- Константы для настройки ---
    public static final int QUEUE_CAPACITY = 100;
    public static final int NUM_TEXTS = 10_000;
    public static final int TEXT_LENGTH = 100_000;
    public static final String LETTERS = "abc";

    // --- Статические блокирующие очереди для каждого анализатора ---
    public static BlockingQueue<String> queueA = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    public static BlockingQueue<String> queueB = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    public static BlockingQueue<String> queueC = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

    public static void main(String[] args) throws InterruptedException {
        // --- Поток, который генерирует тексты и наполняет очереди ---
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < NUM_TEXTS; i++) {
                    String text = generateText(LETTERS, TEXT_LENGTH);
                    // Кладем сгенерированный текст в каждую очередь
                    queueA.put(text);
                    queueB.put(text);
                    queueC.put(text);
                }
            } catch (InterruptedException e) {
                System.err.println("Поток-генератор был прерван.");
                Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
            }
        });

        // --- Поток для анализа символа 'a' ---
        Thread analyzerA = createAnalyzerThread('a', queueA);

        // --- Поток для анализа символа 'b' ---
        Thread analyzerB = createAnalyzerThread('b', queueB);

        // --- Поток для анализа символа 'c' ---
        Thread analyzerC = createAnalyzerThread('c', queueC);

        System.out.println("Запускаем анализ...");
        long startTs = System.currentTimeMillis(); // Засекаем время начала

        // Запускаем все потоки
        producer.start();
        analyzerA.start();
        analyzerB.start();
        analyzerC.start();

        // Ждем завершения всех потоков
        producer.join();
        analyzerA.join();
        analyzerB.join();
        analyzerC.join();

        long endTs = System.currentTimeMillis(); // Засекаем время окончания
        System.out.println("Анализ завершен.");
        System.out.println("Время выполнения: " + (endTs - startTs) + " мс.");
    }

    /**
     * Создает поток-анализатор для указанного символа и очереди.
     * @param symbol Символ для поиска.
     * @param queue Очередь с текстами для анализа.
     * @return Новый, еще не запущенный поток.
     */
    public static Thread createAnalyzerThread(char symbol, BlockingQueue<String> queue) {
        return new Thread(() -> {
            int maxCount = 0;
            // Анализатор знает, сколько текстов ему нужно обработать
            for (int i = 0; i < NUM_TEXTS; i++) {
                try {
                    // take() - блокирующий метод, ждет появления элемента в очереди
                    String text = queue.take();
                    int currentCount = 0;
                    for (char c : text.toCharArray()) {
                        if (c == symbol) {
                            currentCount++;
                        }
                    }
                    if (currentCount > maxCount) {
                        maxCount = currentCount;
                    }
                } catch (InterruptedException e) {
                    System.err.println("Поток-анализатор для '" + symbol + "' был прерван.");
                    Thread.currentThread().interrupt();
                    return; // Завершаем поток при прерывании
                }
            }
            System.out.printf("Максимальное количество символов '%c' в одном тексте: %d\n", symbol, maxCount);
        });
    }

    /**
     * Генератор текстов (не изменялся согласно условию).
     */
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
