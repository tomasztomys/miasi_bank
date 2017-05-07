package miasi_bank.reports;

import miasi_bank.Account;
import miasi_bank.DebitAccount;
import miasi_bank.Product;
import miasi_bank.loans.Loan;
import miasi_bank.placements.Placement;

public class CumulativeReportVisitor implements IVisitor {
    public double result;

    public CumulativeReportVisitor() {
        this.result = 0;
    }

   public void visit(Product product) {

   }

    @Override
    public void visit(Account account) {
        this.result += account.getBalance();
    }

    @Override
    public void visit(DebitAccount account) {
        this.result += account.getBalance();
    }

    @Override
    public void visit(Loan loan) {
        this.result += loan.getBalance();
    }

    @Override
    public void visit(Placement placement) {
        this.result += placement.getBalance();
    }
}
