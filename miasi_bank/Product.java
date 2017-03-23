package miasi_bank;

import custom_exceptions.CustomException;
import interests.Interest;

import java.util.Date;

/**
 * Created by Tomasz Gwoździk on 22.03.2017.
 */
public class Product {
    private double balance;
    private String id;
    private String clientID;
    private Date creationDate;
    private History history;
    private Interest interest;

    public Product(Product product) {
        this.id = product.getID();
        this.balance = product.getBalance();
        this.clientID = product.getClientID();
        this.creationDate = new Date();
        this.history = product.getHistory();
        this.interest = product.getInterest();
    }

    public Product(String clientID, double startBalance, Interest interest) {
        this.id = UniqueID.generate();
        this.balance = startBalance;
        this.clientID = clientID;
        this.creationDate = new Date();
        this.history = new History();
        this.interest = interest;
    }

    public Product(String clientID, Interest interest) {
        this.id = UniqueID.generate();
        this.balance = 0.0;
        this.clientID = clientID;
        this.creationDate = new Date();
        this.history = new History();
        this.interest = interest;
    }

    public Interest getInterest() {
        return interest;
    }
    public void setInterest(Interest interest, Operation operation) {
        this.interest = interest;
        history.addOperation(new Operation(operation));
    }

    public double payment(double amount) throws CustomException {
        if(amount <= 0) {
            throw new CustomException("Nieprawidłowa wartość wpłaty " + clientID + " (" + id + ") Wpłata: " + amount);
        }

        balance += amount;

        return balance;
    }

    public double payment(Operation operation) throws CustomException {
        if(operation.getAmount() <= 0) {
            throw new CustomException("Nieprawidłowa wartość wpłaty " + clientID + " (" + id + ") Wpłata: " + operation.getAmount());
        }

        balance += operation.getAmount();

        history.addOperation(new Operation(operation));

        return balance;
    }

    public double withdraw(double amount) throws CustomException {
        if(amount <= 0) {
            throw new CustomException("Nieprawidłowa wartość wypłaty " + clientID + " (" + id + ") Wypłata: " + amount);
        }

        if(balance < amount) {
            throw new CustomException("Nie masz tyle pieniędzy na koncie (clientID: " + clientID + ")");
        }

        balance -= amount;

        return balance;
    }

    public double withdraw(Operation operation) throws CustomException {
        if(operation.getAmount() <= 0) {
            throw new CustomException("Nieprawidłowa wartość wypłaty " + clientID + " (" + id + ") Wypłata: " + operation.getAmount());
        }

        if(balance < operation.getAmount()) {
            throw new CustomException("Nie masz tyle pieniędzy na koncie (clientID: " + clientID + ")");
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

    public Date getCreationDate() {
        return creationDate;
    }

    public History getHistory() {
        return history;
    }
}
