/**
 * Created by inf117182 on 10.03.2017.
 */
public class BankSystem {

    public static void main(String[] args) {
        Bank bank = new Bank();

        UserAccount user1 = bank.addUserAccount(new UserAccount("Tomek", "Zbyszek", "123"));
        bank.addUserAccount(new UserAccount("Tomek", "Zbyszek", "123"));

        BankAccount user1bankAccount1 = user1.addBankAccount();
    }
}
