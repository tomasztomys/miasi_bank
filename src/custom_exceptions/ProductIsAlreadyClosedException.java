package custom_exceptions;

public class ProductIsAlreadyClosedException extends Exception {
    public ProductIsAlreadyClosedException(String message) {
        super(message);
    }
}
