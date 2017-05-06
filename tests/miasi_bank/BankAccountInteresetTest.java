package miasi_bank;

import custom_exceptions.ClientAlreadyExistException;
import custom_exceptions.ClientOrProductDoesNotExistException;
import custom_exceptions.WrongValueException;
import miasi_bank.interests.ExtendedInterest;
import miasi_bank.interests.IInterest;
import miasi_bank.interests.JuniorInterest;
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
  public void checkJuniorInterestSmallAmount() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    this.juniorInterest = new JuniorInterest();

    String accountID = bank.createAccount(clientID, 50.0, juniorInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(52.5, bank.getAccountBalance(clientID, accountID), 0.001);
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
  public void checkExtendedInterestFirst() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    ExtendedInterest extendedInterest = new ExtendedInterest();
    String clientID = bank.addClient("Tomasz", "Franek", "12323534");
    String accountID = bank.createAccount(clientID, 5000.0, extendedInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(5050.0, bank.getAccountBalance(clientID, accountID), 0.01);
  }

  @Test
  public void checkExtendedInterestSecond() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    ExtendedInterest extendedInterest = new ExtendedInterest();
    String clientID = bank.addClient("Tomasz", "Franek", "12323534");
    String accountID = bank.createAccount(clientID, 13000.0, extendedInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(13160.0, bank.getAccountBalance(clientID, accountID), 0.01);
  }

  @Test
  public void checkExtendedInterestThird() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
    ExtendedInterest extendedInterest = new ExtendedInterest();
    String clientID = bank.addClient("Tomasz", "Franek", "12323534");
    String accountID = bank.createAccount(clientID, 20000.0, extendedInterest);
    bank.calculateAndAddInterestToAccount(clientID, accountID);

    assertEquals(20350.0, bank.getAccountBalance(clientID, accountID), 0.01);
  }


}