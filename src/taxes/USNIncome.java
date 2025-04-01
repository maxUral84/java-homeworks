package taxes;

public class USNIncome extends TaxSystem {
    private static final double TAX_RATE = 0.06;

    @Override
    public int calcTaxFor(int debit, int credit) {
        return (int) Math.round(debit * TAX_RATE);
    }

}

