package miasi_bank.reports;

import miasi_bank.Account;
import miasi_bank.DebitAccount;
import miasi_bank.Product;
import miasi_bank.loans.Loan;
import miasi_bank.placements.Placement;

public class BalanceShortReportVisitor implements IVisitor {
   public void visit(Product product) {

   }

    @Override
    public void visit(Account account) {
        System.out.println("Konto " + account.getID() + ", saldo: " + account.getTotalBalance());
    }

    @Override
    public void visit(DebitAccount account) {
        System.out.println("Konto debetowe " + account.getID() + ", saldo: " + account.getBalance() + ", dostępne środki: " + account.getTotalBalance());
    }

    @Override
    public void visit(Loan loan) {
        if(loan.getIsActive()) {
            System.out.println("Kredyt " + loan.getID() + ", saldo: " + loan.getTotalBalance());
        } else {
            System.out.println("Kredyt " + loan.getID() + ", saldo: " + loan.getTotalBalance());
        }
    }

    @Override
    public void visit(Placement placement) {
        if(placement.getIsActive()) {
            System.out.println("Lokata " + placement.getID() + ", saldo: " + placement.getTotalBalance());
        } else {
            System.out.println("Lokata " + placement.getID() + ", saldo: " + placement.getTotalBalance());
        }
    }
}
