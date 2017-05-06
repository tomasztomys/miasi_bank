package miasi_bank;

import custom_exceptions.NoResourcesException;
import custom_exceptions.WrongValueException;
import miasi_bank.interests.IInterest;

/**
 * Created by Tomasz Gwoździk on 05.05.2017.
 */
public interface IAccount {
    double withdraw(Operation operation) throws NoResourcesException, WrongValueException;
    double withdraw(double amount) throws NoResourcesException, WrongValueException;

    double payment(Operation operation) throws WrongValueException;
    double payment(double amount) throws WrongValueException;
    double getTotalBalance();

    String getID();
    String getClientID();

    double getBalance();
    void __setBalance(double balance);

    double calculateInterest();
    void setInterest(IInterest interest, Operation operation);

    History getHistory();
}