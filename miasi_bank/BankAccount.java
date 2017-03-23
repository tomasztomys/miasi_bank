package miasi_bank;

import miasi_bank.custom_exceptions.InsufficientBalanceException;
import miasi_bank.custom_exceptions.NegativeValueOfMoneyTransactionException;

import java.util.*;

/**
 * Created by inf117182 on 10.03.2017.
 */
public class BankAccount extends BankProduct implements IBankAccount {
    private String Id;
    private double Balance;
    private Set<Investment> InvestmentList;
    private Set<Credit> CreditList;
    public static double InterestPercentage = 5.0;

    public BankAccount() {
        super();

        this.Id = RandomString.get();
        this.Balance = 0.0;
        this.InvestmentList = new HashSet<>();
        this.CreditList = new HashSet<>();

    }

    public String getId() {
        return Id;
    }

    public double getBalance() {
        return Balance;
    }

    public Set<Investment> getInvestmentList() {
        return InvestmentList;
    }

    public Investment getInvestmentById(String id) throws NoSuchElementException {
        return this.InvestmentList.stream().filter(s -> s.getId().equals(id)).findFirst().get();
    }

    public Credit getCreditById(String id) throws NoSuchElementException {
        return this.CreditList.stream().filter(s -> s.getId().equals(id)).findFirst().get();
    }

    public Set<Credit> getCreditList() {
        return CreditList;
    }

    private void validateAmount(double amount) throws NegativeValueOfMoneyTransactionException {
        if(amount <= 0) {
            throw new NegativeValueOfMoneyTransactionException();
        }
    }

    private void checkSufficientAmountInBalance(double amount) throws InsufficientBalanceException, NegativeValueOfMoneyTransactionException {
        this.validateAmount(amount);
        if(amount > this.Balance) {
            throw new InsufficientBalanceException();
        }
    }

    private void deposit(double amount) throws NegativeValueOfMoneyTransactionException {
        this.validateAmount(amount);

        this.Balance += amount;
    }

    private void withdraw(double amount) throws NegativeValueOfMoneyTransactionException, InsufficientBalanceException {
        this.validateAmount(amount);
        this.checkSufficientAmountInBalance(amount);

        this.Balance -=amount;

    }

    public String addInvestment(double amount, Date closeDate) throws InsufficientBalanceException, NegativeValueOfMoneyTransactionException {
        this.withdraw(amount);

        Investment investment = new Investment(this, amount, closeDate, new InterestManager(5));
        Operation operation = new Operation(OperationType.OPEN_DEPOSIT, this, investment, amount);
        this.historyManager.addOperation(operation);

        this.InvestmentList.add(investment);

        return investment.getId();
    }

    public boolean closeInvestment(String investmentID, Date closeTempDate) {
        Investment investment = null;

        for (Investment tempInvestment: InvestmentList) {
            if(tempInvestment.getId().equals(investmentID)) investment = tempInvestment;
        }

        if(investment != null) {
            double amount = investment.closeInvestment(closeTempDate);
            this.Balance += amount;

            if(amount > investment.getDeposit()) {
                Operation operation = new Operation(OperationType.CLOSE_DEPOSIT, investment, this, amount);
                this.historyManager.addOperation(operation);
            } else {
                Operation operation = new Operation(OperationType.BREAK_DEPOSIT, investment, this, amount);
                this.historyManager.addOperation(operation);
            }

            investment.disableInvestment(amount);

            return true;
        } else {
            return false;
        }
    }

    public String takeCredit(double amount) throws NegativeValueOfMoneyTransactionException {
        this.validateAmount(amount);

        Credit credit = new Credit(this, amount, new InterestManager(InterestPercentage));
        Operation operation = new Operation(OperationType.TAKE_CREDIT, this, credit, amount);

        this.deposit(amount);

        this.historyManager.addOperation(operation);
        this.CreditList.add(credit);

        return credit.getId();
    }

    public boolean payOffDebt(String creditID, Date closeTempDate) {
        Credit credit = null;

        for (Credit tempCredit: CreditList) {
            if(tempCredit.getId().equals(creditID)) credit = tempCredit;
        }

        if(credit != null) {
            double amount = credit.payOffDebt(closeTempDate);

            if(this.Balance >= amount && amount > 0) {
                this.Balance -= amount;

                Operation operation = new Operation(OperationType.PAY_OFF_DEBT, this, credit, amount);
                this.historyManager.addOperation(operation);

                return true;
            }
        } else {
            return false;
        }

        return false;
    }

    public void depositCash(double amount) throws NegativeValueOfMoneyTransactionException {
        deposit(amount);
        Operation operation = new Operation(OperationType.DEPOSIT, null, this, amount);
        this.historyManager.addOperation(operation);
//
//        System.out.println("Dodano " + amount + " do konta " + this.getId() + " Razem: " + this.getBalance());
    }

    public double withdrawCash(double amount) throws InsufficientBalanceException, NegativeValueOfMoneyTransactionException {
        this.withdraw(amount);
        Operation operation = new Operation(OperationType.WITHDRAW, this, null, amount);
        this.historyManager.addOperation(operation);
//
//        System.out.println("Wypłacono: " + amount + " z konta " + this.getId());
//
        return amount;

    }

    public boolean makeTransfer(BankAccount destination, double amount) throws InsufficientBalanceException, NegativeValueOfMoneyTransactionException {
        this.withdraw(amount);
        destination.deposit(amount);
        Operation operation = new Operation(OperationType.TRANSFER, this, destination, amount);
        this.historyManager.addOperation(operation);
//
//        System.out.println("Wykonano przelew: " + amount + " do konta " + destination.getId() + " z konta: " + this.getId() + " Razem: " + this.getBalance());

        return true;
    }


}
