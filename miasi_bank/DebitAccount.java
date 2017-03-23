package miasi_bank;

import miasi_bank.custom_exceptions.InsufficientBalanceException;
import miasi_bank.custom_exceptions.NegativeValueOfMoneyTransaction;

import java.util.*;

/**
 * Created by TG & MM on 10.03.2017.
 */
public class DebitAccount implements IBankAccount {
    BankAccount bankAccount = null;
    double debit;
    double maxDebit;

    public DebitAccount(BankAccount bankAccount, double maxDebit) {
        this.bankAccount = bankAccount;
        this.maxDebit = maxDebit;
    }

    public String getId() {
        return bankAccount.getId();
    }

    public double getBalance() {
        return bankAccount.getBalance();
    }

    public String addInvestment(double amount, Date closeDate) {
        return bankAccount.addInvestment(amount, closeDate);
    }

    public boolean closeInvestment(String investmentID, Date closeTempDate) {
        return bankAccount.closeInvestment(investmentID, closeTempDate);
    }

    public String takeCredit(double amount) {
        return bankAccount.takeCredit(amount);
    }

    public boolean payOffDebt(String creditID, Date closeTempDate) {
        return bankAccount.payOffDebt(creditID, closeTempDate);
    }

    public void depositCash(double amount) throws NegativeValueOfMoneyTransaction {
        bankAccount.depositCash(amount);
    }

    public double withdrawCash(double amount) throws InsufficientBalanceException, NegativeValueOfMoneyTransaction {
        return bankAccount.withdrawCash(amount);
    }

    public boolean makeTransfer(BankAccount destination, double amount) throws InsufficientBalanceException, NegativeValueOfMoneyTransaction {
        return bankAccount.makeTransfer(destination, amount);
    }
}
