import java.util.ArrayList;

/**
 * Created by inf117182 on 10.03.2017.
 */
public class HistoryManager {
    private ArrayList<Operation> operations;

    public HistoryManager() {
        this.operations = new ArrayList<>();
    }

    public void addOperation(Operation operation) {
        operations.add(operation);

        System.out.println("Dodano nową operację do historii");
    }

    public ArrayList getOperations() {
        return operations;
    }
}
