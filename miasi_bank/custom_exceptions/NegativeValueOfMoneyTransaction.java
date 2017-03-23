package miasi_bank.custom_exceptions;

public class NegativeValueOfMoneyTransaction extends Exception {
    public NegativeValueOfMoneyTransaction() {
        super("Wyjątek: Ujemna kwota operacji pieniężnej");
    }

    public NegativeValueOfMoneyTransaction(String message) {
        super(message);
        System.out.println("Wyjątek: " + message);
    }
}
