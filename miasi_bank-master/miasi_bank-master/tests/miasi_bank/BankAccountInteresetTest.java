package miasi_bank;

import custom_exceptions.ClientAlreadyExistException;
import custom_exceptions.ClientOrProductDoesNotExistException;
import custom_exceptions.WrongValueException;
import miasi_bank.interests.ExtendedInterest;
import miasi_bank.interests.IInterest;
import miasi_bank.interests.JuniorInterest;
import miasi_bank.interests.LinearInterest;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class BankAccountInteresetTest {
  private IInterest interest;
  private IInterest juniorInterest;
  private IInterest extendedInterest;
  private Bank bank;
  private String clientID;

  @Before
  public void setUp() throws Exception {
    bank = new Bank();
    clientID = bank.addClient("Tomasz", "Franek", "12323534");
  }

  @Test
  public void checkLinearInterest() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    this.juniorInterest = new LinearInterest();

    String accountID = bank.createAccount(clientID, 500.0, juniorInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(510, bank.getAccountBalance(clientID, accountID), 0.001);
  }

  @Test
  public void checkLinearInterest2() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    this.juniorInterest = new LinearInterest();

    String accountID = bank.createAccount(clientID, 1000.0, juniorInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(1020, bank.getAccountBalance(clientID, accountID), 0.001);
  }

  @Test
  public void checkJuniorInterestSmallAmountEdge() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    this.juniorInterest = new JuniorInterest();

    String accountID = bank.createAccount(clientID, 1000.0, juniorInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(1050.0, bank.getAccountBalance(clientID, accountID), 0.001);
  }

  @Test
  public void checkJuniorInterestBigAmountEdge() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    this.juniorInterest = new JuniorInterest();

    String accountID = bank.createAccount(clientID, 1001.0, juniorInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(1051.01, bank.getAccountBalance(clientID, accountID), 0.001);
  }

  @Test
  public void checkJuniorInterestBigAmount() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    this.juniorInterest = new JuniorInterest();

    String accountID = bank.createAccount(clientID, 5000.0, juniorInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(5090.0, bank.getAccountBalance(clientID, accountID), 0.01);
  }

  @Test
  public void checkExtendedInterestSmallAmount() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    ExtendedInterest extendedInterest = new ExtendedInterest();
    String accountID = bank.createAccount(clientID, 5000.0, extendedInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(5050.0, bank.getAccountBalance(clientID, accountID), 0.01);
  }

  @Test
  public void checkExtendedInterestSmallAmountEdge() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    ExtendedInterest extendedInterest = new ExtendedInterest();
    String accountID = bank.createAccount(clientID, 10000.0, extendedInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(10100.00, bank.getAccountBalance(clientID, accountID), 0.001);
  }

  @Test
  public void checkExtendedInterestMediumAmountEdge() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    ExtendedInterest extendedInterest = new ExtendedInterest();
    String accountID = bank.createAccount(clientID, 10001.0, extendedInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(10101.02, bank.getAccountBalance(clientID, accountID), 0.001);
  }

  @Test
  public void checkExtendedInterestMediumAmount() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    ExtendedInterest extendedInterest = new ExtendedInterest();
    String accountID = bank.createAccount(clientID, 12500.0, extendedInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(12650.0, bank.getAccountBalance(clientID, accountID), 0.001);
  }

  @Test
  public void checkExtendedInterestMediumAmountEndEdge() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    ExtendedInterest extendedInterest = new ExtendedInterest();
    String accountID = bank.createAccount(clientID, 15000.0, extendedInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(15200.0, bank.getAccountBalance(clientID, accountID), 0.001);
  }

  @Test
  public void checkExtendedInterestBigAmountEdge() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    ExtendedInterest extendedInterest = new ExtendedInterest();
    String accountID = bank.createAccount(clientID, 15001.0, extendedInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(15201.03, bank.getAccountBalance(clientID, accountID), 0.001);
  }

  @Test
  public void checkExtendedInterestBigAmount() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    ExtendedInterest extendedInterest = new ExtendedInterest();
    String accountID = bank.createAccount(clientID, 20000.0, extendedInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(20350.0, bank.getAccountBalance(clientID, accountID), 0.01);
  }

}