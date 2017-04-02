import java.util.Date;

public class Operation {
    private String id;
    private OperationType type;
    private double amount;
    private String from;
    private String to;
    private Date date;
    private String clientID;

    public Operation(OperationType type, String clientID, double amount, String from, String to) {
        this.id = UniqueID.generate();
        this.type = type;
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.date = new Date();
        this.clientID = clientID;
    }

    public Operation(Operation operation) {
        this.id = UniqueID.generate();
        this.type = operation.getType();
        this.amount = operation.getAmount();
        this.from = operation.getFrom();
        this.to = operation.getTo();
        this.date = operation.getDate();
        this.clientID = operation.getClientID();
    }

    public double getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public OperationType getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }

    public String getClientID() {
        return clientID;
    }
}
