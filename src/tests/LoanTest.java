import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Tomasz Gwoździk on 24.03.2017.
 */
public class LoanTest {
    private String clientID;
    private Interest interest;
    private Loan loan;

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

        clientID = UniqueID.generate();

        DateFormat format = new SimpleDateFormat("dd.MM.yyy");
        Date date = format.parse("25.04.2017");
        loan = new Loan(clientID, 1000.0, date, interest);
    }

    @Test
    public void getClientID() {
        assertEquals(clientID, loan.getClientID());
    }

    @Test
    public void getInterest() throws Exception {
        DateFormat format = new SimpleDateFormat("dd.MM.yyy");
        Date date = format.parse("25.04.2017");

        Double interest = loan.getInterest(date);

        assertEquals(20.0, interest, 0);
    }

    @Test
    public void close() throws Exception {
        DateFormat format = new SimpleDateFormat("dd.MM.yyy");
        Date date = format.parse("25.04.2017");

        double payOff = loan.close(date);

        assertEquals(1020.0, payOff, 0);
    }

    @Test (expected = ProductIsAlreadyClosedException.class)
    public void closeWhenInactive() throws Exception {
        DateFormat format = new SimpleDateFormat("dd.MM.yyy");
        Date date = format.parse("25.04.2017");

        loan.close(date);
        loan.close(date);
    }
}