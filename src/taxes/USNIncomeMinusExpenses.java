package taxes;

public class USNIncomeMinusExpenses extends TaxSystem {
    private static final double TAX_RATE = 0.15;
    private static final int MIN_TAX = 0;

    @Override
    public int calcTaxFor(int debit, int credit) {
        int difference = debit - credit;
        if (difference > MIN_TAX) {
            return (int) Math.round(difference * TAX_RATE);
        }
        return 0;
    }
}
