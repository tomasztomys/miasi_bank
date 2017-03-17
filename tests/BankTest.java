package tests;

import miasi_bank.BankAccount;
import miasi_bank.DebitAccount;
import org.junit.*;
import miasi_bank.Bank;
import miasi_bank.UserAccount;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Created by inf117199 on 17.03.2017.
 */
public class BankTest {
    private Bank bank;

    @Before
    public void setUp() {
        this.bank = new Bank();
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
        assertThat(user1, instanceOf(UserAccount.class));
        UserAccount user2 = this.bank.addUserAccount("Tomek", "Zbyszek", "123");
        assertNull(user2);
    }

    @Test
    public void addBankAccount() throws Exception {
        UserAccount user1 = this.bank.addUserAccount("Tomek", "Zbyszek", "123");

        BankAccount bankAccount = this.bank.addBankAccount(user1);
        assertNotNull(bankAccount);
        assertEquals(user1.getBankAccounts().size(), 1);
    }

    @Test
    public void addDebit() throws Exception {
        UserAccount user1 = this.bank.addUserAccount("Tomek", "Zbyszek", "123");
        BankAccount bankAccount = this.bank.addBankAccount(user1);

        DebitAccount debitAccount = this.bank.addDebit(user1, bankAccount, 1000.00);
        assertNotNull(debitAccount);
        assertThat(debitAccount, instanceOf(DebitAccount.class));
        assertEquals(this.bank.getBankAccounts().size(), 1);
    }

    @After
    public void tearDown() {
        this.bank = null;
    }
}