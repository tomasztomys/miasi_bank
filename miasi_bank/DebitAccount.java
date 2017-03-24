package miasi_bank;

import custom_exceptions.NoResourcesException;
import custom_exceptions.WrongValueException;

/**
 * Created by Tomasz Gwoździk on 23.03.2017.
 */
public class DebitAccount extends Account {
    private double debit;
    private double maxDebit;

    public DebitAccount(Account account, Operation operation) {
        super(account);
        super.getHistory().addOperation(operation);

        this.maxDebit = operation.getAmount();
        this.debit = operation.getAmount();
    }

    @Override
    public double getTotalBalance() {
        return super.getBalance() + debit;
    }

    @Override
    public double withdraw(Operation operation) throws NoResourcesException, WrongValueException {
        if(operation.getAmount() <= 0) {
            throw new WrongValueException("Nieprawidłowa wartość wpłaty " + getClientID() + " (" + getID() + ") Wpłata: " + operation.getAmount());
        }

        if(getBalance() + debit < operation.getAmount()) {
            throw new NoResourcesException("Nie masz tyle pieniędzy na koncie (clientID: " + getClientID() + ")");
        }

        double withdrawFromMain = getBalance() - operation.getAmount();

        if(withdrawFromMain < 0) {
            super.withdraw(getBalance());
            debit -= -withdrawFromMain;
        } else {
            super.withdraw(withdrawFromMain);
        }

        getHistory().addOperation(new Operation(operation));

        return getBalance() + debit;
    }

    @Override
    public double payment(Operation operation) throws WrongValueException {
        if(operation.getAmount() <= 0) {
            throw new WrongValueException("Nieprawidłowa wartość wpłaty " + getClientID() + " (" + getID() + ") Wpłata: " + operation.getAmount());
        }

        double debetMissing = maxDebit - debit;

        if(operation.getAmount() - debetMissing <= 0) {
            debit += operation.getAmount();
        } else {
            debit += debetMissing;
            super.payment(operation.getAmount() - debetMissing);
        }

        getHistory().addOperation(new Operation(operation));

        return getBalance() + debit;
    }
}
