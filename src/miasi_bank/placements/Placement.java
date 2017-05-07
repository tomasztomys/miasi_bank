package miasi_bank.placements;

import custom_exceptions.*;
import miasi_bank.Product;
import miasi_bank.interests.IInterest;
import miasi_bank.loans.LoanClose;
import miasi_bank.reports.IVisitor;

import java.util.Date;

public class Placement extends Product {
    private Date initCloseDate;
    private Date closeDate;
    IPlacementState state;

    public Placement(String clientID, double startBalance, Date initCloseDate, IInterest interest) throws WrongValueException {
        super(clientID, startBalance, interest);

        this.initCloseDate = initCloseDate;
        this.state = new PlacementOpen();
    }

    public Date getInitCloseDate() {
        return initCloseDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void validCloseDate(Date date) throws WrongCloseDateException {
        if(this.getCreationDate().compareTo(date) > 0) {
            throw new WrongCloseDateException("Niepoprawna data zamkniecia lokaty" + this.getID());
        }
    }

    public double calculateAmount(Date date) throws ProductIsAlreadyClosedException, WrongCloseDateException {
        this.validCloseDate(date);
        return this.state.calculateAmount(this, date);
    }

    public void close(Date date) throws ProductIsAlreadyClosedException, WrongCloseDateException {
        this.validCloseDate(date);
        this.state = new PlacementClose();
    }

    public boolean getIsActive() {
        return this.state.getIsActive();
    }

    public void accept(IVisitor visitor)
    {
        visitor.visit(this);
    }
}
