import java.util.Date;

public interface Interest {
    double calculate(double amount);
    double calculate(double amount, Date startDate, Date endDate);
}
