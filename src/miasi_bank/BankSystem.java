package miasi_bank;

import custom_exceptions.*;
import miasi_bank.interests.IInterest;
import miasi_bank.interests.LinearInterest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BankSystem {
    public static void main(String[] args) {
        IInterest interest1 = new LinearInterest();
        IInterest interest2 = new LinearInterest();

        Bank bank = new Bank();
        System.out.println("-----------------------");

        String client1 = null;
        try {
            client1 = bank.addClient("Tomasz", "Gwoździk", "0");
        } catch (ClientAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        String client1Copy = null;
        try {
            client1Copy = bank.addClient("Tomasz", "Gwoździk", "0");
        } catch (ClientAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        String client2 = null;
        try {
            client2 = bank.addClient("Dariusz", "Paluch", "1");
        } catch (ClientAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        String client1Account1 = null;
        try {
            client1Account1 = bank.createAccount(client1, interest1);
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        String client2Account1 = null;
        try {
            client2Account1 = bank.createAccount(client2, 500.0, interest1);
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            bank.payment(client1, client1Account1, 200.0);
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            bank.payment(client1, client1Account1, -200.0);
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            bank.withdraw(client1, client1Account1, -200.0);
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (NoResourcesException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            bank.withdraw(client1, client1Account1, 200.0);
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (NoResourcesException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            bank.transfer(client1, client1Account1, client2Account1, 200.0);
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (NoResourcesException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            bank.transfer(client2, client2Account1, client1Account1, 200.0);
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (NoResourcesException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        String client1Account1PlacementCopy1 = null;
        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyy");
            Date date = format.parse("25.03.2017");
            client1Account1PlacementCopy1 = bank.createPlacement(client1, client1Account1, -200.0, date, interest1);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (NoResourcesException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        String client1Account1Placement1 = null;
        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyy");
            Date date = format.parse("25.03.2017");
            client1Account1Placement1 = bank.createPlacement(client1, client1Account1, 200.0, date, interest1);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (NoResourcesException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        String client1Account1Loan1 = null;
        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyy");
            Date date = format.parse("25.03.2017");
            client1Account1Loan1 = bank.createLoan(client1, client1Account1, 1000.0, date, interest1);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            bank.payment(client1, client1Account1, 500.0);
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyy");
            Date date = format.parse("25.03.2017");
            bank.payOffLoan(client1, client1Account1, client1Account1Loan1, date);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (NoResourcesToPayOffLoanExeption e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        } catch (ProductIsAlreadyClosedException e) {
            System.out.println(e.getMessage());
        } catch (NoResourcesException e) {
            System.out.println(e.getMessage());
        } catch (WrongCloseDateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyy");
            Date date = format.parse("25.03.2017");
            bank.closePlacement(client1, client1Account1, client1Account1Placement1, date);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        } catch (ProductIsAlreadyClosedException e) {
            System.out.println(e.getMessage());
        } catch (WrongCloseDateException e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------");

        try {
            bank.setDebitAccount(client1, client1Account1, 5000.0);
        } catch (DebitAccountAlreadyExists e) {
            System.out.println(e.getMessage());
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            bank.setDebitAccount(client1, client1Account1, 5000.0);
        } catch (DebitAccountAlreadyExists e) {
            System.out.println(e.getMessage());
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            bank.calculateAndAddInterestToAccount(client1, client1Account1);
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            bank.calculateAndAddInterestToAccounts();
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        try {
            bank.changeInterest(client1, client1Account1, interest2);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        bank.makeBalanceReport();
        System.out.println("-----------------------");

        bank.makeBalanceShortReport();
        System.out.println("-----------------------");

        bank.makeCumulativeReport();
        System.out.println("-----------------------");

        Bank bank2 = new Bank();

        String client4 = null;
        try {
            client4 = bank2.addClient("Tomasz", "Gwoździk", "0");
        } catch (ClientAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        String client4Account1 = null;
        try {
            client4Account1 = bank2.createAccount(client4, interest1);
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("-----------------------");

        KIR.addBank(bank);
        KIR.addBank(bank2);

        try {
            bank.makeExternalOperation(client1, client1Account1, client4Account1, 500.0);
        } catch (ClientOrProductDoesNotExistException e) {
            System.out.println(e.getMessage());
        } catch (NoResourcesException e) {
            System.out.println(e.getMessage());
        } catch (WrongValueException e) {
            System.out.println(e.getMessage());
        }
    }
}
