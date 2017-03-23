package custom_exceptions;

/**
 * Created by Tomasz Gwoździk on 23.03.2017.
 */
public class ClientAlreadyExistException extends Exception {
    public ClientAlreadyExistException(String message) {
        super(message);
    }
}
