package miasi_bank.interests;

import java.util.Date;

public class LinearInterest implements IInterest {
    private double percent = 0.02;

    @Override
    public double calculate(double amount) {
        return amount * percent;
    }

    @Override
    public double calculate(double amount, Date startDate, Date endDate) {
        int years = DateUtils.getDiffYears(startDate, endDate);
        double amountTemp = amount;

        for(int i = 0; i < Math.max(1, years); i++) {
            amountTemp = this.calculate(amountTemp);
        }
        return amountTemp;
    }
}
