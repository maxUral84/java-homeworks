public class Main {
    public static void main(String[] args) {
        // Создаем компании с разными системами налогообложения
        Company company1 = new Company("Рога и копыта", new taxes.USNIncome());
        Company company2 = new Company("Пятёрочка", new taxes.USNIncomeMinusExpenses());

        // Проводим финансовые операции
        company1.shiftMoney(100000);
        company1.shiftMoney(-50000);

        company2.shiftMoney(200000);
        company2.shiftMoney(-150000);

        // Уплачиваем налоги
        company1.payTaxes(); // УСН доходы: 6% от 100000 = 6000
        company2.payTaxes(); // УСН доходы-расходы: 15% от (200000-150000) = 7500

        // Меняем систему налогообложения и тестируем снова
        company1.setTaxSystem(new taxes.USNIncomeMinusExpenses());
        company2.setTaxSystem(new taxes.USNIncome());

        company1.shiftMoney(120000);
        company1.shiftMoney(-30000);

        company2.shiftMoney(180000);
        company2.shiftMoney(-70000);

        company1.payTaxes(); // Теперь УСН доходы-расходы: 15% от (120000-30000) = 13500
        company2.payTaxes(); // Теперь УСН доходы: 6% от 180000 = 10800
    }
}
