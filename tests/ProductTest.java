package tests;

import custom_exceptions.NoResourcesException;
import custom_exceptions.WrongValueException;
import interests.Interest;
import miasi_bank.Operation;
import miasi_bank.OperationType;
import miasi_bank.Product;
import miasi_bank.UniqueID;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Tomasz Gwoździk on 23.03.2017.
 */
public class ProductTest {
    private Interest interest1;
    private Interest interest2;
    private Product product;
    private Product productFromRef;
    private Product productWithStartBalance;
    private String clientID;

    @Before
    public void setUp() throws Exception {
        interest1 = new Interest() {
            @Override
            public double calculate(double amount) {
                return amount * 2;
            }

            @Override
            public double calculate(double amount, Date startDate, Date endDate) {
                return amount * 2;
            }
        };

        interest2 = new Interest() {
            @Override
            public double calculate(double amount) {
                return amount * 4;
            }

            @Override
            public double calculate(double amount, Date startDate, Date endDate) {
                return amount * 4;
            }
        };

        clientID = UniqueID.generate();

        product = new Product(clientID, interest1);
        productWithStartBalance = new Product(clientID, 1000.0, interest1);
        productFromRef = new Product(productWithStartBalance);
    }

    @Test
    public void getInterest() throws Exception {
        assertEquals(interest1, product.getInterest());
        assertEquals(interest1, productWithStartBalance.getInterest());
        assertEquals(productWithStartBalance.getInterest(), productFromRef.getInterest());
    }

    @Test
    public void setInterest() throws Exception {
        product.setInterest(interest2, new Operation(OperationType.CHANGE_INTEREST_MECHANISM, null, 0, null, null));
        assertEquals(interest2, product.getInterest());

        productWithStartBalance.setInterest(interest2, new Operation(OperationType.CHANGE_INTEREST_MECHANISM, null, 0, null, null));
        assertEquals(interest2, productWithStartBalance.getInterest());

        assertNotEquals(productWithStartBalance.getInterest(), productFromRef.getInterest());
    }

    @Test
    public void paymentOperation() throws Exception {
        Operation operation = new Operation(OperationType.PAYMENT, null, 0.1, null, null);

        productWithStartBalance.payment(operation);

        assertEquals(1000.1, productWithStartBalance.getBalance(), 0);
    }

    @Test (expected=WrongValueException.class)
    public void paymentNegativeOperation() throws Exception {
        Operation operation = new Operation(OperationType.PAYMENT, null, -1000.0, null, null);

        productWithStartBalance.payment(operation);
    }

    @Test (expected=WrongValueException.class)
    public void paymentZeroOperation() throws Exception {
        Operation operation = new Operation(OperationType.PAYMENT, null, 0, null, null);

        productWithStartBalance.payment(operation);
    }

    @Test
    public void paymentAmount() throws Exception {
        product.payment(0.1);

        assertEquals(0.1, product.getBalance(), 0);
    }

    @Test (expected=WrongValueException.class)
    public void paymentNegativeAmount() throws Exception {
        product.payment(-1000.0);
    }

    @Test (expected=WrongValueException.class)
    public void paymentZeroAmount() throws Exception {
        product.payment(0.0);
    }

    @Test
    public void withdrawOperation() throws Exception {
        Operation operation = new Operation(OperationType.PAYMENT, null, 100.0, null, null);

        double balance = productWithStartBalance.withdraw(operation);

        assertEquals(900.0, balance, 0);
    }

    @Test (expected=NoResourcesException.class)
    public void withdrawNoMoneyOperation() throws Exception {
        Operation operation = new Operation(OperationType.PAYMENT, null, 100000.0, null, null);

        product.withdraw(operation);
    }

    @Test (expected=WrongValueException.class)
    public void withdrawNegativeOperation() throws Exception {
        Operation operation = new Operation(OperationType.PAYMENT, null, -100.0, null, null);

        product.withdraw(operation);
    }

    @Test (expected=WrongValueException.class)
    public void withdrawZeroOperation() throws Exception {
        Operation operation = new Operation(OperationType.PAYMENT, null, 0.0, null, null);

        product.withdraw(operation);
    }

