package miasi_bank.operations;

import custom_exceptions.WrongValueException;
import miasi_bank.Account;
import miasi_bank.Operation;
import miasi_bank.OperationType;

public class Payment implements IOperation {
    private Account account;
    private double amount;
    private Operation operation;

    public Payment(Account account, double amount, Operation operation) {
        this.account = account;
        this.amount = amount;
        this.operation = operation;
    }

    @Override
    public double execute() throws WrongValueException {
        if(this.amount <= 0) {
            throw new WrongValueException("Nieprawidłowa wartość wpłaty na konto " + account.getID() + " o wartości: " + Double.toString(this.amount));
        }

        this.account.__setBalance(this.account.getBalance() + this.amount);

        
        this.account.getHistory().addOperation(new Operation(operation));

        return this.account.getBalance();
    }
}
