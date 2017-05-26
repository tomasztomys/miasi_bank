package miasi_bank;

import custom_exceptions.*;

import miasi_bank.interests.IInterest;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class BankPlacementTest {
    private IInterest interest;
    private Bank bank;
    private String clientID;
    private String accountID;
    private Date closeDate;
    private DateFormat dateFormat;

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

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 1);
        closeDate = c.getTime();
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void PlacementWrongClientID() throws Exception {
        bank.payment(clientID, accountID, 3000);
        bank.createPlacement("test", accountID, 3000, closeDate, interest);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void PlacementWrongAccountID() throws Exception {
        bank.payment(clientID, accountID, 3000);
        bank.createPlacement(clientID, "test", 3000, closeDate, interest);
    }

    @Test (expected = WrongValueException.class)
    public void PlacementLessThanZero() throws Exception {
        bank.payment(clientID, accountID, 3000);
        bank.createPlacement(clientID, accountID, -3000, closeDate, interest);
    }

    @Test (expected = WrongValueException.class)
    public void PlacementZero() throws Exception {
        bank.payment(clientID, accountID, 3000);
        bank.createPlacement(clientID, accountID, 0, closeDate, interest);
    }

    @Test (expected = NoResourcesException.class)
    public void PlacementMoreThanHaveOnAccount() throws Exception {
        bank.payment(clientID, accountID, 3000);
        bank.createPlacement(clientID, accountID, 5000, closeDate, interest);
    }

    @Test
    public void PlacementCreate() throws Exception {
        bank.payment(clientID, accountID, 3000);
        String placementID = bank.createPlacement(clientID, accountID, 2000, closeDate, interest);

        assertNotNull(placementID);
        assertEquals(1000.0, bank.getAccountTotalBalance(clientID, accountID), 0);
    }

    @Test
    public void CheckHistory() throws Exception {
        bank.payment(clientID, accountID, 3000);
        bank.createPlacement(clientID, accountID, 2000, closeDate, interest);

        assertEquals(2, bank.getHistory().getHistory().size(), 0);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void ClosePlacementWrongClientID() throws Exception {
        bank.payment(clientID, accountID, 3000);
        String placementID = bank.createPlacement(clientID, accountID, 2000, closeDate, interest);

        bank.closePlacement("test", accountID, placementID, closeDate);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void ClosePlacementWrongAccountID() throws Exception {
        bank.payment(clientID, accountID, 3000);
        String placementID = bank.createPlacement(clientID, accountID, 2000, closeDate, interest);

        bank.closePlacement(clientID, "test", placementID, closeDate);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void ClosePlacementWrongPlacementID() throws Exception {
        bank.payment(clientID, accountID, 3000);
        bank.createPlacement(clientID, accountID, 2000, closeDate, interest);

        bank.closePlacement(clientID, accountID, "test", closeDate);
    }

    @Test
    public void ClosePlacementBeforeEndTime() throws Exception {
        bank.payment(clientID, accountID, 3000);
        String placementID = bank.createPlacement(clientID, accountID, 2000, closeDate, interest);
        Calendar c = Calendar.getInstance();
        c.setTime(closeDate);
        c.add(Calendar.MONTH, -1);

        double balance = bank.closePlacement(clientID, accountID, placementID, c.getTime());

        assertEquals(3000.0, balance, 0);
    }

    @Test
    public void ClosePlacementAfterEndTime() throws Exception {
        bank.payment(clientID, accountID, 3000);
        String placementID = bank.createPlacement(clientID, accountID, 2000, closeDate, interest);
        Calendar c = Calendar.getInstance();
        c.setTime(closeDate);
        c.add(Calendar.MONTH, 1);
        double balance = bank.closePlacement(clientID, accountID, placementID, c.getTime());

        assertEquals(3200.0, balance, 0);
    }

    @Test
    public void ClosePlacementAfterEndTimeExact() throws Exception {
        bank.payment(clientID, accountID, 3000);
        String placementID = bank.createPlacement(clientID, accountID, 2000, closeDate, interest);

        double balance = bank.closePlacement(clientID, accountID, placementID, closeDate);

        assertEquals(3200.0, balance, 0);
    }

    @Test (expected = ProductIsAlreadyClosedException.class)
    public void CloseClosedPlacement() throws Exception {
        bank.payment(clientID, accountID, 3000);
        String placementID = bank.createPlacement(clientID, accountID, 2000, closeDate, interest);

        Calendar c = Calendar.getInstance();
        c.setTime(closeDate);
        c.add(Calendar.MONTH, +1);
        bank.closePlacement(clientID, accountID, placementID, c.getTime());
        bank.closePlacement(clientID, accountID, placementID, c.getTime());
    }

    @Test
    public void ClosePlacementHistory() throws Exception {
        bank.payment(clientID, accountID, 3000);
        String placementID = bank.createPlacement(clientID, accountID, 2000, closeDate, interest);

        bank.closePlacement(clientID, accountID, placementID, closeDate);

        assertEquals(3, bank.getHistory().getHistory().size(), 0);
    }
}