    @Test
    public void withdrawAmount() throws Exception {
        double balance = productWithStartBalance.withdraw(100.0);
        assertEquals(900.0, balance, 0);
    }

    @Test (expected=NoResourcesException.class)
    public void withdrawNoMoneyAmount() throws Exception {
        productWithStartBalance.withdraw(100000.0);
    }

    @Test (expected=WrongValueException.class)
    public void withdrawNegativeAmount() throws Exception {
        productWithStartBalance.withdraw(-100.0);
    }

    @Test (expected=WrongValueException.class)
    public void withdrawZeroAmount() throws Exception {
        productWithStartBalance.withdraw(0.0);
    }

    @Test
    public void getID() throws Exception {
        assertNotNull(productWithStartBalance.getID());
        assertNotNull(product.getID());
        assertNotNull(productFromRef.getID());
    }

    @Test
    public void getClientID() throws Exception {
        assertEquals(clientID, product.getClientID());
        assertEquals(clientID, productWithStartBalance.getClientID());
        assertEquals(clientID, productFromRef.getClientID());
    }

    @Test
    public void getBalance() throws Exception {
        assertEquals(0.0, product.getBalance(), 0);
        assertEquals(1000.0, productWithStartBalance.getBalance(), 0);
        assertEquals(1000.0, productFromRef.getBalance(), 0);
    }

    @Test
    public void getCreationDate() throws Exception {
        assertNotNull(productWithStartBalance.getCreationDate());
        assertNotNull(product.getCreationDate());
        assertNotNull(productFromRef.getCreationDate());
    }

    @Test
    public void getHistory() throws Exception {
        assertNotNull(productWithStartBalance.getHistory());
        assertNotNull(product.getHistory());
        assertNotNull(productFromRef.getHistory());
    }

    @Test
    public void getHistoryOperationWithdraw() throws Exception {
        Operation operation = new Operation(OperationType.WITHDRAW, null, 100.0, null, null);

        productWithStartBalance.withdraw(operation);

        assertEquals(1, productWithStartBalance.getHistory().getHistory().size(), 0);
    }

    @Test (expected=WrongValueException.class)
    public void getHistoryOperationWithdrawZero() throws Exception {
        Operation operation = new Operation(OperationType.WITHDRAW, null, 0.0, null, null);

        productWithStartBalance.withdraw(operation);

        assertEquals(0, productWithStartBalance.getHistory().getHistory().size(), 0);
    }

    @Test (expected=WrongValueException.class)
    public void getHistoryOperationWithdrawNegative() throws Exception {
        Operation operation = new Operation(OperationType.WITHDRAW, null, -100.0, null, null);

        productWithStartBalance.withdraw(operation);

        assertEquals(0, productWithStartBalance.getHistory().getHistory().size(), 0);
    }

    @Test
    public void getHistoryOperationPayment() throws Exception {
        Operation operation = new Operation(OperationType.PAYMENT, null, 100.0, null, null);

        productWithStartBalance.payment(operation);

        assertEquals(1.0, productWithStartBalance.getHistory().getHistory().size(), 0);
    }

    @Test (expected=WrongValueException.class)
    public void getHistoryOperationPaymentZero() throws Exception {
        Operation operation = new Operation(OperationType.PAYMENT, null, 0.0, null, null);

        productWithStartBalance.payment(operation);

        assertEquals(0.0, productWithStartBalance.getHistory().getHistory().size(), 0);
    }

    @Test (expected=WrongValueException.class)
    public void getHistoryOperationPaymentNegative() throws Exception {
        Operation operation = new Operation(OperationType.PAYMENT, null, -100.0, null, null);

        productWithStartBalance.payment(operation);

        assertEquals(0.0, productWithStartBalance.getHistory().getHistory().size(), 0);
    }

    @Test
    public void getHistoryOperationPaymentAmount() throws Exception {
        productWithStartBalance.payment(100.0);

        assertEquals(0.0, productWithStartBalance.getHistory().getHistory().size(), 0);
    }

    @Test
    public void getHistoryOperationWithdrawAmount() throws Exception {
        productWithStartBalance.withdraw(100.0);

        assertEquals(0.0, productWithStartBalance.getHistory().getHistory().size(), 0);
    }
}