package miasi_bank;

import custom_exceptions.*;
import miasi_bank.interests.IInterest;
import miasi_bank.reports.IVisitor;

import java.util.Date;

public class Product implements IAccount {
    private double balance;
    private String id;
    private String clientID;
    private Date creationDate;
    private History history;
    private IInterest interest;

    public Product(Product product) {
        this.id = product.getID();
        this.balance = product.getBalance();
        this.clientID = product.getClientID();
        this.creationDate = new Date();
        this.history = product.getHistory();
        this.interest = product.getInterest();
    }

    public Product(String clientID, double startBalance, IInterest interest) throws WrongValueException {
        if(startBalance < 0) {
            throw new WrongValueException("Produkt nie może być zainicjowany wartością mniejsza niż 0");
        }

        this.id = UniqueID.generate();
        this.balance = startBalance;
        this.clientID = clientID;
        this.creationDate = new Date();
        this.history = new History();
        this.interest = interest;
    }

    public Product(String clientID, IInterest interest) {
        this.id = UniqueID.generate();
        this.balance = 0.0;
        this.clientID = clientID;
        this.creationDate = new Date();
        this.history = new History();
        this.interest = interest;
    }

    public void __setBalance(double balance) {
        this.balance = balance;
    }

    public IInterest getInterest() {
        return interest;
    }
    public void setInterest(IInterest interest, Operation operation) {
        this.interest = interest;
        history.addOperation(new Operation(operation));
    }

    public double payment(double amount) throws WrongValueException {
        if(amount <= 0) {
            throw new WrongValueException("Nieprawidłowa wartość wpłaty " + clientID + " (" + id + ") Wpłata: " + amount);
        }

        balance += amount;

        return balance;
    }

    public double getTotalBalance() {
        return balance;
    }

    public double payment(Operation operation) throws WrongValueException {
        if(operation.getAmount() <= 0) {
            throw new WrongValueException("Nieprawidłowa wartość wpłaty " + clientID + " (" + id + ") Wpłata: " + operation.getAmount());
        }

        balance += operation.getAmount();

        history.addOperation(new Operation(operation));

        return balance;
    }

    public double withdraw(double amount) throws NoResourcesException, WrongValueException {
        if(amount <= 0) {
            throw new WrongValueException("Nieprawidłowa wartość wypłaty " + clientID + " (" + id + ") Wypłata: " + amount);
        }

        if(balance < amount) {
            throw new NoResourcesException("Nie masz tyle pieniędzy na koncie (clientID: " + clientID + ")");
        }

        balance -= amount;

        return balance;
    }

    public double withdraw(Operation operation) throws NoResourcesException, WrongValueException {
        if(operation.getAmount() <= 0) {
            throw new WrongValueException("Nieprawidłowa wartość wypłaty " + clientID + " (" + id + ") Wypłata: " + operation.getAmount());
        }

        if(balance < operation.getAmount()) {
            throw new NoResourcesException("Nie masz tyle pieniędzy na koncie (clientID: " + clientID + ")");
        }

        balance -= operation.getAmount();

        history.addOperation(new Operation(operation));

        return balance;
    }

    public String getID() {
        return id;
    }

    public String getClientID() {
        return clientID;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public double calculateInterest() {
        return getInterest().calculate(getBalance());
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public History getHistory() {
        return history;
    }

    @Override
    public void accept(IVisitor visitor)
    {
        visitor.visit(this);
    }
}
