package miasi_bank;

import custom_exceptions.*;
import miasi_bank.interests.IInterest;

import java.util.Date;

public class Placement extends Product {
    private Date closingDate;
    private boolean isActive;

    public Placement(String clientID, double startBalance, Date closingDate, IInterest interest) throws WrongValueException {
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
}
