package interests;

import java.util.Date;

/**
 * Created by Tomasz Gwoździk on 23.03.2017.
 */
public class LinearInterest implements Interest {
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
