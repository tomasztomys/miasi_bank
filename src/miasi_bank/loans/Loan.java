package miasi_bank.loans;

import custom_exceptions.*;
import miasi_bank.Product;
import miasi_bank.interests.IInterest;
import miasi_bank.loans.ILoanState;
import miasi_bank.loans.LoanClose;
import miasi_bank.loans.LoanOpen;

import java.util.Date;

public class Loan extends Product {
    private Date closingDate;
    private boolean isActive;
    private ILoanState state = null;

    public Loan(String clientID, double startBalance, Date closingDate, IInterest interest) throws WrongValueException {
        super(clientID, startBalance, interest);

        this.isActive = true;
        this.closingDate = closingDate;
        this.state = new LoanOpen();
    }

    public double getInterest(Date date) {
        return super.getInterest().calculate(getBalance(), getCreationDate(), date);
    }

    public double getTotalAmount(Date date) throws ProductIsAlreadyClosedException, WrongCloseDateException {
        this.validCloseDate(date);
        return this.state.getTotalAmount(this, date);
    }

    public void validCloseDate(Date date) throws WrongCloseDateException {
        if(this.getCreationDate().compareTo(date) > 0) {
            throw new WrongCloseDateException("Niepoprawna data zamkniecia kredytu " + this.getID());
        }
    }

    public void close(Date date) throws WrongCloseDateException {
        this.validCloseDate(date);
        this.state = new LoanClose();
    }
}
