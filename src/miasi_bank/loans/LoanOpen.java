package miasi_bank.loans;

import custom_exceptions.ProductIsAlreadyClosedException;
import custom_exceptions.WrongCloseDateException;
import custom_exceptions.WrongValueException;
import miasi_bank.Loan;
import miasi_bank.Product;
import miasi_bank.interests.IInterest;

import java.util.Date;

public class LoanOpen implements ILoanState {
  public double close(Loan loan, Date date) throws ProductIsAlreadyClosedException, WrongCloseDateException {
    if(loan.getCreationDate().compareTo(date) > 0) {
      throw new WrongCloseDateException("Niepoprawna data zamkniecia lokaty " + loan.getID());
    }

    return loan.getBalance() + loan.getInterest(date);
  }
}
