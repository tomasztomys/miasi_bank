package miasi_bank.custom_exceptions;

public class NegativeValueOfMoneyTransactionException extends Exception {
    public NegativeValueOfMoneyTransactionException() {
        super("Wyjątek: Ujemna kwota operacji pieniężnej");
    }

    public NegativeValueOfMoneyTransactionException(String message) {
        super(message);
        System.out.println("Wyjątek: " + message);
    }
}
