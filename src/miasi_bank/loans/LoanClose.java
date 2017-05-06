package miasi_bank.loans;

import custom_exceptions.ProductIsAlreadyClosedException;
import custom_exceptions.WrongCloseDateException;
import miasi_bank.Loan;

import java.util.Date;

public class LoanClose  implements ILoanState {
  public double close(Loan loan, Date date) throws ProductIsAlreadyClosedException, WrongCloseDateException {
    throw new ProductIsAlreadyClosedException("Lokata " + loan.getID() + " została już zamknięta");
  }
}