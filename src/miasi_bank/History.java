import java.util.LinkedHashSet;
import java.util.Set;

public class History {
    private Set<Operation> operations;

    public History() {
        this.operations = new LinkedHashSet<>();
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }
    public Set getHistory() { return operations; }
}
