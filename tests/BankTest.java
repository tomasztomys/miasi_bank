package tests;

import org.junit.*;
import miasi_bank.Bank;
import miasi_bank.UserAccount;

import static org.junit.Assert.*;

/**
 * Created by inf117199 on 17.03.2017.
 */
public class BankTest {

    private Bank bank;

    @Before
    public void setUp() {
        this.bank = new Bank();
        System.out.println("now");
    }

    @Test
    public void addUserAccount() throws Exception {


        UserAccount user1 = this.bank.addUserAccount("Tomek", "Zbyszek", "123");
        assertNotNull(user1);
    }

    @Test
    public void addUserAccountWithThisSamePesel() throws Exception {
        UserAccount user1 = this.bank.addUserAccount("Tomek", "Zbyszek", "123");
        assertNotNull(user1);
        UserAccount user2 = this.bank.addUserAccount("Tomek", "Zbyszek", "123");
        assertNull(user2);
    }

    @Test
    public void removeUserAccount() throws Exception {
        UserAccount user1 = this.bank.addUserAccount("Tomek", "Zbyszek", "123");
        assertNotNull(user1);
        UserAccount user2 = this.bank.addUserAccount("Tomek", "Zbyszek", "123");
        assertNull(user2);
    }

    @Test
    public void addBankAccount() throws Exception {

    }

    @Test
    public void addDebit() throws Exception {

    }

}