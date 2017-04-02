import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashSet;

import static org.junit.Assert.*;

public class HistoryTest {
    History history;

    @Before
    public void setUp() throws Exception {
        history = new History();
    }

    @Test
    public void addOperation() throws Exception {
        Operation operation = new Operation(OperationType.PAYMENT, null, 100.0, null, null);

        history.addOperation(operation);

        assertEquals(1.0, history.getHistory().size(), 0);
    }

    @Test
    public void addOperationOperationType() throws Exception {
        Operation operation = new Operation(OperationType.PAYMENT, null, 100.0, null, null);

        history.addOperation(operation);

        Iterator<Operation> iterator = history.getHistory().iterator();

        assertEquals("PAYMENT", iterator.next().getType().name());
    }

    @Test
    public void getHistory() throws Exception {
        assertNotNull(history.getHistory());
        assertTrue(history.getHistory() instanceof LinkedHashSet);
    }
}