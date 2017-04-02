package miasi_bank;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class AccountTest {
    private Product product;
    private IInterest interest;
    private Account accountProductRef;
    private Account accountNoStartBalance;
    private Account accountWithStartBalance;
    private String clientID;

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

        clientID = UniqueID.generate();
        product = new Product(clientID, 1000.0, interest);

        accountProductRef = new Account(product);
        accountNoStartBalance = new Account(clientID, interest);
        accountWithStartBalance = new Account(clientID, 500.0, interest);
    }

    @Test
    public void getClientID() throws Exception {
        assertEquals(clientID, accountNoStartBalance.getClientID());
        assertEquals(clientID, accountProductRef.getClientID());
        assertEquals(clientID, accountWithStartBalance.getClientID());
    }

    @Test
    public void calculateInterestProdRef() throws Exception {
        assertEquals(20.0, accountProductRef.calculateInterest(), 0);
    }

    @Test
    public void calculateInterestNoStartBalance() throws Exception {
        assertEquals(0.0, accountNoStartBalance.calculateInterest(), 0);
    }

    @Test
    public void calculateInterestWithStartBalance() throws Exception {
        assertEquals(10.0, accountWithStartBalance.calculateInterest(), 0);
    }

    @Test
    public void getTotalBalanceProdRef() throws Exception {
        assertEquals(1000.0, accountProductRef.getBalance(), 0);
    }

    @Test
    public void getTotalBalanceNoStartBalance() throws Exception {
        assertEquals(0.0, accountNoStartBalance.getBalance(), 0);
    }

    @Test
    public void getTotalBalanceWithStartBalance() throws Exception {
        assertEquals(500.0, accountWithStartBalance.getBalance(), 0);
    }
}