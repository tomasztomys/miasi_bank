import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by TG & MM on 10.03.2017.
 */
public class Investment extends BankProduct {
    private Date createDate;
    private Date closeDate;
    boolean isActive;
    Double deposit;
    InterestManager interestManager;

    public Investment(BankAccount bankAccount, Double amount, Date closeDate, InterestManager interestManager) {
        Date createDate = new Date();

        if(closeDate.compareTo(createDate) <= 0) {
            isActive = false;
        }

        this.closeDate = closeDate;
        this.createDate = createDate;
        this.deposit = amount;
        this.interestManager = interestManager;

        Operation operation = new Operation(OperationType.OPEN_DEPOSIT, bankAccount, this, amount);
        this.historyManager.addOperation(operation);
    }

    public Double closeInvestment(Date closeTempDate) {
        if(!this.isActive) return 0.0;

        if(closeTempDate.after(createDate)) {
            long diff = closeTempDate.getTime() - createDate.getTime();
            long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            return this.deposit + (this.deposit * interestManager.getPercentage() / 100 * diffDays / 365.25 );
        }

        return this.deposit;
    }
}
