package miasi_bank.loans;

import custom_exceptions.ProductIsAlreadyClosedException;
import custom_exceptions.WrongCloseDateException;
import miasi_bank.Loan;

import java.util.Date;

public interface ILoanState {
  public double close(Loan loan, Date date) throws ProductIsAlreadyClosedException, WrongCloseDateException;
}
