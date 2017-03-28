import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Tomasz Gwoździk on 28.03.2017.
 */
public class BankPlacementTest {
    private Interest interest;
    private Bank bank;
    private String clientID;
    private String accountID;
    private Date closeDate;
    private DateFormat dateFormat;

    @Before
    public void setUp() throws Exception {
        interest = new Interest() {
            @Override
            public double calculate(double amount) {
                return amount * 0.05;
            }

            @Override
            public double calculate(double amount, Date startDate, Date endDate) {
                return amount * 0.05;
            }
        };

        bank = new Bank();
        clientID = bank.addClient("Tomasz", "Gwozdzik", "12323534");
        accountID = bank.createAccount(clientID, interest);

        dateFormat = new SimpleDateFormat("dd.MM.yyy");
        closeDate = dateFormat.parse("15.04.2017");
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void PlacementWrongClientID() throws Exception {
        bank.payment(clientID, accountID, 3000);
        bank.createPlacement("test", accountID, 3000, closeDate, interest);

        assertTrue(false);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void PlacementWrongAccountID() throws Exception {
        bank.payment(clientID, accountID, 3000);
        bank.createPlacement("test", accountID, 3000, closeDate, interest);

        assertTrue(false);
    }
}