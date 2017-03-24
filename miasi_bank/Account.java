package miasi_bank;

import interests.Interest;

/**
 * Created by Tomasz Gwoździk on 22.03.2017.
 */
public class Account extends Product {
    public Account(Product account) {
        super(account);
    }

    public Account(String clientID, double startBalance, Interest interest) {
        super(clientID, startBalance, interest);
    }

    public Account(String clientID, Interest interest) {
        super(clientID, interest);
    }

    public double calculateInterest() {
        double interest = getInterest().calculate(getBalance());

        return interest;
    }

    public double getTotalBalance() {
        return getBalance();
    };
}
