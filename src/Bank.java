import java.util.ArrayList;

/**
 * Created by TG & MM on 10.03.2017.
 */
public class Bank {
    private ArrayList<UserAccount> userAccountList;

    Bank() {
        this.userAccountList = new ArrayList<>();
    }

    void addUserAccount(UserAccount newUserAccount) {
        boolean isAlreadyOnList = false;
        for(UserAccount userAccount : userAccountList) {
            if(userAccount.getPesel().equals(newUserAccount.getPesel())) isAlreadyOnList = true;
        }

        if(isAlreadyOnList) {
            System.out.println("Konto założone na ten PESEL już istnieje.");
        } else {
            userAccountList.add(newUserAccount);
        }
    }

    
}
