package miasi_bank;

import custom_exceptions.*;
import miasi_bank.interests.IInterest;
import miasi_bank.reports.IVisitor;

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

    public double getTotalBalance() {
        return getBalance();
    };

    public void accept(IVisitor visitor)
    {
        visitor.visit(this);
    }
}
