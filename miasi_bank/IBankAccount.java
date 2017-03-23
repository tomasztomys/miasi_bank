package miasi_bank;

import miasi_bank.BankAccount;
import miasi_bank.custom_exceptions.InsufficientBalanceException;
import miasi_bank.custom_exceptions.NegativeValueOfMoneyTransaction;

import java.util.Date;

/**
 * Created by Tomasz Gwoździk on 17.03.2017.
 */
public interface IBankAccount {
    String getId();

    double getBalance();

    String addInvestment(double amount, Date closeDate) throws InsufficientBalanceException, NegativeValueOfMoneyTransaction;

    boolean closeInvestment(String investmentID, Date closeTempDate) ;

    String takeCredit(double amount);

    boolean payOffDebt(String creditID, Date closeTempDate);

    void depositCash(double amount) throws NegativeValueOfMoneyTransaction;

    double withdrawCash(double amount) throws InsufficientBalanceException, NegativeValueOfMoneyTransaction;

    boolean makeTransfer(BankAccount destination, double amount) throws InsufficientBalanceException, NegativeValueOfMoneyTransaction;
}
