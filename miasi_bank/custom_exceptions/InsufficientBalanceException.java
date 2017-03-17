package miasi_bank.custom_exceptions;

/**
 * Created by inf117225 on 17.03.2017.
 */
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super();
    }
    public InsufficientBalanceException(final String message) {
        super(message);
    }

    public InsufficientBalanceException(final String message, final Throwable cause) {
        super(message, cause);

    }
}
