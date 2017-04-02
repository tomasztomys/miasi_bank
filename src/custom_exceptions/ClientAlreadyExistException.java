package custom_exceptions;

public class ClientAlreadyExistException extends Exception {
    public ClientAlreadyExistException(String message) {
        super(message);
    }
}
