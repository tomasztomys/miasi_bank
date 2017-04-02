package custom_exceptions;

public class ClientOrProductDoesNotExistException extends Exception {
   public ClientOrProductDoesNotExistException(String message) {
       super(message);
   }
}
