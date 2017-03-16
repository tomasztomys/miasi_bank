import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by TG & MM on 10.03.2017.
 */
public class Investment extends BankProduct {
    String id;
    private Date createDate;
    private Date closeDate;
    boolean isActive;
    Double deposit;
    InterestManager interestManager;
    BankAccount bankAccount;

    public Investment(BankAccount bankAccount, Double amount, Date closeDate, InterestManager interestManager) {
        Date createDate = new Date();

        if(closeDate.compareTo(createDate) <= 0) {
            isActive = false;
        }

        this.id = new BigInteger(130, new SecureRandom()).toString(32);
        this.closeDate = closeDate;
        this.createDate = createDate;
        this.deposit = amount;
        this.interestManager = interestManager;
        this.bankAccount = bankAccount;

        Operation operation = new Operation(OperationType.OPEN_DEPOSIT, bankAccount, this, amount);
        this.historyManager.addOperation(operation);
    }

    public String getId() {
        return this.id;
    }

    public Double closeInvestment(Date closeTempDate) {
        if(!this.isActive) return 0.0;

        Double returnDeposit = this.deposit;

        if(closeTempDate.after(createDate)) {
            long diff = closeTempDate.getTime() - createDate.getTime();
            long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            returnDeposit =  this.deposit + (this.deposit * interestManager.getPercentage() / 100 * diffDays / 365.25 );
        }

        return returnDeposit;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void disableInvestment(Double amount) {
        if(this.isActive) this.isActive = false;

        Operation operation = new Operation(OperationType.CLOSE_DEPOSIT, this.bankAccount, this, amount);
        this.historyManager.addOperation(operation);
    }
}
