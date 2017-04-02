package miasi_bank;

import custom_exceptions.*;

import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class PlacementTest {
    private String clientID;
    private IInterest interest;
    private Placement placement;

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

        DateFormat format = new SimpleDateFormat("dd.MM.yyy");
        Date date = format.parse("25.04.2017");
        placement = new Placement(clientID, 1000.0, date, interest);
    }

    @Test
    public void closeEqualEndDate() throws Exception {
        DateFormat format = new SimpleDateFormat("dd.MM.yyy");
        Date date = format.parse("25.04.2017");

        assertEquals(1020.0, placement.close(date), 0);
    }

    @Test
    public void closeAfterEndDate() throws Exception {
        DateFormat format = new SimpleDateFormat("dd.MM.yyy");
        Date date = format.parse("30.04.2017");

        assertEquals(1020.0, placement.close(date), 0);
    }

    @Test
    public void closeBeforeEndDate() throws Exception {
        DateFormat format = new SimpleDateFormat("dd.MM.yyy");
        Date date = format.parse("22.04.2017");

        assertEquals(1000.0, placement.close(date), 0);
    }

    @Test (expected = ProductIsAlreadyClosedException.class)
    public void closeWhenInactive() throws Exception {
        DateFormat format = new SimpleDateFormat("dd.MM.yyy");
        Date date = format.parse("25.04.2017");

        placement.close(date);
        placement.close(date);
    }
}