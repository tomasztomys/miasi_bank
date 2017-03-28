import java.util.Date;

/**
 * Created by Tomasz Gwoździk on 22.03.2017.
 */
public class Loan extends Product {
    private Date closingDate;
    private boolean isActive;

    public Loan(String clientID, double startBalance, Date closingDate, Interest interest) throws WrongValueException {
        super(clientID, startBalance, interest);

        this.isActive = true;
        this.closingDate = closingDate;
    }

    public double getInterest(Date date) {
        return super.getInterest().calculate(getBalance(), getCreationDate(), date);
    }

    public double close(Date date) throws ProductIsAlreadyClosedException {
        if(!isActive) throw new ProductIsAlreadyClosedException("Lokata " + getID() + " została już zamknięta");

        this.isActive = false;

        return getBalance() + getInterest(date);
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
