package miasi_bank.interests;

import java.util.Date;

public interface IInterest {
    double calculate(double amount);
    double calculate(double amount, Date startDate, Date endDate);
}
