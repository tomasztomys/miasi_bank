package tests;

import custom_exceptions.NoResourcesException;
import custom_exceptions.WrongValueException;
import interests.Interest;
import miasi_bank.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by Tomasz Gwoździk on 24.03.2017.
 */
public class DebitAccountTest {
    private Account account;
    private Product product;
    private DebitAccount debitAccount;
    private Interest interest;
    private String clientID;
    private Operation operation;

    @Before
    public void setUp() throws Exception {
        interest = new Interest() {
            @Override
            public double calculate(double amount) {
                return amount * 2;
            }

            @Override
            public double calculate(double amount, Date startDate, Date endDate) {
                return amount * 2;
            }
        };

        clientID = UniqueID.generate();

        product = new Product(clientID, 1000.0, interest);
        account = new Account(product);

        operation = new Operation(OperationType.CREATE_DEBIT, clientID, 5000.0, account.getID(), null);
        debitAccount = new DebitAccount(account, operation);
    }

    @Test
    public void debitCreateOperation() {
        Iterator<Operation> iterator = debitAccount.getHistory().getHistory().iterator();

        assertEquals("CREATE_DEBIT", iterator.next().getType().name());
    }

    @Test
    public void checkID() {
        assertEquals(account.getID(), debitAccount.getID());
    }

    @Test
    public void checkClientID() {
        assertEquals(clientID, debitAccount.getClientID());
    }

    @Test
    public void getTotalBalance() throws Exception {
        assertEquals(6000.0, debitAccount.getTotalBalance(), 0);
    }

    @Test
    public void getTotalBalanceAfterWithDraw() throws Exception {
        operation = new Operation(OperationType.WITHDRAW, clientID, 5500.0, account.getID(), null);
        debitAccount.withdraw(operation);

        assertEquals(500.0, debitAccount.getTotalBalance(), 0);
    }

    @Test (expected = WrongValueException.class)
    public void withdrawNegativeValue() throws Exception {
        operation = new Operation(OperationType.WITHDRAW, clientID, -5500.0, account.getID(), null);
        debitAccount.withdraw(operation);
    }

    @Test (expected = WrongValueException.class)
    public void withdrawZero() throws Exception {
        operation = new Operation(OperationType.WITHDRAW, clientID, 0.0, account.getID(), null);
        debitAccount.withdraw(operation);
    }

    @Test (expected = NoResourcesException.class)
    public void withdrawMoreThanYouHave() throws Exception {
        operation = new Operation(OperationType.WITHDRAW, clientID, 100000.0, account.getID(), null);
        debitAccount.withdraw(operation);
    }

    @Test
    public void withdrawFromAccountNormal() throws Exception {
        operation = new Operation(OperationType.WITHDRAW, clientID, 500.0, account.getID(), null);
        double left = debitAccount.withdraw(operation);

        assertEquals(5500.0, left, 0);
    }

    @Test
    public void withdrawFromAccountAndDebet() throws Exception {
        operation = new Operation(OperationType.WITHDRAW, clientID, 1500.0, account.getID(), null);
        double left = debitAccount.withdraw(operation);

        assertEquals(4500.0, left, 0);
    }

    @Test
    public void withdrawAll() throws Exception {
        operation = new Operation(OperationType.WITHDRAW, clientID, 6000.0, account.getID(), null);
        double left = debitAccount.withdraw(operation);

        assertEquals(0.0, left, 0);
    }

    @Test (expected = WrongValueException.class)
    public void paymentNegativeValue() throws Exception {
        operation = new Operation(OperationType.PAYMENT, clientID, -500.0, account.getID(), null);
        debitAccount.payment(operation);
    }

    @Test (expected = WrongValueException.class)
    public void paymentZeroValue() throws Exception {
        operation = new Operation(OperationType.PAYMENT, clientID, 0.0, account.getID(), null);
        debitAccount.payment(operation);
    }

    @Test
    public void paymentNormalValue() throws Exception {
        operation = new Operation(OperationType.PAYMENT, clientID, 2000.0, account.getID(), null);
        double balance = debitAccount.payment(operation);

        assertEquals(8000.0, balance, 0);
    }

    @Test
    public void paymentWhenOnDebit() throws Exception {
        operation = new Operation(OperationType.WITHDRAW, clientID, 5000.0, account.getID(), null);
        debitAccount.withdraw(operation);

        operation = new Operation(OperationType.PAYMENT, clientID, 2000.0, account.getID(), null);
        double balance = debitAccount.payment(operation);

        assertEquals(3000.0, balance, 0);
        assertEquals(0.0, debitAccount.getBalance(), 0);
    }

    @Test
    public void paymentPayOffDebit() throws Exception {
        operation = new Operation(OperationType.WITHDRAW, clientID, 5000.0, account.getID(), null);
        debitAccount.withdraw(operation);

        operation = new Operation(OperationType.PAYMENT, clientID, 10000.0, account.getID(), null);
        double balance = debitAccount.payment(operation);

        assertEquals(11000.0, balance, 0);
        assertEquals(6000.0, debitAccount.getBalance(), 0);
    }

    @Test
    public void historyAddition() throws Exception {
        operation = new Operation(OperationType.WITHDRAW, clientID, 5000.0, account.getID(), null);
        debitAccount.withdraw(operation);

        Iterator<Operation> iterator = debitAccount.getHistory().getHistory().iterator();
        iterator.next();

        assertEquals(2.0, debitAccount.getHistory().getHistory().size(), 0);
        assertEquals("WITHDRAW", iterator.next().getType().name());

        operation = new Operation(OperationType.PAYMENT, clientID, 10000.0, account.getID(), null);
        double balance = debitAccount.payment(operation);

        iterator = debitAccount.getHistory().getHistory().iterator();
        iterator.next();
        iterator.next();

        assertEquals(3.0, debitAccount.getHistory().getHistory().size(), 0);
        assertEquals("PAYMENT", iterator.next().getType().name());
    }
}