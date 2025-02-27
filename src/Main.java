import java.util.Scanner;

public class Main {
    private static final int COMMAND_ADD_NEW_EARNINGS = 1;
    private static final int COMMAND_ADD_NEW_SPENDINGS = 2;
    private static final int COMMAND_CHOSE_TAX_SYSTEM = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String titlePrograms = "Помощник индивидуальному предпринимателю";
        String quitText = "end";
        String incomeStr;
        String expenseStr;
        String input;
        int income;
        int expense;
        int earnings = 0;
        int spendings = 0;
        int command;
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n" + titlePrograms + "\n");

            System.out.println("Выберите операцию и введите её номер: ");
            System.out.println(COMMAND_ADD_NEW_EARNINGS + ". Добавить новый доход.");
            System.out.println(COMMAND_ADD_NEW_SPENDINGS + ". Добавить новый расход.");
            System.out.println(COMMAND_CHOSE_TAX_SYSTEM + ". Выбрать систему налогообложения.");
            System.out.println("Для выхода введите: " + quitText);
            input = scanner.nextLine().toLowerCase();

            if (quitText.equals(input)) {
                System.out.println("До новых встречь :)\n");
                isRunning = false;
                continue;
            }

            try {
                command = Integer.parseInt(input);

                switch (command) {
                    case COMMAND_ADD_NEW_EARNINGS:
                        System.out.print("\nВведите сумму дохода: ");
                        incomeStr = scanner.nextLine();
                        income = Integer.parseInt(incomeStr);
                        earnings += income;
                        break;

                    case COMMAND_ADD_NEW_SPENDINGS:
                        System.out.print("\nВведите сумму расхода: ");
                        expenseStr = scanner.nextLine();
                        expense = Integer.parseInt(expenseStr);
                        spendings += expense;
                        break;

                    case COMMAND_CHOSE_TAX_SYSTEM:
                        System.out.println("\nВыбрать систему налогообложения.\n");
                        int tax1 = taxEarnings(earnings);
                        int tax2 = taxEarningsMinusSpendings(earnings, spendings);
                        suggestBestTaxSystem(tax1, tax2);
                        break;

                    default:
                        System.out.println("Такой операции нет!\n");
                        isRunning = false;
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод! Введите номер команды или " + quitText + " для выхода.\n");
            }
        }

        System.out.println("Программа завершена!");
    }

    public static int taxEarnings(int earnings) {
        int taxRate = 6;
        int percentageDivisor = 100;

        return earnings * taxRate / percentageDivisor;
    }

    public static int taxEarningsMinusSpendings(int earnings, int spendings) {
        int taxRate = 15;
        int percentageDivisor = 100;

        int tax = (earnings - spendings) * taxRate / percentageDivisor;

        if (tax >= 0) {
            return tax;
        }
        else {
            System.out.println("Ошибка! Налог не может быть отрицательным.");
            return 0;
        }
    }

    public static void suggestBestTaxSystem(int tax1, int tax2) {
        System.out.println("УСН доходы: " + tax1 + " рублей");
        System.out.println("УСН доходы минус расходы: " + tax2 + " рублей");

        if (tax1 < tax2) {
            System.out.println("\nМы советуем вам УСН доходы.\nЭкономия: " + (tax2 - tax1) + " рублей");
        } else {
            System.out.println("\nМы советуем вам УСН доходы минус расходы.\nЭкономия: " + (tax1 - tax2) + " рублей");
        }
    }
}