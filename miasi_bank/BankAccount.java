package miasi_bank;

import java.util.*;

/**
 * Created by inf117182 on 10.03.2017.
 */
public class BankAccount extends BankProduct implements IBankAccount {
    private String Id;
    private Double Balance;
    private Set<Investment> InvestmentList;
    private Set<Credit> CreditList;

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

    public Double getBalance() {
        return Balance;
    }

    public Set<Investment> getInvestmentList() {
        return InvestmentList;
    }

    public Set<Credit> getCreditList() {
        return CreditList;
    }

    public String addInvestment(Double amount, Date closeDate) {
        if(this.Balance < amount) {
            return null;
        }

        this.Balance -= amount;

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
            Double amount = investment.closeInvestment(closeTempDate);
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

    public String takeCredit(Double amount) {
        if(amount <= 0) return null;

        this.Balance += amount;

        Credit credit = new Credit(this, amount, new InterestManager(5));

        Operation operation = new Operation(OperationType.TAKE_CREDIT, this, credit, amount);
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
            Double amount = credit.payOffDebt(closeTempDate);

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

    private void deposit(Double amount) {
        if(amount <= 0) return;

        this.Balance += amount;
    }

    public void depositCash(Double amount) {
        if(amount <= 0) return;

        deposit(amount);

        Operation operation = new Operation(OperationType.DEPOSIT, null, this, amount);
        this.historyManager.addOperation(operation);

        System.out.println("Dodano " + amount + " do konta " + this.getId() + " Razem: " + this.getBalance());
    }

    public Double widtdrawCash(Double amount) {
        if(amount > this.Balance || amount <= 0) {
            System.out.println("Błędna kwota przelewu lub brak środków na koncie");
            return 0.0;
        }

        this.Balance -= amount;

        Operation operation = new Operation(OperationType.WITHDRAW, this, null, amount);
        this.historyManager.addOperation(operation);

        System.out.println("Wypłacono: " + amount + " z konta " + this.getId());

        return amount;
    }

    public boolean makeTransfer(BankAccount destination, Double amount) {
        if(amount > this.Balance || amount <= 0) {
            System.out.println("Błędna kwota przelewu");
            return false;
        }

        destination.deposit(amount);
        this.Balance -= amount;

        Operation operation = new Operation(OperationType.TRANSFER, this, destination, amount);
        this.historyManager.addOperation(operation);

        System.out.println("Wykonano przelew: " + amount + " do konta " + destination.getId() + " z konta: " + this.getId() + " Razem: " + this.getBalance());

        return true;
    }
}
