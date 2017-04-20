package miasi_bank;

import custom_exceptions.*;
import miasi_bank.interests.IInterest;
import miasi_bank.operations.Payment;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Bank {
    private Set<Client> clients;
    private Set<Account> accounts;
    private Set<Placement> placements;
    private Set<Loan> loans;
    private History history;

    public Bank() {
        this.clients = new LinkedHashSet<>();
        this.accounts = new LinkedHashSet<>();
        this.placements = new LinkedHashSet<>();
        this.loans = new LinkedHashSet<>();
        this.history = new History();

        System.out.println("Utworzono nowy bank.");
    }

    public History getHistory() {
        return history;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public Set<Placement> getPlacements() {
        return placements;
    }

    public Set<Loan> getLoans() {
        return loans;
    }

    public double getAccountTotalBalance(String clientID, String accountID) throws ClientOrProductDoesNotExistException {
        Account account = null;
        for (Account acc: accounts) {
            if(Objects.equals(acc.getID(), accountID) && Objects.equals(acc.getClientID(), clientID)) account = acc;
        }

        if(account == null) {
            throw new ClientOrProductDoesNotExistException("Nie można spłacic kredytu, klient lub konto nie istanieje w Banku!");
        }

        return account.getTotalBalance();
    }

    public double getAccountBalance(String clientID, String accountID) throws ClientOrProductDoesNotExistException {
        Account account = null;
        for (Account acc: accounts) {
            if(Objects.equals(acc.getID(), accountID) && Objects.equals(acc.getClientID(), clientID)) account = acc;
        }

        if(account == null) {
            throw new ClientOrProductDoesNotExistException("Nie można spłacic kredytu, klient lub konto nie istanieje w Banku!");
        }

        return account.getBalance();
    }

    public String addClient(String name, String surname, String pesel) throws ClientAlreadyExistException {
        boolean isOnList = false;
        for (Client client: clients) {
            if(Objects.equals(client.getPesel(), pesel)) isOnList = true;
        }

        if(isOnList) {
            throw new ClientAlreadyExistException("Klient o podanym PESELU ma już konto w tym banku!");
        }

        Client client = new Client(name, surname, pesel);
        clients.add(client);

        System.out.println("Dodano nowego klienta: " + client.getName() + " " + client.getSurname() + " (" + client.getId() + ")");
        return client.getId();
    }

    public String createAccount(String clientID, IInterest interest) throws ClientOrProductDoesNotExistException {
        Client client = null;
        for (Client c: clients) {
            if(c.getId() == clientID) client = c;
        }

        if(client == null) {
            throw new ClientOrProductDoesNotExistException("KLient nie ma jeszcze konta w banku. Nie można otworzyć konta!");
        }

        Account account = new Account(clientID, interest);
        accounts.add(account);

        System.out.println("Otwarto nowe konto bankowe dla klienta " + clientID + " (" + account.getID() + "). Stan konta: " + account.getBalance());
        return account.getID();
    }

    public String createAccount(String clientID, double startBalance, IInterest interest) throws WrongValueException, ClientOrProductDoesNotExistException {
        Client client = null;
        for (Client c: clients) {
            if(Objects.equals(c.getId(), clientID)) client = c;
        }

        if(client == null) {
            throw new ClientOrProductDoesNotExistException("Klient nie ma jeszcze konta w banku. Nie można otworzyć konta!");
        }

        Account account = new Account(clientID, startBalance, interest);
        accounts.add(account);

        System.out.println("Otwarto nowe konto bankowe dla klienta " + clientID + " (" + account.getID() + "). Stan konta: " + account.getBalance());
        return account.getID();
    }

    public double payment(String clientID, String accountID, double amount) throws ClientOrProductDoesNotExistException, WrongValueException {
        Account account = null;
        for (Account acc: accounts) {
            if(Objects.equals(acc.getID(), accountID) && Objects.equals(acc.getClientID(), clientID)) account = acc;
        }

        if(account == null) {
            throw new ClientOrProductDoesNotExistException("Nie można zrealizować wpłaty, klient lub konto nie istanieje w Banku!");
        }

        Operation operation = new Operation(OperationType.PAYMENT, clientID, amount, null, accountID);

        Payment payment = new Payment(account, amount, operation);
        double balance = payment.execute();

        history.addOperation(operation);

        System.out.println("Dokonano wpłate na konto bankowe klienta " + clientID + " (" + accountID + "). Stan konta: " + balance);
        return balance;
    }

    public double withdraw(String clientID, String accountID, double amount) throws ClientOrProductDoesNotExistException, NoResourcesException, WrongValueException {
        Account account = null;
        for (Account acc: accounts) {
            if(Objects.equals(acc.getID(), accountID) && Objects.equals(acc.getClientID(), clientID)) account = acc;
        }

        if(account == null) {
            throw new ClientOrProductDoesNotExistException("Nie można zrealizować wypłaty, klient lub konto nie istanieje w Banku!");
        }

        Operation operation = new Operation(OperationType.WITHDRAW, clientID, amount, accountID, null);

        double balance = account.withdraw(operation);
        history.addOperation(operation);

        System.out.println("Dokonano wypłaty z konta bankowe klienta " + clientID + " (" + accountID + "). Stan konta: " + balance);
        return balance;
    }

    public double transfer(String clientID, String accountFromID, String accountToID, double amount) throws ClientOrProductDoesNotExistException, NoResourcesException, WrongValueException {
        Account accountFrom = null;
        Account accountTo = null;
        for (Account acc: accounts) {
            if(Objects.equals(acc.getID(), accountFromID) && Objects.equals(acc.getClientID(), clientID)) accountFrom = acc;
            if(Objects.equals(acc.getID(), accountToID)) accountTo = acc;
        }

        if(accountFrom == null) {
            throw new ClientOrProductDoesNotExistException("Nie można zrealizować przelewu, klient lub konto, z którego ma być zrealizowana transakcja nie istanieje w Banku!");
        }

        if(accountTo == null) {
            throw new ClientOrProductDoesNotExistException("Nie można zrealizować przelewu, konto na które mają zostac przelane pieniądze nie istnieje nie istanieje w Banku!");
        }

        Operation operation = new Operation(OperationType.TRANSFER, clientID, amount, accountFromID, accountToID);

        double balance = accountFrom.withdraw(operation);
        accountTo.payment(operation);

        history.addOperation(operation);

        System.out.println("Dokonano przelewu z konta bankowe klienta " + clientID + " (" + accountFromID + " -> " + accountToID + ") Stan konta: " + balance);
        return balance;
    }

    public String createPlacement(String clientID, String accountID, double amount, Date closingDate, IInterest interest) throws ClientOrProductDoesNotExistException, NoResourcesException, WrongValueException {
        Account account = null;
        for (Account acc: accounts) {
            if(Objects.equals(acc.getID(), accountID) && Objects.equals(acc.getClientID(), clientID)) account = acc;
        }

        if(account == null) {
            throw new ClientOrProductDoesNotExistException("Nie można stworzyć lokaty, klient lub konto nie istanieje w Banku!");
        }

        Placement placement = new Placement(clientID, amount, closingDate, interest);
        Operation operation = new Operation(OperationType.CREATE_PLACEMENT, clientID, amount, accountID, placement.getID());

        double balance = account.withdraw(operation);
        placements.add(placement);
        history.addOperation(operation);

        System.out.println("Otwarto nową lokatę z konta " + clientID + " (" + accountID + ", " + placement.getID() + "). Stan konta: " + balance);
        return placement.getID();
    }

    public double closePlacement(String clientID, String accountID, String placementID, Date closingDate) throws ClientOrProductDoesNotExistException, ProductIsAlreadyClosedException, WrongValueException {
        Account account = null;
        for (Account acc: accounts) {
            if(Objects.equals(acc.getID(), accountID) && Objects.equals(acc.getClientID(), clientID)) account = acc;
        }

        if(account == null) {
            throw new ClientOrProductDoesNotExistException("Nie można zamknąć lokaty, klient lub konto nie istanieje w Banku!");
        }

        Placement placement = null;
        for (Placement p: placements) {
            if(Objects.equals(p.getID(), placementID) && Objects.equals(p.getClientID(), clientID)) placement = p;
        }

        if(placement == null) {
            throw new ClientOrProductDoesNotExistException("Nie można zamknąć lokaty, lokata nie istnieje!");
        }

        Operation operation = new Operation(OperationType.CLOSE_PLACEMENT, clientID, placement.close(closingDate), placementID, accountID);

        double balance = account.payment(operation);
        history.addOperation(operation);

        System.out.println("Zamknięto lokatę z konta bankowe klienta " + clientID + " (" + accountID + ", " + placementID + "). Stan konta: " + balance);
        return balance;
    }

    public String createLoan(String clientID, String accountID, double amount, Date closingDate, IInterest interest) throws ClientOrProductDoesNotExistException, WrongValueException {
        Account account = null;
        for (Account acc: accounts) {
            if(Objects.equals(acc.getID(), accountID) && Objects.equals(acc.getClientID(), clientID)) account = acc;
        }

        if(account == null) {
            throw new ClientOrProductDoesNotExistException("Nie można zrealizować wypłaty, klient lub konto nie istanieje w Banku!");
        }

        Loan loan = new Loan(clientID, amount, closingDate, interest);
        Operation operation = new Operation(OperationType.CREATE_LOAN, clientID, amount, accountID, loan.getID());

        double balance = account.payment(operation);

        loans.add(loan);
        history.addOperation(operation);

        System.out.println("Zaciągnięto kredyt na koncie " + clientID + " (" + accountID + ", " + loan.getID() + "). Stan konta: " + balance);
        return loan.getID();
    }

    public double payOffLoan(String clientID, String accountID, String loanID, Date closingDate) throws ClientOrProductDoesNotExistException, NoResourcesToPayOffLoanExeption, NoResourcesException, WrongValueException, ProductIsAlreadyClosedException, WrongCloseDateException {
        Account account = null;
        for (Account acc: accounts) {
            if(Objects.equals(acc.getID(), accountID) && Objects.equals(acc.getClientID(), clientID)) account = acc;
        }

        if(account == null) {
            throw new ClientOrProductDoesNotExistException("Nie można spłacic kredytu, klient lub konto nie istanieje w Banku!");
        }

        Loan loan = null;
        for (Loan l: loans) {
            if(Objects.equals(l.getID(), loanID) && Objects.equals(l.getClientID(), clientID)) loan = l;
        }

        if(loan == null) {
            throw new ClientOrProductDoesNotExistException("Nie można spłacic kredytu, kredyt nie istnieje!");
        }

        double interest = loan.getInterest(closingDate);

        System.out.println(account.getBalance());
        System.out.println(loan.getBalance());
        if(account.getBalance() < loan.getBalance() + interest) {
            throw new NoResourcesToPayOffLoanExeption("Nie masz wystarczających środków aby spłacić kredyt!");
        }

        Operation operation = new Operation(OperationType.PAY_OFF_LOAN, clientID, loan.close(closingDate), accountID, loan.getID());
        double balance = account.withdraw(operation);
        history.addOperation(operation);

        System.out.println("Dokonano spłaty kredytu z konta bankowe klienta " + clientID + " (" + accountID + ", " + loanID + "). Stan konta: " + balance);
        return balance;
    }

    public boolean setDebitAccount(String clientID, String accountID, double maxDebit) throws DebitAccountAlreadyExists, ClientOrProductDoesNotExistException {
        Account account = null;
        for (Account acc: accounts) {
            if(Objects.equals(acc.getID(), accountID) && Objects.equals(acc.getClientID(), clientID)) account = acc;
        }

        if(account == null) {
            throw new ClientOrProductDoesNotExistException("Nie można utworzyć konta debetowego, klient lub konto nie istanieje w Banku!");
        }

        if(account instanceof DebitAccount) {
            throw new DebitAccountAlreadyExists("Nie można utworzyć konta debetowego, z konta, które ma już założony debet!");
        }

        Operation operation = new Operation(OperationType.CREATE_DEBIT, clientID, maxDebit, accountID, null);
        DebitAccount debitAccount = new DebitAccount(account, operation);

        accounts.remove(account);
        history.addOperation(operation);

        System.out.println("Utworzono konto debetowe z konta klienta " + clientID + " (" + accountID + "). Stan konta: " + debitAccount.getTotalBalance());
        return accounts.add(debitAccount);
    }

    public void calculateAndAddInterestToAccounts() throws WrongValueException {
        for (Account acc: accounts) {
           double interest = acc.calculateInterest();
           Operation operation = new Operation(OperationType.CALCULATE_INTEREST, acc.getClientID(), interest, acc.getID(), null);

           acc.payment(operation);
           history.addOperation(operation);
        }

        System.out.println("Naliczono odsetki dla wszystkich kont");
    }

    public double calculateAndAddInterestToAccount(String clientID, String accountID) throws ClientOrProductDoesNotExistException, WrongValueException {
        Account account = null;
        for (Account acc: accounts) {
            if(Objects.equals(acc.getID(), accountID) && Objects.equals(acc.getClientID(), clientID)) account = acc;
        }

        if(account == null) {
            throw new ClientOrProductDoesNotExistException("Nie można utworzyć konta debetowego, klient lub konto nie istanieje w Banku!");
        }

        double interest = account.calculateInterest();
        Operation operation = new Operation(OperationType.CALCULATE_INTEREST, account.getClientID(), interest, account.getID(), null);

        account.payment(operation);

        history.addOperation(operation);

        System.out.println("Naliczono odsetki dla konta klienta: " + clientID + " (" + accountID + "). Stan konta: " + account.getTotalBalance());
        return account.getBalance();
    }

    public void changeInterest(String clientID, String accountID, IInterest interest) throws CustomException {
        Operation operation = new Operation(OperationType.CHANGE_INTEREST_MECHANISM, clientID, 0.0, accountID, null);

        Account account = null;
        for (Account acc: accounts) {
            if(Objects.equals(acc.getID(), accountID) && Objects.equals(acc.getClientID(), clientID)) account = acc;
        }

        if(account != null) {
            account.setInterest(interest, operation);
        }

        Placement placement = null;
        for (Placement p: placements) {
            if(Objects.equals(p.getID(), accountID) && Objects.equals(p.getClientID(), clientID)) placement = p;
        }

        if(placement != null) {
            placement.setInterest(interest, operation);
        }

        Loan loan = null;
        for (Loan l: loans) {
            if(Objects.equals(l.getID(), accountID) && Objects.equals(l.getClientID(), clientID)) loan = l;
        }

        if(loan != null) {
            loan.setInterest(interest, operation);
        }

        if(account != null || placement != null || loan != null) {
            history.addOperation(operation);
            System.out.println("Zmieniono system odsetek dla konta klienta: " + clientID + " (" + accountID + ").");
        }
    }

    public Set<Product> getProducts() {
        Set<Product> products = new LinkedHashSet<>();
        products.addAll(this.accounts);
        products.addAll(this.loans);
        products.addAll(this.placements);

        return products;
    }

    public Set<Product> makeRaport(ReportType type, Integer limit) {
        ReportSystem reportSystem = new ReportSystem();
        if(type.equals(ReportType.UNDER_LIMIT)) {
            return reportSystem.getProductsUnderLimit(this.getProducts(), limit);
        } else {
            return reportSystem.getProductsAtLeastLimit(this.getProducts(), limit);
        }
    }
}
