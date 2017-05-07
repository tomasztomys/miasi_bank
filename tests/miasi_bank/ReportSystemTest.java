package miasi_bank;

import miasi_bank.interests.IInterest;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class ReportSystemTest {
    private Bank bank;
    private ReportSystem reportSystem;
    private IInterest interest;

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
        reportSystem = new ReportSystem();

        String clientID = bank.addClient("Tomasz", "Franek", "12323534");
        String accountID = bank.createAccount(clientID, 500, interest);
        bank.createAccount(clientID, 800, interest);
        bank.createAccount(clientID, 1000, interest);
        bank.createAccount(clientID, 1800, interest);
        bank.createAccount(clientID, 0, interest);

        DateFormat format = new SimpleDateFormat("dd.MM.yyy");
        Date closingDate = format.parse("25.04.2017");
        bank.createLoan(clientID, accountID, 800.0, closingDate, this.interest);
        bank.createLoan(clientID, accountID, 100.0, closingDate, this.interest);
        bank.createLoan(clientID, accountID, 1000.0, closingDate, this.interest);
        bank.createLoan(clientID, accountID, 1800.0, closingDate, this.interest);
    }

    @Test
    public void makeReportUnderLimit1000() throws Exception {
        assertEquals(4, bank.makeBalanceRaport(ReportType.UNDER_LIMIT, 1000).size());
    }

    @Test
    public void makeReportAtLeastLimit1000() throws Exception {
        assertEquals(5, bank.makeBalanceRaport(ReportType.AT_LEAST_LIMIT, 1000).size());
    }
}
