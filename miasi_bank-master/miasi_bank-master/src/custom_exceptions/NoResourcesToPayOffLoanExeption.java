package custom_exceptions;

public class NoResourcesToPayOffLoanExeption extends Exception {
    public NoResourcesToPayOffLoanExeption(String message) {
        super(message);
    }
}
