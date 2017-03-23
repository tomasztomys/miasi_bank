package miasi_bank;

import miasi_bank.custom_exceptions.InsufficientBalanceException;
import miasi_bank.custom_exceptions.NegativeValueOfMoneyTransactionException;

import java.util.Date;

/**
 * Created by Tomasz Gwoździk on 17.03.2017.
 */
public interface IBankAccount {
    String getId();

    double getBalance();

    String addInvestment(double amount, Date closeDate) throws InsufficientBalanceException, NegativeValueOfMoneyTransactionException;

    boolean closeInvestment(String investmentID, Date closeTempDate) ;

    String takeCredit(double amount);

    boolean payOffDebt(String creditID, Date closeTempDate);

    void depositCash(double amount) throws NegativeValueOfMoneyTransactionException;

    double withdrawCash(double amount) throws InsufficientBalanceException, NegativeValueOfMoneyTransactionException;

    boolean makeTransfer(BankAccount destination, double amount) throws InsufficientBalanceException, NegativeValueOfMoneyTransactionException;
}
