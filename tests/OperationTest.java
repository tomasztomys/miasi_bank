package tests;

import miasi_bank.Operation;
import miasi_bank.OperationType;
import miasi_bank.UniqueID;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tomasz Gwoździk on 23.03.2017.
 */
public class OperationTest {
    String clientID;
    String fromID;
    String toID;
    Operation operation;

    @Before
    public void setUp() throws Exception {
        clientID = UniqueID.generate();
        fromID =  UniqueID.generate();
        toID =  UniqueID.generate();
        operation = new Operation(OperationType.PAYMENT, clientID, 100.0, fromID, toID);
    }

    @Test
    public void getAmount() throws Exception {
        assertEquals(100.0, operation.getAmount(), 0);
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(operation.getId());
    }

    @Test
    public void getType() throws Exception {
        assertEquals("PAYMENT", operation.getType().name());
    }

    @Test
    public void getFrom() throws Exception {
        assertEquals(fromID, operation.getFrom());
    }

    @Test
    public void getTo() throws Exception {
        assertEquals(toID, operation.getTo());
    }

    @Test
    public void getDate() throws Exception {
        assertNotNull(operation.getDate());
    }

    @Test
    public void getClientID() throws Exception {
        assertEquals(clientID, operation.getClientID());
    }

}