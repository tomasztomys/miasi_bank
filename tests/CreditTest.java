package tests;

import miasi_bank_old.BankAccount;
import miasi_bank_old.Credit;
import miasi_bank_old.InterestManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by inf117199 on 17.03.2017.
 */
public class CreditTest {
    Credit credit1 = null;
    BankAccount account1 = null;
    InterestManager manager1 = null;

    @Before
    public void init(){
        account1 = new BankAccount();
        manager1 = new InterestManager(5);
        credit1 = new Credit(account1,2000.0,manager1);
    }

    @Test
    public void getId() throws Exception {
        assertNotNull("Id test", credit1.getId());
    }

    @Test
    public void payOffDebtIsActive() throws Exception {
        Double after = credit1.payOffDebt(new Date(new Date().getTime()+(long)(1000*3600*24*10)));
        System.out.println(after);
        System.out.println(new Date(new Date().getTime()+(long)(1000*3600*24*10)).toString());
        assertEquals("Instalment test",202740,Math.round(after*100.0));
    }


    @Test
    public void closeCredit() throws Exception {

    }

    @After
    public void clean(){
        account1 = null;
        manager1 = null;
        credit1 = null;
    }

}