package miasi_bank;

import custom_exceptions.CustomException;
import interests.Interest;

import java.util.Date;

/**
 * Created by Tomasz Gwoździk on 22.03.2017.
 */
public class Placement extends Product {
    private Date closingDate;
    private boolean isActive;

    Placement(String clientID, double startBalance, Date closingDate, Interest interest) {
        super(clientID, startBalance, interest);

        this.isActive = true;
        this.closingDate = closingDate;
    }

    public double close(Date date) throws CustomException {
        if(!isActive) throw new CustomException("Lokata " + getID() + " została już zamknięta");

        this.isActive = false;
        if(date.equals(closingDate) || date.after(closingDate)) {
            return getBalance() + getInterest().calculate(getBalance(), getCreationDate(), closingDate);
        }

        return getBalance();
    }

    @Deprecated
    @Override
    public double withdraw(Operation operation) {
        return 0.0;
    }

    @Deprecated
    @Override
    public double payment(Operation operation) {
        return 0.0;
    }
}
