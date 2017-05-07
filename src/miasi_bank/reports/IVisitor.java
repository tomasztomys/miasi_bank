package miasi_bank.reports;

import miasi_bank.Account;
import miasi_bank.DebitAccount;
import miasi_bank.Product;
import miasi_bank.loans.Loan;
import miasi_bank.placements.Placement;

/**
 * Created by Tomasz on 07.05.2017.
 */
public interface IVisitor {
    void visit(Account account);
    void visit(DebitAccount account);
    void visit(Loan loan);
    void visit(Placement placement);
    void visit(Product product);
}
