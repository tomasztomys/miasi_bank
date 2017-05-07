package miasi_bank;

import custom_exceptions.*;

import miasi_bank.interests.IInterest;
import miasi_bank.placements.Placement;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class PlacementTest {
    private String clientID;
    private IInterest interest;
    private Placement placement;
    private Date closeDate;

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

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 1);
        closeDate = c.getTime();
        placement = new Placement(clientID, 1000.0, closeDate, interest);
    }

    @Test
    public void closeEqualEndDate() throws Exception {
        assertEquals(1020.0, placement.calculateAmount(closeDate), 0);
    }

    @Test
    public void closeAfterEndDate() throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(closeDate);
        c.add(Calendar.DAY_OF_MONTH, 1);

        assertEquals(1020.0, placement.calculateAmount(c.getTime()), 0);
    }

    @Test
    public void closeBeforeEndDate() throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(closeDate);
        c.add(Calendar.DAY_OF_MONTH, -1);

        assertEquals(1000.0, placement.calculateAmount(c.getTime()), 0);
    }

    @Test (expected = ProductIsAlreadyClosedException.class)
    public void closeWhenInactive() throws Exception {

        placement.close(closeDate);
        placement.calculateAmount(closeDate);
    }
}