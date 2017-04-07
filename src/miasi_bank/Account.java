package miasi_bank;

import custom_exceptions.*;
import miasi_bank.interests.IInterest;

public class Account extends Product {
    public Account(Product account) {
        super(account);
    }

    public Account(String clientID, double startBalance, IInterest interest) throws WrongValueException {
        super(clientID, startBalance, interest);
    }

    public Account(String clientID, IInterest interest) {
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
