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

    public boolean addInvestment() {
        return this.InvestmentList.add(new Investment());
    }

    public boolean addCredit() {
        return this.CreditList.add(new Credit());
    }

    public boolean addOperation() {
        //return this.OperationList.add(new Operation()); //TODO: constructor changed
        return false;
    }

    public void depositCash(Double cash) {
        this.Balance += cash; //TODO: operation
    }
}
