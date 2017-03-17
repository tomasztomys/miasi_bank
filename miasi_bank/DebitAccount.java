package miasi_bank;

import java.util.*;

/**
 * Created by TG & MM on 10.03.2017.
 */
public class DebitAccount implements IBankAccount {
    BankAccount bankAccount = null;
    Double debit;
    Double maxDebit;

    public DebitAccount(BankAccount bankAccount, Double maxDebit) {
        this.bankAccount = bankAccount;
        this.maxDebit = maxDebit;
    }

    public String getId() {
        return bankAccount.getId();
    }

    public Double getBalance() {
        return bankAccount.getBalance();
    }

    public String addInvestment(Double amount, Date closeDate) {
        return bankAccount.addInvestment(amount, closeDate);
    }

    public boolean closeInvestment(String investmentID, Date closeTempDate) {
        return bankAccount.closeInvestment(investmentID, closeTempDate);
    }

    public String takeCredit(Double amount) {
        return bankAccount.takeCredit(amount);
    }

    public boolean payOffDebt(String creditID, Date closeTempDate) {
        return bankAccount.payOffDebt(creditID, closeTempDate);
    }

    public void depositCash(Double amount) {
        bankAccount.depositCash(amount);
    }

    public Double withdrawCash(Double amount) {
        return bankAccount.withdrawCash(amount);
    }

    public boolean makeTransfer(BankAccount destination, Double amount) {
        return bankAccount.makeTransfer(destination, amount);
    }
}
