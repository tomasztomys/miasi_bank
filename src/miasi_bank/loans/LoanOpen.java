package miasi_bank.loans;

import custom_exceptions.ProductIsAlreadyClosedException;

import java.util.Date;

public class LoanOpen implements ILoanState {
  public double getTotalAmount(Loan loan, Date date) throws ProductIsAlreadyClosedException {


    return loan.getBalance() + loan.getInterest(date);
  }
}
