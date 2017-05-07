package miasi_bank.reports;

import miasi_bank.Account;
import miasi_bank.DebitAccount;
import miasi_bank.IAccount;
import miasi_bank.Product;
import miasi_bank.loans.Loan;
import miasi_bank.placements.Placement;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class BalanceReportVisitor implements IVisitor {
   public void visit(Product product) {

   }

    @Override
    public void visit(Account account) {
        System.out.println("Konto " + account.getID() + ", saldo: " + account.getBalance());
    }

    @Override
    public void visit(DebitAccount account) {
        System.out.println("Konto debetowe " + account.getID() + ", saldo: " + account.getBalance());
    }

    @Override
    public void visit(Loan loan) {
        if(loan.getIsActive()) {
            System.out.println("Kredyt " + loan.getID() + " założony " + loan.getCreationDate() + " zostanie zamknięty " + loan.getCloseDate() + ", saldo: " + loan.getBalance());
        } else {
            System.out.println("Kredyt " + loan.getID() + " założony " + loan.getCreationDate() + " został zamknięty " + loan.getCloseDate() + ", saldo: " + loan.getBalance());
        }
    }

    @Override
    public void visit(Placement placement) {
        if(placement.getIsActive()) {
            System.out.println("Lokata " + placement.getID() + " założona " + placement.getCreationDate() + " zostanie zamknięta " + placement.getInitCloseDate() + ", saldo: " + placement.getBalance());
        } else {
            System.out.println("Lokata " + placement.getID() + " założona " + placement.getCreationDate() + " została zamknięta " + placement.getCloseDate() + ", saldo: " + placement.getBalance());
        }
    }
}
