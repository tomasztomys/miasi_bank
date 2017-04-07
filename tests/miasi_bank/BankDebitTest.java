package miasi_bank;

import custom_exceptions.*;

import miasi_bank.interests.IInterest;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class BankDebitTest {
    private IInterest interest;
    private Bank bank;
    private String clientID;
    private String accountID;

    @Before
    public void setUp() throws Exception {
        interest = new IInterest() {
            @Override
            public double calculate(double amount) {
                return amount * 0.05;
            }

            @Override
            public double calculate(double amount, Date startDate, Date endDate) {
                return endDate.after(startDate) ? amount * 0.1 : amount;
            }
        };

        bank = new Bank();
        clientID = bank.addClient("Tomasz", "Gwozdzik", "12323534");
        accountID = bank.createAccount(clientID, interest);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void DebitWrongClientID() throws Exception {
        bank.setDebitAccount("test", accountID, 5000.0);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void DebitWrongAccountID() throws Exception {
        bank.setDebitAccount(clientID, "test", 5000.0);
    }

    @Test (expected = DebitAccountAlreadyExists.class)
    public void DebitOnAccountWithDebit() throws Exception {
        bank.setDebitAccount(clientID, accountID, 5000.0);
        bank.setDebitAccount(clientID, accountID, 5000.0);
    }

    @Test
    public void CheckAccountsAfterSettingDebit() throws Exception {
        bank.setDebitAccount(clientID, accountID, 5000.0);

        assertEquals(1, bank.getAccounts().size(), 0);
        assertEquals(5000, bank.getAccountTotalBalance(clientID, accountID), 0);
    }

    @Test (expected = NoResourcesException.class)
    public void DebitWidthdrawMoreThanYouHave() throws Exception {
        bank.setDebitAccount(clientID, accountID, 5000.0);

        bank.withdraw(clientID, accountID, 6000.0);
    }

    @Test (expected = WrongValueException.class)
    public void DebitWidthdrawZero() throws Exception {
        bank.setDebitAccount(clientID, accountID, 5000.0);

        bank.withdraw(clientID, accountID, 0.0);
    }

    @Test (expected = WrongValueException.class)
    public void DebitWidthdrawLessThanZero() throws Exception {
        bank.setDebitAccount(clientID, accountID, 5000.0);

        bank.withdraw(clientID, accountID, -5000.0);
    }

    @Test
    public void DebitWidthdraw() throws Exception {
        bank.setDebitAccount(clientID, accountID, 5000.0);

        bank.withdraw(clientID, accountID, 4000.0);
        assertEquals(1000.0, bank.getAccountTotalBalance(clientID, accountID), 0);
        assertEquals(0.0, bank.getAccountBalance(clientID, accountID), 0);
    }

    @Test
    public void DebitPaymentOnlyDebit() throws Exception {
        bank.setDebitAccount(clientID, accountID, 5000.0);

        bank.withdraw(clientID, accountID, 4000.0);
        bank.payment(clientID, accountID, 2000.0);

        assertEquals(3000.0, bank.getAccountTotalBalance(clientID, accountID), 0);
        assertEquals(3000.0, bank.getAccountTotalBalance(clientID, accountID), 0);
        assertEquals(0.0, bank.getAccountBalance(clientID, accountID), 0);
    }

    @Test
    public void DebitPaymentToAccountAlso() throws Exception {
        bank.setDebitAccount(clientID, accountID, 5000.0);

        bank.withdraw(clientID, accountID, 4000.0);
        bank.payment(clientID, accountID, 8000.0);

        assertEquals(9000.0, bank.getAccountTotalBalance(clientID, accountID), 0);
        assertEquals(5000.0, bank.getAccountTotalBalance(clientID, accountID) - bank.getAccountBalance(clientID, accountID), 0);
        assertEquals(4000.0, bank.getAccountBalance(clientID, accountID), 0);
    }

    @Test
    public void WidthdrawFromAccount() throws Exception {
        bank.setDebitAccount(clientID, accountID, 5000.0);
        bank.payment(clientID, accountID, 8000.0);

        assertEquals(8000.0, bank.getAccountBalance(clientID, accountID), 0);

        bank.withdraw(clientID, accountID, 2000.0);

        assertEquals(6000.0, bank.getAccountBalance(clientID, accountID), 0);
        assertEquals(11000.0, bank.getAccountTotalBalance(clientID, accountID), 0);
    }
}