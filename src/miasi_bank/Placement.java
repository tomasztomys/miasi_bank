import java.util.Date;

public class Placement extends Product {
    private Date closingDate;
    private boolean isActive;

    public Placement(String clientID, double startBalance, Date closingDate, Interest interest) throws WrongValueException {
        super(clientID, startBalance, interest);

        this.isActive = true;
        this.closingDate = closingDate;
    }

    public double close(Date date) throws ProductIsAlreadyClosedException {
        if(!isActive) throw new ProductIsAlreadyClosedException("Lokata " + getID() + " została już zamknięta");

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
