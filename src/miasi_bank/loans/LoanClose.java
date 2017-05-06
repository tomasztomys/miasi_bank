package miasi_bank.loans;

import custom_exceptions.ProductIsAlreadyClosedException;
import miasi_bank.Loan;

import java.util.Date;

public class LoanClose  implements ILoanState {
  public double getTotalAmount(Loan loan, Date date) throws ProductIsAlreadyClosedException {
    throw new ProductIsAlreadyClosedException("Kredyt " + loan.getID() + " została już zamknięta");
  }
}