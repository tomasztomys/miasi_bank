package miasi_bank;

import miasi_bank.BankAccount;

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

    void depositCash(Double amount);

    Double widtdrawCash(Double amount);

    boolean makeTransfer(BankAccount destination, Double amount);
}
