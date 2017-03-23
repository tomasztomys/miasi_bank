package miasi_bank;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Tomasz Gwoździk on 22.03.2017.
 */
public class History {
    private Set<Operation> operations;

    public History() {
        this.operations = new LinkedHashSet<>();
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }
}
