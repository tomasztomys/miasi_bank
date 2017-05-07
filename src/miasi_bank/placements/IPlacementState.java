package miasi_bank.placements;

import custom_exceptions.ProductIsAlreadyClosedException;
import miasi_bank.loans.Loan;

import java.util.Date;

public interface IPlacementState {
  boolean getIsActive();
  double calculateAmount(Placement placement, Date date) throws ProductIsAlreadyClosedException;
}
