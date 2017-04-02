import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class BankLoanTest {
    private Interest interest;
    private Bank bank;
    private String clientID;
    private String accountID;
    private DateFormat format;

    private Date startDate;
    private Date endDate;

    @Before
    public void setUp() throws Exception {
        this.bank = new Bank();
        this.clientID = bank.addClient("Tomasz", "Gwozdzik", "12323534");
        this.accountID = bank.createAccount(clientID, interest);
        this.format = new SimpleDateFormat("dd.MM.yyy");

        Calendar startCal = Calendar.getInstance();
        this.startDate = startCal.getTime();

        Calendar endCal = Calendar.getInstance();
        endCal.set(Calendar.MONTH, startCal.get(Calendar.MONTH) + 6);
        this.startDate = startCal.getTime();
        this.endDate = endCal.getTime();

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
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getLoans() throws Exception {
        Date closingDate = this.format.parse("25.04.2017");
        assertEquals(this.bank.getLoans().size(), 0);

        String loanId = this.bank.createLoan(this.clientID, this.accountID, 5000.0, closingDate, this.interest);
        assertEquals(this.bank.getLoans().size(), 1);

        Loan firstItem = this.bank.getLoans().iterator().next();
        assertEquals(firstItem.getID(), loanId);

        this.bank.createLoan(this.clientID, this.accountID, 10000.0, closingDate, this.interest);
        assertEquals(this.bank.getLoans().size(), 2);
    }

    @Test
    public void createLoan() throws Exception {
        Date closingDate = this.format.parse("25.04.2017");
        double amount = 5000.0;

        assertEquals(this.bank.getLoans().size(), 0);
        this.bank.createLoan(this.clientID, this.accountID, 5000.0, closingDate, this.interest);
        assertEquals(this.bank.getLoans().size(), 1);
        Loan firstItem = this.bank.getLoans().iterator().next();
        assertEquals(firstItem.getBalance(), amount, 0.01);
        assertEquals(firstItem.getClientID(), this.clientID);
    }

    @Test (expected = WrongValueException.class)
    public void createLoanWrongValue() throws Exception {
        this.bank.createLoan(this.clientID, this.accountID, -500, this.endDate, this.interest);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void wrongAccountIdCreateLoan() throws Exception {
        Date closingDate = this.format.parse("25.04.2017");
        this.bank.createLoan(this.clientID, "test", 5000.0, closingDate, this.interest);
    }

    @Test (expected = ClientOrProductDoesNotExistException.class)
    public void wrongUserIdCreateLoan() throws Exception {
        Date closingDate = this.format.parse("25.04.2017");
        this.bank.createLoan("test", this.accountID , 5000.0, closingDate, this.interest);
    }

    @Test
    public void payOffLoan() throws Exception {
        double depositAmount = 5000;
        double loanAmount = 1000;
        double interesetAmount = this.interest.calculate(loanAmount, this.startDate, this.endDate);

        this.bank.payment(this.clientID, this.accountID, depositAmount);
        String loanId = this.bank.createLoan(this.clientID, this.accountID,  loanAmount, this.endDate, this.interest);
        this.bank.payOffLoan(this.clientID, this.accountID, loanId, this.endDate);


        assertEquals(this.bank.getAccountBalance(this.clientID, this.accountID), depositAmount  - interesetAmount, 0.01);
    }

    @Test (expected = NoResourcesToPayOffLoanExeption.class)
    public void payOffLoanWithoutMoney()  throws Exception {
        double loanAmount = 1000;
        String loanId = this.bank.createLoan(this.clientID, this.accountID,  loanAmount, this.endDate, this.interest);
        this.bank.payOffLoan(this.clientID, this.accountID, loanId, this.endDate);
    }

    @Test
    public void payOffLoanWithPerfectBalanceForInterest()  throws Exception {
        double loanAmount = 1000;
        double interesetAmount = this.interest.calculate(loanAmount, this.startDate, this.endDate);

        this.bank.payment(this.clientID, this.accountID, interesetAmount);
        String loanId = this.bank.createLoan(this.clientID, this.accountID,  loanAmount, this.endDate, this.interest);
        this.bank.payOffLoan(this.clientID, this.accountID, loanId, this.endDate);


        assertEquals(this.bank.getAccountBalance(this.clientID, this.accountID), 0, 0.01);
    }

    @Test (expected = WrongCloseDateException.class)
    public void payOffLoanWithEndDateBeforeStartDate() throws Exception {
        double depositAmount = 5000;

        double loanAmount = 1000;
        this.bank.payment(this.clientID, this.accountID, depositAmount);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(this.startDate);
        endCal.set(Calendar.DATE, endCal.get(Calendar.DATE) - 1);
        String loanId = this.bank.createLoan(this.clientID, this.accountID,  loanAmount, this.endDate, this.interest);
        this.bank.payOffLoan(this.clientID, this.accountID, loanId, endCal.getTime());
    }

}