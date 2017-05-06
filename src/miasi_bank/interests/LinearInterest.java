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
        return amount * percent;
    }
}
