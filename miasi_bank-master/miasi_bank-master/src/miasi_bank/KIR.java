package miasi_bank;

import custom_exceptions.ClientOrProductDoesNotExistException;
import custom_exceptions.WrongValueException;

import java.util.ArrayList;
import java.util.Objects;

public final class KIR {
    private static ArrayList<Bank> banks = new ArrayList<>();

    private KIR() {
    }

    public static void addBank(Bank bank) {
        banks.add(bank);
    }

    private static Bank findBank(String accountID) {
        Bank searchedBank = null;

        for (Bank bank: banks) {
            for (IAccount account : bank.getAccounts()) {
                if(account.getID().equals(accountID)) searchedBank = bank;
            }
        }

        return searchedBank;
    }

    public static void makeOperation(String accountFromID, String accountToID, double amount) throws ClientOrProductDoesNotExistException, WrongValueException {
        Bank bankFrom = findBank(accountFromID);
        Bank bankTo = findBank(accountToID);

        if (bankTo == null) {
            bankFrom.receiveExternalErrorOperation(accountFromID, accountToID, amount);

            throw new ClientOrProductDoesNotExistException("Brak konta w banku docelowym");
        }

        bankTo.receiveExternalPaymentOperation(accountToID, accountFromID, amount);
    }
}
