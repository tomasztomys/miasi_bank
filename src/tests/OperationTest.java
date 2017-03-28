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
    Operation operationFromRef;

    @Before
    public void setUp() throws Exception {
        clientID = UniqueID.generate();
        fromID =  UniqueID.generate();
        toID =  UniqueID.generate();
        operation = new Operation(OperationType.PAYMENT, clientID, 100.0, fromID, toID);
        operationFromRef = new Operation(operation);
    }

    @Test
    public void getAmount() throws Exception {
        assertEquals(100.0, operation.getAmount(), 0);
        assertEquals(operation.getAmount(), operationFromRef.getAmount(), 0);
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(operation.getId());
        assertNotNull(operationFromRef.getId());
    }

    @Test
    public void getType() throws Exception {
        assertEquals("PAYMENT", operation.getType().name());
        assertEquals(operation.getType(), operationFromRef.getType());
    }

    @Test
    public void getFrom() throws Exception {
        assertEquals(fromID, operation.getFrom());
        assertEquals(operation.getFrom(), operationFromRef.getFrom());
    }

    @Test
    public void getTo() throws Exception {
        assertEquals(toID, operation.getTo());
        assertEquals(operation.getTo(), operationFromRef.getTo());
    }

    @Test
    public void getDate() throws Exception {
        assertNotNull(operation.getDate());
        assertNotNull(operationFromRef.getDate());
    }

    @Test
    public void getClientID() throws Exception {
        assertEquals(clientID, operation.getClientID());
        assertEquals(operation.getClientID(), operationFromRef.getClientID());
    }

}