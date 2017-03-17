package tests;

import miasi_bank.Bank;
import miasi_bank.BankAccount;
import miasi_bank.UserAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by inf117225 on 17.03.2017.
 */
public class BankAccountTest {

    private Bank bank;
    private UserAccount user;

    @Before
    public void setUp() {
        this.bank = new Bank();
        this.user = bank.addUserAccount("Tomek", "Zbyszek", "123");
    }

    @Test
    public void getId() throws Exception {
        BankAccount bankAccount1 = bank.addBankAccount(this.user);
        assertTrue(bankAccount1.getId().length() > 0);
    }

    @Test
    public void getIdCheckUnique() throws Exception {
        BankAccount bankAccount1 = bank.addBankAccount(this.user);
        BankAccount bankAccount2 = bank.addBankAccount(this.user);

        assertNotSame(bankAccount1.getId(), bankAccount2.getId());
    }

    @Test
    public void getBalance() throws Exception {
        BankAccount bankAccount = bank.addBankAccount(this.user);
        Double amount1 = 100.00;
        bankAccount.depositCash(amount1);
        assertEquals(bankAccount.getBalance(), amount1, 0);
    }

//    @Test
//    public void getInvestmentList() throws Exception {
//    }
//
//    @Test
//    public void getCreditList() throws Exception {
//    }
//
//    @Test
//    public void addInvestment() throws Exception {
//
//    }
//
//    @Test
//    public void closeInvestment() throws Exception {
//
//    }
//
//    @Test
//    public void takeCredit() throws Exception {
//
//    }
//
//    @Test
//    public void payOffDebt() throws Exception {
//
//    }
//
    @Test
    public void depositCash() throws Exception {
        BankAccount bankAccount = bank.addBankAccount(this.user);
        Double amount1 = 100.00;
        bankAccount.depositCash(amount1);
        assertEquals(bankAccount.getBalance(), amount1, 0);

        Double amount2 = 50.00;
        bankAccount.depositCash(amount2);
        assertEquals(bankAccount.getBalance(), amount1 + amount2, 0);
    }

    @Test
    public void withdrawCash() throws Exception {
        BankAccount bankAccount = bank.addBankAccount(this.user);
        Double amount1 = 100.00;
        bankAccount.depositCash(amount1);
        bankAccount.withdrawCash(amount1);
        assertEquals(bankAccount.getBalance(), 0, 0);
    }

    @Test
    public void withdrawCrashMoreOfBalance() throws Exception {
        BankAccount bankAccount = bank.addBankAccount(this.user);
        Double amount1 = 100.00;
        Double withdrawAmount = 500.00;
        bankAccount.depositCash(amount1);
        bankAccount.withdrawCash(withdrawAmount);
        assertEquals(bankAccount.getBalance(), 0, 0);  //TODO Potrzebny wyjątek, bo przy wypłacie takiej kwoty jak na koncie a wypłacie większej kwoty zwraca to samo
    }

    @After
    public void tearDown() {
        this.bank = null;
        this.user = null;
    }

}