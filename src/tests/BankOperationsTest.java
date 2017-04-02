import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class BankOperationsTest {
    private Interest interest;
    private Bank bank;
    private String clientFromID;
    private String clientToID;
    private String accountFromID;
    private String accountToID;

    @Before
    public void setUp() throws Exception {
        interest = new Interest() {
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
        clientFromID = bank.addClient("Tomasz", "Gwozdzik", "12323534");
        clientToID = bank.addClient("Maks", "Marcinowski", "132123123");
        accountFromID = bank.createAccount(clientFromID, interest);
        accountToID = bank.createAccount(clientToID, interest);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void PaymentWrongClientID() throws Exception {
        bank.payment("test", accountFromID, 3000);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void PaymentWrongAccountID() throws Exception {
        bank.payment(clientFromID, "test", 3000);
    }

    @Test (expected = WrongValueException.class)
    public void PaymentLessThanZero() throws Exception {
        bank.payment(clientFromID, accountFromID, -3000);
    }

    @Test (expected = WrongValueException.class)
    public void PaymentZero() throws Exception {
        bank.payment(clientFromID, accountFromID, 0);
    }

    @Test
    public void PaymentMoreThanZero() throws Exception {
        bank.payment(clientFromID, accountFromID, 3000);

        assertEquals(3000, bank.getAccountTotalBalance(clientFromID, accountFromID), 0);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void WithdrawWrongClientID() throws Exception {
        bank.withdraw("test", accountToID, 3000);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void WithdrawWrongAccountID() throws Exception {
        bank.withdraw(clientToID, "test", 3000);
    }

    @Test (expected = WrongValueException.class)
    public void WithdrawLessThanZero() throws Exception {
        bank.withdraw(clientToID, accountToID, -3000);
    }

    @Test (expected = WrongValueException.class)
    public void WithdrawZero() throws Exception {
        bank.withdraw(clientToID, accountToID, 0);
    }

    @Test (expected = NoResourcesException.class)
    public void WithdrawMoreThanZeroNoMoney() throws Exception {
        bank.withdraw(clientToID, accountToID, 3000);
    }

    @Test
    public void WithdrawMoreThanZero() throws Exception {
        bank.payment(clientToID, accountToID, 3000);
        bank.withdraw(clientToID, accountToID, 3000);

        assertEquals(0, bank.getAccountTotalBalance(clientToID, accountToID), 0);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void TransferWrongClientID() throws Exception {
        bank.transfer("test", accountFromID, accountToID, 3000);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void TransferWrongAccountFromID() throws Exception {
        bank.transfer(clientFromID, "test", accountToID, 3000);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void TransferWrongAccountToID() throws Exception {
        bank.transfer(clientFromID, clientFromID, "test", 3000);
    }

    @Test (expected = WrongValueException.class)
    public void TransferLessThanZero() throws Exception {
        bank.transfer(clientFromID, accountFromID, accountToID, -3000);
    }

    @Test (expected = WrongValueException.class)
    public void TransferZero() throws Exception {
        bank.transfer(clientFromID, accountFromID, accountToID, 0);
    }

    @Test (expected = NoResourcesException.class)
    public void TransferMoreThanZeroNoMoney() throws Exception {
        bank.transfer(clientFromID, accountFromID, accountToID, 3000);
    }

    @Test
    public void TransferMoreThanZero() throws Exception {
        bank.payment(clientFromID, accountFromID, 3000);
        bank.transfer(clientFromID, accountFromID, accountToID, 3000);

        assertEquals(0, bank.getAccountTotalBalance(clientFromID, accountFromID), 0);
        assertEquals(3000, bank.getAccountTotalBalance(clientToID, accountToID), 0);
    }
}