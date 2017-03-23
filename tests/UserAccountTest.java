package tests;

import miasi_bank_old.BankAccount;
import miasi_bank_old.UserAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by inf117199 on 17.03.2017.
 */
public class UserAccountTest {

    UserAccount user1 = null;

    @Before
    public void init(){
        user1 = new UserAccount("Tomek","Fran","1111");
    }

    @Test
    public void getName() throws Exception {
        assertEquals("Name test",user1.getName(),"Tomek");
    }

    @Test
    public void getSurname() throws Exception {
        assertEquals("Surname test",user1.getSurname(),"Fran");
    }

    @Test
    public void setSurname() throws Exception {
        user1.setSurname("Nowak");
        assertEquals("Setting surname test",user1.getSurname(),"Nowak");
    }

    @Test
    public void getPesel() throws Exception {
        assertEquals("Pesel test",user1.getPesel(),"1111");
    }

    @Test
    public void getCreatedDate() throws Exception {
        assertNotNull("Date test",user1.getCreatedDate());
    }

    @Test
    public void addBankAccount() throws Exception {
        BankAccount account = new BankAccount();
        user1.addBankAccount(account);
        assertTrue("Adding accounts test",user1.getBankAccounts().contains(account));
    }

    @After
    public void clean(){
        user1=null;
    }
}