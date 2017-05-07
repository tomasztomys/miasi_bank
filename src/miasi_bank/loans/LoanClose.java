package miasi_bank.loans;

import custom_exceptions.ProductIsAlreadyClosedException;

import java.util.Date;

public class LoanClose  implements ILoanState {
  @Override
  public boolean getIsActive() {
    return false;
  }

  public double getTotalAmount(Loan loan, Date date) throws ProductIsAlreadyClosedException {
    throw new ProductIsAlreadyClosedException("Kredyt " + loan.getID() + " została już zamknięta");
  }
}