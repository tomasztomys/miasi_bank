package tests;

import miasi_bank.UniqueID;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tomasz Gwoździk on 23.03.2017.
 */
public class UniqueIDTest {
    @Test
    public void generate() throws Exception {
        for (int i = 0; i < 1000; ++i) assertNotEquals(UniqueID.generate(), UniqueID.generate());
    }

}