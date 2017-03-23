package miasi_bank.custom_exceptions;

public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException() {
        super("Wyjątek: Nie wystarczające środki na koncie, nie można wypłacić");
    }
    public InsufficientBalanceException(String message) {
        super(message);
        System.out.println("Wyjątek: " + message);
    }
}
