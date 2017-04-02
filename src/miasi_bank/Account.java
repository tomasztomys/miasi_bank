public class Account extends Product {
    public Account(Product account) {
        super(account);
    }

    public Account(String clientID, double startBalance, Interest interest) throws WrongValueException {
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
