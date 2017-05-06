package miasi_bank;

import custom_exceptions.NoResourcesException;
import custom_exceptions.WrongValueException;
import miasi_bank.interests.IInterest;

public class DebitAccount implements IAccount {
    private double debit;
    private double maxDebit;

    protected IAccount account;

    public DebitAccount(IAccount account, Operation operation) {
        this.account = account;
        this.maxDebit = operation.getAmount();
        this.debit = operation.getAmount();

        account.getHistory().addOperation(operation);
    }

    public String getID() {
        return account.getID();
    }

    public String getClientID() {
        return account.getClientID();
    }

    public double getBalance() {
        return account.getBalance();
    }

    public void __setBalance(double balance) {
        account.__setBalance(balance);
    }

    public History getHistory() {
        return account.getHistory();
    }

    public double getTotalBalance() {
        return account.getBalance() + debit;
    }

    public double withdraw(Operation operation) throws NoResourcesException, WrongValueException {
        if(operation.getAmount() <= 0) {
            throw new WrongValueException("Nieprawidłowa wartość wpłaty " + account.getClientID() + " (" + account.getID() + ") Wpłata: " + operation.getAmount());
        }

        if(account.getBalance() + debit < operation.getAmount()) {
            throw new NoResourcesException("Nie masz tyle pieniędzy na koncie (clientID: " + account.getClientID() + ")");
        }

        double withdrawFromMain = account.getBalance() - operation.getAmount();

        if(withdrawFromMain < 0) {
            if(account.getBalance() != 0.0) account.withdraw(account.getBalance());
            debit -= -withdrawFromMain;
        } else {
            account.withdraw(operation.getAmount());
        }

        account.getHistory().addOperation(new Operation(operation));

        return account.getBalance() + debit;
    }

    public double withdraw(double amount) throws NoResourcesException, WrongValueException {
        return account.withdraw(amount);
    }

    public double calculateInterest() {
        return account.calculateInterest();
    }

    public void setInterest(IInterest interest, Operation operation) {
        account.setInterest(interest, operation);
    }

    public double payment(Operation operation) throws WrongValueException {
        if(operation.getAmount() <= 0) {
            throw new WrongValueException("Nieprawidłowa wartość wpłaty " + account.getClientID() + " (" + account.getID() + ") Wpłata: " + operation.getAmount());
        }

        double debetMissing = maxDebit - debit;

        if(operation.getAmount() - debetMissing <= 0) {
            debit += operation.getAmount();
        } else {
            debit += debetMissing;
            account.payment(operation.getAmount() - debetMissing);
        }

        account.getHistory().addOperation(new Operation(operation));

        return account.getBalance() + debit;
    }

    public double payment(double amount) throws WrongValueException {
        return account.payment(amount);
    }
}
