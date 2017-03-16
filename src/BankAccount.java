import java.util.*;

/**
 * Created by inf117182 on 10.03.2017.
 */
public class BankAccount extends BankProduct {
    private String Id;
    private Double Balance;
    private Set<Investment> InvestmentList;
    private Set<Credit> CreditList;
    private List<Operation> OperationList;

    public BankAccount() {
        super();

        this.Id = RandomString.get();
        this.Balance = 0.0;
        this.InvestmentList = new HashSet<>();
        this.CreditList = new HashSet<>();
        this.OperationList = new ArrayList<>();

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

    public List<Operation> getOperationList() {
        return OperationList;
    }

    public Investment addInvestment(Double amount, Date closeDate) {
        if(this.Balance < amount) {
            return null;
        }

        this.Balance -= amount;

        Investment investment = new Investment(this, amount, closeDate, new InterestManager(5));

        Operation operation = new Operation(OperationType.OPEN_DEPOSIT, this, investment, amount);
        this.historyManager.addOperation(operation);

        this.InvestmentList.add(investment);

        return investment;
    }

    public void closeInvestment(Investment investment, Date closeTempDate) {
        Double amount = investment.closeInvestment(closeTempDate);
        this.Balance += amount;

        Operation operation = new Operation(OperationType.CLOSE_DEPOSIT, null, this, amount);
        this.historyManager.addOperation(operation);
    }

    public boolean addCredit() {
        return this.CreditList.add(new Credit());
    }

    public boolean addOperation() {
        //return this.OperationList.add(new Operation()); //TODO: constructor changed
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
