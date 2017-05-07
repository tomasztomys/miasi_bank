package miasi_bank.placements;

import custom_exceptions.ProductIsAlreadyClosedException;
import miasi_bank.loans.Loan;

import java.util.Date;

public class PlacementClose implements IPlacementState {
  @Override
  public double calculateAmount(Placement placement, Date date) throws ProductIsAlreadyClosedException {
    throw new ProductIsAlreadyClosedException("Lokata " + placement.getID() + " została już zamknięta");
  }
}
