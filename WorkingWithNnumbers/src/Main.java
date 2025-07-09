import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(1, 2, 5, 16, -1, -2, 0, 32, 3, 5, 8, 23, 4);

        // Фильтрация положительных чисел
        List<Integer> positiveNumbers = new ArrayList<>();
        for (Integer num : intList) {
            if (num > 0) {
                positiveNumbers.add(num);
            }
        }

        // Фильтрация четных чисел
        List<Integer> evenNumbers = new ArrayList<>();
        for (Integer num : positiveNumbers) {
            if (num % 2 == 0) {
                evenNumbers.add(num);
            }
        }

        // Сортировка по возрастанию
        Collections.sort(evenNumbers);

        // Вывод результата
        for (Integer num : evenNumbers) {
            System.out.print(num + " ");
        }
    }
}