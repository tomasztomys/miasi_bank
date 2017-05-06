package miasi_bank;

import custom_exceptions.*;

import miasi_bank.interests.IInterest;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class BankExternalTransfersTest {
    private IInterest interest;
    private Bank bank;
    private Bank bank2;
    private String clientFromID;
    private String clientToID;
    private String accountFromID;
    private String accountToID;

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
        bank2 = new Bank();
        KIR.addBank(bank);
        KIR.addBank(bank2);
        clientFromID = bank.addClient("Tomasz", "Gwozdzik", "12323534");
        clientToID = bank2.addClient("Maks", "Marcinowski", "132123123");
        accountFromID = bank.createAccount(clientFromID, 5000, interest);
        accountToID = bank2.createAccount(clientToID, interest);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void ExternalTransferToNotExistToAccount() throws Exception {
        bank.makeExternalOperation(clientFromID, accountFromID, "xxx", 3000);
    }

    @Test
    public void ExternalTransfer() throws Exception {
        bank.makeExternalOperation(clientFromID, accountFromID, accountToID, 3000);

        assertEquals(0, bank.getAccountTotalBalance(clientFromID, accountFromID), 2000);
        assertEquals(3000, bank2.getAccountTotalBalance(clientToID, accountToID), 3000);
    }

    @Test (expected = NoResourcesException.class)
    public void ExternalTransferMoreThanHave() throws Exception {
        bank.makeExternalOperation(clientFromID, accountFromID, accountToID, 8000);
    }

    @Test (expected = WrongValueException.class)
    public void ExternalTransferLessThanZero() throws Exception {
        bank.makeExternalOperation(clientFromID, accountFromID, accountToID, -3000);
    }

    @Test (expected = WrongValueException.class)
    public void ExternalTransferZero() throws Exception {
        bank.makeExternalOperation(clientFromID, accountFromID, accountToID, 0);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void ExternalTransferFromFakeAccount() throws Exception {
        bank.makeExternalOperation(clientFromID, "xxx", accountToID, 1000);
    }
}