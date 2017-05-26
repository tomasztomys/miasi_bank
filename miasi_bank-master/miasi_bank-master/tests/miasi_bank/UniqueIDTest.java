package miasi_bank;

import org.junit.Test;

import static org.junit.Assert.*;

public class UniqueIDTest {
    @Test
    public void generate() throws Exception {
        for (int i = 0; i < 1000; ++i) assertNotEquals(UniqueID.generate(), UniqueID.generate());
    }

}