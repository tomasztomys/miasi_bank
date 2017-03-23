package tests;

import miasi_bank.*;
import miasi_bank.custom_exceptions.InsufficientBalanceException;
import miasi_bank.custom_exceptions.NegativeValueOfMoneyTransactionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.NoSuchElementException;

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
        UserAccount tempUser = this.bank.addUserAccount("Adam", "Nowak", "123456789");
        BankAccount tempBankAccount = this.bank.addBankAccount(tempUser);

        assertNotEquals(this.bankAccount.getId(), tempBankAccount.getId());
    }

    @Test
    public void getBalanceZeroAfterCreate() {
        assertEquals(this.bankAccount.getBalance(), 0, 0.01);
    }

    @Test
    public void getBalanceAfterDeposit() throws Exception {
        double amount1 = 100.00;
        this.bankAccount.depositCash(amount1);
        assertEquals(this.bankAccount.getBalance(), amount1, 0.01);
    }

    @Test
    public void depositCash() throws Exception {
        double amount1 = 100.00;
        this.bankAccount.depositCash(amount1);
        assertEquals(this.bankAccount.getBalance(), amount1, 0);

        double amount2 = 50.00;
        this.bankAccount.depositCash(amount2);
        assertEquals(this.bankAccount.getBalance(), amount1 + amount2, 0);
    }

    @Test(expected= NegativeValueOfMoneyTransactionException.class)
    public void depositCashNegativeValue() throws InsufficientBalanceException, NegativeValueOfMoneyTransactionException {
        this.bankAccount.depositCash(-500.0);
    }

    @Test(expected= NegativeValueOfMoneyTransactionException.class)
    public void withdrawNegativeAmount() throws InsufficientBalanceException, NegativeValueOfMoneyTransactionException {
        this.bankAccount.withdrawCash(-500.0);
    }
    @Test
    public void withdrawCash() throws Exception {
        double amount1 = 100.00;
        double withdrawAmount = 50.00;
        this.bankAccount.depositCash(amount1);
        this.bankAccount.withdrawCash(withdrawAmount);
        assertEquals(this.bankAccount.getBalance(), amount1 - withdrawAmount, 0.01  );
    }

    @Test
    public void withdrawAllCash() throws Exception {
        double amount1 = 100.00;
        this.bankAccount.depositCash(amount1);
        this.bankAccount.withdrawCash(amount1);
        assertEquals(this.bankAccount.getBalance(), 0, 0.01  );
    }

    @Test(expected= InsufficientBalanceException.class)
    public void withdrawCrashMoreOfBalance() throws Exception {
        double amount1 = 100.00;
        double withdrawAmount = 500.00;
        this.bankAccount.depositCash(amount1);
        this.bankAccount.withdrawCash(withdrawAmount);
    }

    @Test
    public void makeTransfer() throws NegativeValueOfMoneyTransactionException, InsufficientBalanceException {
        UserAccount userAccountTemp = this.bank.addUserAccount("Test", "test", "98052007457");
        BankAccount bankAccountTemp = this.bank.addBankAccount(userAccountTemp);
        double depositAmount = 500.00;
        double transferAmount = 200.00;
        this.bankAccount.depositCash(depositAmount);

        this.bankAccount.makeTransfer(bankAccountTemp, transferAmount);
        assertEquals(this.bankAccount.getBalance(), depositAmount - transferAmount, 0.01);
        assertEquals(bankAccountTemp.getBalance(), transferAmount);
    }

    @Test(expected = InsufficientBalanceException.class)
    public void makeTransferMoreOfBalance() throws NegativeValueOfMoneyTransactionException, InsufficientBalanceException {
        UserAccount userAccountTemp = this.bank.addUserAccount("Test", "test", "98052007457");
        BankAccount bankAccountTemp = this.bank.addBankAccount(userAccountTemp);
        double depositAmount = 500.00;
        double transferAmount = 700.00;
        this.bankAccount.depositCash(depositAmount);

        this.bankAccount.makeTransfer(bankAccountTemp, transferAmount);
    }

    @Test(expected = NegativeValueOfMoneyTransactionException.class)
    public void makeTransferNegativeValue() throws NegativeValueOfMoneyTransactionException, InsufficientBalanceException {
        UserAccount userAccountTemp = this.bank.addUserAccount("Test", "test", "98052007457");
        BankAccount bankAccountTemp = this.bank.addBankAccount(userAccountTemp);
        double depositAmount = 500.00;
        double transferAmount = -700.00;
        this.bankAccount.depositCash(depositAmount);

        this.bankAccount.makeTransfer(bankAccountTemp, transferAmount);
    }

    @Test
    public void addInvestment() throws NegativeValueOfMoneyTransactionException, InsufficientBalanceException {
        double amount = 500.00;
        double investmentAmount = 200.00;
        this.bankAccount.depositCash(amount);
        String investmentIdTemp = this.bankAccount.addInvestment(investmentAmount, new Date(2018, 5, 15));
        assertEquals(this.bankAccount.getBalance(), amount - investmentAmount, 0.01);

        Investment investmentTemp = this.bankAccount.getInvestmentById(investmentIdTemp);
        assertEquals(investmentTemp.getDeposit(), investmentAmount, 0.01);
    }

    @Test
    public void getInvestmentById() throws NegativeValueOfMoneyTransactionException, InsufficientBalanceException {
        double amount = 500.00;
        double investmentAmount = 200.00;
        this.bankAccount.depositCash(amount);
        String investmentIdTemp = this.bankAccount.addInvestment(investmentAmount, new Date(2018, 5, 15));

        Investment investmentTemp = this.bankAccount.getInvestmentById(investmentIdTemp);
        assertEquals(investmentTemp.getId(), investmentIdTemp);
    }

    @Test(expected = NoSuchElementException.class)
    public void getWrongInvestmentId() throws NegativeValueOfMoneyTransactionException, InsufficientBalanceException {
        double amount = 500.00;
        double investmentAmount = 200.00;
        this.bankAccount.depositCash(amount);
        String investmentIdTemp = this.bankAccount.addInvestment(investmentAmount, new Date(2018, 5, 15));

        Investment investmentTemp = this.bankAccount.getInvestmentById("TEST");
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
    @Test
    public void takeCredit() throws Exception {
        double amount = 5000.0;
        String creditIdTemp = this.bankAccount.takeCredit(amount);

        assertEquals(this.bankAccount.getBalance(), amount, 0.01);

        //TODO nie wiem czy to powinno byc, bo chyba nie powinnismy miec dostepu do pobierania tego obiektu
        assertEquals(this.bankAccount.getCreditById(creditIdTemp).getAmount(), amount, 0.01);
    }

    @Test(expected = NegativeValueOfMoneyTransactionException.class)
    public void takeCreditNegativeValue() throws NegativeValueOfMoneyTransactionException {
        double amount = -5000.0;
        String creditIdTemp = this.bankAccount.takeCredit(amount);
    }

    @Test
    public void payOffDebt() throws Exception {
        double depositOnAccount = 1000;
        double amount = 500.0;
        double percentage = BankAccount.InterestPercentage;

        this.bankAccount.depositCash(depositOnAccount);
        String creditIdTemp = this.bankAccount.takeCredit(amount);
        this.bankAccount.payOffDebt(creditIdTemp, new Date());

        //TODO nie wiem jak to zrobic aby sprawdzic czy mamy prawidlowa kwote
//        assertEquals(this.bankAccount.getBalance(), depositOnAccount );
    }


    @After
    public void tearDown() {
        this.bank = null;
        this.user = null;
    }

}