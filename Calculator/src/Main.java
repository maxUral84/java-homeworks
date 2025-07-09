public class Main {
    public static void main(String[] args) {
        Calculator calc = Calculator.instance.get();

        try {
            int a = calc.plus.apply(1, 2);
            int b = calc.minus.apply(1, 1);
            int c = calc.divide.apply(a, b);

            calc.println.accept(c);
        } catch (ArithmeticException e) {
            calc.printError.accept("Ошибка: " + e.getMessage());
        }

        // Другие операции
        calc.println.accept(calc.pow.apply(5)); // 25
        calc.println.accept(calc.abs.apply(-10)); // 10
        calc.println.accept(calc.abs.apply(10)); // 10
        calc.println.accept(calc.isPositive.test(-5) ? 1 : 0); // 0
    }
}
