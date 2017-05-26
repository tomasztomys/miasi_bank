package miasi_bank.operations;

import custom_exceptions.WrongValueException;
import miasi_bank.Account;
import miasi_bank.IAccount;
import miasi_bank.Operation;
import miasi_bank.OperationType;

public class Payment implements IOperation {
    private IAccount account;
    private double amount;
    private Operation operation;

    public Payment(IAccount account, double amount, Operation operation) {
        this.account = account;
        this.amount = amount;
        this.operation = operation;
    }

    @Override
    public double execute() throws WrongValueException {
        if(this.amount <= 0) {
            throw new WrongValueException("Nieprawidłowa wartość wpłaty na konto " + account.getID() + " o wartości: " + Double.toString(this.amount));
        }

        this.account.payment(operation);

        
        this.account.getHistory().addOperation(new Operation(operation));

        return this.account.getBalance();
    }
}
