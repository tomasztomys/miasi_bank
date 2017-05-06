package miasi_bank.interests;

import java.util.Date;

public class JuniorInterest implements IInterest {

    @Override
    public double calculate(double amount) {
        double result = 0;

          result += Math.min(amount, 1000) * 0.05;

        if(amount > 1000) {
            result += (amount - 1000) * 0.01;
        }

        return result;
    }

    @Override
    public double calculate(double amount, Date startDate, Date endDate) {
        return amount;
    }
}
