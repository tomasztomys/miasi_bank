package miasi_bank;

import java.util.Date;

public class LinearInterest implements IInterest {
    private double percent;

    public LinearInterest(double percent) {
        this.percent = percent / 100;
    }

    public double getPercent() {
        return percent;
    }

    @Override
    public double calculate(double amount) {
        return amount * percent;
    }

    @Override
    public double calculate(double amount, Date startDate, Date endDate) {
        return amount * percent;
    }
}
