package custom_exceptions;

public class DebitAccountAlreadyExists extends Exception {
    public DebitAccountAlreadyExists(String message) {
        super(message);
    }
}