package miasi_bank.loans;

import custom_exceptions.ProductIsAlreadyClosedException;
import miasi_bank.Loan;

import java.util.Date;

public interface ILoanState {
  public double getTotalAmount(Loan loan, Date date) throws ProductIsAlreadyClosedException;
}
