/**
 * Created by Tomasz Gwoździk on 23.03.2017.
 */
public class ClientOrProductDoesNotExistException extends Exception {
   public ClientOrProductDoesNotExistException(String message) {
       super(message);
   }
}
