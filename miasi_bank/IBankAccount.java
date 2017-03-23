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

    Double getBalance();

    String addInvestment(Double amount, Date closeDate);

    boolean closeInvestment(String investmentID, Date closeTempDate) ;

    String takeCredit(Double amount);

    boolean payOffDebt(String creditID, Date closeTempDate);

    void depositCash(Double amount) throws NegativeValueOfMoneyTransaction;

    Double withdrawCash(Double amount) throws InsufficientBalanceException, NegativeValueOfMoneyTransaction;

    boolean makeTransfer(BankAccount destination, Double amount);
}
