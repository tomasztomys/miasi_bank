package tests;

import miasi_bank.Bank;
import miasi_bank.BankAccount;
import miasi_bank.UserAccount;
import miasi_bank.custom_exceptions.InsufficientBalanceException;
import miasi_bank.custom_exceptions.NegativeValueOfMoneyTransaction;
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
    private BankAccount bankAccount;

    @Before
    public void setUp() {
        this.bank = new Bank();
        this.user = bank.addUserAccount("Tomek", "Zbyszek", "123");
        this.bankAccount = bank.addBankAccount(this.user);
    }

    @Test
    public void getId() {
        assertTrue(this.bankAccount.getId().length() > 0);
    }


    @Test
    public void getIdCheckUnique() {
        UserAccount tempUser = bank.addUserAccount("Adam", "Nowak", "123456789");
        BankAccount tempBankAccount = this.bank.addBankAccount(tempUser);

        assertNotEquals(this.bankAccount.getId(), tempBankAccount.getId());
    }

    @Test
    public void getBalanceZeroAfterCreate() {
        assertEquals(this.bankAccount.getBalance(), 0, 0.01);
    }

    @Test
    public void getBalanceAfterDeposit() throws Exception {
        BankAccount bankAccount = bank.addBankAccount(this.user);
        Double amount1 = 100.00;
        bankAccount.depositCash(amount1);
        assertEquals(bankAccount.getBalance(), amount1, 0.01);
    }

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

    @Test(expected= NegativeValueOfMoneyTransaction.class)
    public void depositCashNegativeValue() throws InsufficientBalanceException, NegativeValueOfMoneyTransaction {
        this.bankAccount.depositCash(-500.0);
    }

    @Test(expected= NegativeValueOfMoneyTransaction.class)
    public void withdrawNegativeAmount() throws InsufficientBalanceException, NegativeValueOfMoneyTransaction {
        this.bankAccount.withdrawCash(-500.0);
    }
    @Test
    public void withdrawCash() throws Exception {
        BankAccount bankAccount = bank.addBankAccount(this.user);
        Double amount1 = 100.00;
        Double withdrawAmount = 50.00;
        bankAccount.depositCash(amount1);
        bankAccount.withdrawCash(withdrawAmount);
        assertEquals(bankAccount.getBalance(), amount1 - withdrawAmount, 0.01  );
    }

    @Test
    public void withdrawAllCash() throws Exception {
        BankAccount bankAccount = bank.addBankAccount(this.user);
        Double amount1 = 100.00;
        bankAccount.depositCash(amount1);
        bankAccount.withdrawCash(amount1);
        assertEquals(bankAccount.getBalance(), 0, 0.01  );
    }

    @Test(expected= InsufficientBalanceException.class)
    public void withdrawCrashMoreOfBalance() throws Exception {
        BankAccount bankAccount = bank.addBankAccount(this.user);
        Double amount1 = 100.00;
        Double withdrawAmount = 500.00;
        bankAccount.depositCash(amount1);
        bankAccount.withdrawCash(withdrawAmount);
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


    @After
    public void tearDown() {
        this.bank = null;
        this.user = null;
    }

}