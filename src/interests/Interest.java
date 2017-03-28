import java.util.Date;

/**
 * Created by Tomasz Gwoździk on 23.03.2017.
 */
public interface Interest {
    double calculate(double amount);
    double calculate(double amount, Date startDate, Date endDate);
}
