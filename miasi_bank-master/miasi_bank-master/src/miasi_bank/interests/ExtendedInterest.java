package miasi_bank.interests;

import java.util.Date;

public class ExtendedInterest implements IInterest {

    @Override
    public double calculate(double amount) {
        double result = 0;

        result += Math.min(amount, 10000) * 0.01;

        if(amount > 10000) {
            result += Math.min((amount - 10000), 5000) * 0.02;
        }

        if(amount > 15000) {
            result += (amount - 15000) * 0.03;
        }

        return result;
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
