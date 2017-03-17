package miasi_bank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by TG & MM on 10.03.2017.
 */
public class Bank {
    private Set<UserAccount> userAccountList;
    private Set<IBankAccount> bankAccounts;

    Bank() {
        this.userAccountList = new HashSet<>();
        this.bankAccounts = new HashSet<>();
    }

    public UserAccount addUserAccount(String name, String surname, String pesel) {
        boolean isAlreadyOnList = false;
        UserAccount newUserAccount = new UserAccount(name, surname, pesel);

        for(UserAccount userAccount : userAccountList) {
            if(userAccount.getPesel().equals(newUserAccount.getPesel())) isAlreadyOnList = true;
        }

        if(isAlreadyOnList) {
            System.out.println("Konto założone na ten PESEL już istnieje.");
            return null;
        } else {
            userAccountList.add(newUserAccount);
            System.out.println("Konto użytkownika " +  newUserAccount.getPesel() + " zostało utworzone");
            return newUserAccount;
        }
    }

    public boolean removeUserAccount(UserAccount newUserAccount) {
        return userAccountList.remove(newUserAccount);
    }

    public BankAccount addBankAccount(UserAccount user) {
        BankAccount bankAccount = new BankAccount();

        this.bankAccounts.add(bankAccount);
        user.addBankAccount(bankAccount);

        System.out.println("Konto bankowe " +  bankAccount.getId() + " zostało utworzone dla użytkownika o numerze PESEL: " + user.getPesel());
        return bankAccount;
    }

    public DebitAccount addDebit(UserAccount user1, BankAccount bankAccount, Double maxDebit) {
        bankAccounts.remove(bankAccount);

        DebitAccount newBankAccount = new DebitAccount(bankAccount, maxDebit);

        bankAccounts.add(newBankAccount);
        user1.addDebit(bankAccount, newBankAccount);

        return newBankAccount;
    }
}
