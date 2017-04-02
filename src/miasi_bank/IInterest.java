package miasi_bank;

import java.util.Date;

public interface IInterest {
    double calculate(double amount);
    double calculate(double amount, Date startDate, Date endDate);
}
