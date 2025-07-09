import java.util.function.*;

public class Calculator {
    // Статический Supplier для создания экземпляра Calculator
    public static Supplier<Calculator> instance = Calculator::new;

    public BinaryOperator<Integer> plus = (x, y) -> x + y;
    public BinaryOperator<Integer> minus = (x, y) -> x - y;
    public BinaryOperator<Integer> multiply = (x, y) -> x * y;

    // Бинарные операторы для основных арифметических операций
    public BinaryOperator<Integer> divide = (x, y) -> {
        if (y == 0) {
            throw new ArithmeticException("Деление на ноль");
        }

        return x / y;
    };

    // Унарные операторы
    public UnaryOperator<Integer> pow = x -> x * x;
    public UnaryOperator<Integer> abs = x -> x > 0 ? x : x * -1;

    // Предикат для проверки положительного числа
    public Predicate<Integer> isPositive = x -> x > 0;

    // Consumer для вывода в консоль
    public Consumer<Integer> println = System.out::println;
    public Consumer<String> printError = System.out::println;
}
