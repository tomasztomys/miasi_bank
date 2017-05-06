package miasi_bank;

import custom_exceptions.*;

import miasi_bank.interests.ExtendedInterest;
import miasi_bank.interests.IInterest;
import miasi_bank.interests.JuniorInterest;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Iterator;

import static org.junit.Assert.*;

public class BankAccountTest {
    private IInterest interest;
    private IInterest juniorInterest;
    private IInterest extendedInterest;
    private Bank bank;

    @Before
    public void setUp() throws Exception {
        interest = new IInterest() {
            @Override
            public double calculate(double amount) {
                return amount * 0.02;
            }

            @Override
            public double calculate(double amount, Date startDate, Date endDate) {
                return amount * 0.02;
            }
        };


        bank = new Bank();
    }


    @Test
    public void checkJuniorInterestEdge() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
        this.juniorInterest = new JuniorInterest();

        String clientID = bank.addClient("Tomasz", "Franek", "12323534");
        String accountID = bank.createAccount(clientID, 100.0, juniorInterest);
        bank.calculateAndAddInterestToAccount(clientID, accountID);

        assertEquals(105.0, bank.getAccountBalance(clientID, accountID), 0.01);
    }

    @Test
    public void checkJuniorInterest1() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
        this.juniorInterest = new JuniorInterest();

        String clientID = bank.addClient("Tomasz", "Franek", "12323534");
        String accountID = bank.createAccount(clientID, 1000.0, juniorInterest);
        bank.calculateAndAddInterestToAccount(clientID, accountID);

        assertEquals(1050.0, bank.getAccountBalance(clientID, accountID), 0.01);
    }


    @Test
    public void checkJuniorInterest2() throws ClientAlreadyExistException, ClientOrProductDoesNotExistException, WrongValueException {
        this.juniorInterest = new JuniorInterest();

        String clientID = bank.addClient("Tomasz", "Franek", "12323534");
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

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void createNormalAccountTest() throws Exception{
        bank.createAccount(UniqueID.generate(), interest);
    }

    @Test
    public void createNewAccount() throws Exception{
        String clientID = bank.addClient("Tomasz", "Franek", "12323534");
        assertNotNull(clientID);
        String accountID = bank.createAccount(clientID, interest);

        assertNotNull(accountID);
        assertEquals(0.0, bank.getHistory().getHistory().size(), 0);
    }

    @Test
    public void checkNormalAccountBalance() throws Exception{
        String clientID = bank.addClient("Tomasz","Franek","12323534");
        String accountID = bank.createAccount(clientID, interest);

        assertEquals(0,bank.getAccountTotalBalance(clientID, accountID), 0);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void createAccountWithStartBalanceNoClient() throws Exception {
        bank.createAccount(UniqueID.generate(), 200.0, interest);
    }

    @Test (expected = WrongValueException.class)
    public void createAccountWithStartBalanceNegative() throws Exception {
        String clientID = bank.addClient("Tomasz", "Franek", "12323534");
        bank.createAccount(clientID, -200, interest);
    }

    @Test
    public void createAccountWithStartBalanceZero() throws Exception {
        String clientID = bank.addClient("Tomasz", "Franek", "12323534");
        String accountID = bank.createAccount(clientID, 0, interest);

        assertEquals(0,bank.getAccountTotalBalance(clientID, accountID), 0);
    }

    @Test
    public void createAccountWithStartBalancePositive() throws Exception {
        String clientID = bank.addClient("Tomasz", "Franek", "12323534");
        String accountID = bank.createAccount(clientID, 500.0, interest);

        assertNotNull(accountID);
        assertEquals(500.0,bank.getAccountTotalBalance(clientID, accountID), 0);
    }

    @Test
    public void createMultipleAccountsToClient() throws Exception {
        String clientID = bank.addClient("Tomasz", "Franek", "12323534");
        String accountID1 = bank.createAccount(clientID, 500.0, interest);
        assertNotNull(accountID1);

        String accountID2 = bank.createAccount(clientID, interest);
        assertNotNull(accountID2);

        assertEquals(2.0, bank.getAccounts().size(), 0);

        Iterator<IAccount> iterator = bank.getAccounts().iterator();
        assertEquals(clientID, iterator.next().getClientID());
        assertEquals(clientID, iterator.next().getClientID());
    }
}