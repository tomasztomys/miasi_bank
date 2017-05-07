package miasi_bank.placements;

import custom_exceptions.ProductIsAlreadyClosedException;
import miasi_bank.loans.Loan;

import java.util.Date;

public class PlacementOpen implements IPlacementState {
  @Override
  public double calculateAmount(Placement placement, Date date) throws ProductIsAlreadyClosedException {
    if(date.equals(placement.getInitCloseDate()) || date.after(placement.getInitCloseDate())) {
      return placement.getBalance() + placement.getInterest().calculate(placement.getBalance(), placement.getCreationDate(), placement.getInitCloseDate());
    }

    return placement.getBalance();
  }
}
