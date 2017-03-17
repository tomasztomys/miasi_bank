/**
 * Created by inf117182 on 10.03.2017.
 */
public class BankSystem {

    public static void main(String[] args) {
        Bank bank = new Bank();

        UserAccount user1 = bank.addUserAccount("Tomek", "Zbyszek", "123");
        System.out.println("-----------------------");
        UserAccount user2 = bank.addUserAccount("Tomek", "KKK", "1er");
        System.out.println("-----------------------");

        BankAccount user1bankAccount1 = bank.addBankAccount(user1);
        System.out.println("-----------------------");
        BankAccount user1bankAccount2 = bank.addBankAccount(user1);
        System.out.println("-----------------------");
        BankAccount user2bankAccount1 = bank.addBankAccount(user2);
        System.out.println("-----------------------");

        DebitAccount user1debitAccount1 = bank.addDebit(user1, user1bankAccount2, 5000.0);

        System.out.println("-----------------------");
        user1bankAccount1.depositCash(500.0);
        System.out.println("-----------------------");

        user1bankAccount1.makeTransfer(user2bankAccount1, 800.0);
        System.out.println("-----------------------");
        user1bankAccount1.makeTransfer(user2bankAccount1, -800.0);
        System.out.println("-----------------------");
        user1bankAccount1.makeTransfer(user2bankAccount1, 200.0);
        System.out.println("-----------------------");

        System.out.println("User 2 ma: " + user2bankAccount1.getBalance());
        System.out.println("-----------------------");

        user1bankAccount1.widtdrawCash(800.0);
        System.out.println("-----------------------");
        user1bankAccount1.widtdrawCash(200.0);
        System.out.println("-----------------------");
    }
}
