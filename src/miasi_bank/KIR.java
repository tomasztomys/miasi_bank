package miasi_bank;

import custom_exceptions.ClientOrProductDoesNotExistException;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by inf117199 on 21.04.2017.
 */
public final class KIR {
    private static ArrayList<Bank> banks = new ArrayList<>();

    private KIR() {
    }

    public static void addBank(Bank bank) {
        banks.add(bank);
    }

    public static void makeOperation(String bankFromID, String bankToID, String accountFromID, String accountToID, double amount) throws ClientOrProductDoesNotExistException {
        Bank bankFrom = null;
        Bank bankTo = null;

        for (Bank bank: banks) {
            if(Objects.equals(bank.getBankId(), bankFromID)) bankFrom = bank;
            if(Objects.equals(bank.getBankId(), bankToID)) bankTo = bank;
        }

        if(bankFrom == null || bankTo == null) {
            bankFrom.receiveExternalErrorOperation(accountFromID, amount);
        }

        try {
            bankTo.receiveExternalPaymentOperation(accountToID, amount);
        } catch (Exception e) {
            bankFrom.receiveExternalErrorOperation(accountFromID, amount);

            throw new ClientOrProductDoesNotExistException("Brak konta w banku docelowym");
        }

        bankTo.receiveExternalSuccessOperation();
    }
}
