package miasi_bank.custom_exceptions;

public class WrongIdException extends Exception {
    public WrongIdException() {
        super("Wyjątek: Ujemna kwota operacji pieniężnej");
    }

    public WrongIdException(String message) {
        super(message);
        System.out.println("Wyjątek: " + message);
    }
}
