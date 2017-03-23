package miasi_bank;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by TG & MM on 10.03.2017.
 */
public class Credit extends BankProduct {
    String id;
    private Date createDate;
    boolean isActive;
    double amount;
    InterestManager interestManager;
    BankAccount bankAccount;

    public Credit(BankAccount bankAccount, double amount, InterestManager interestManager) {
        Date createDate = new Date();

        this.id = new BigInteger(130, new SecureRandom()).toString(32);
        this.createDate = createDate;
        this.amount = amount;
        this.interestManager = interestManager;
        this.bankAccount = bankAccount;
        this.isActive = true;

        Operation operation = new Operation(OperationType.TAKE_CREDIT, bankAccount, this, amount);
        this.historyManager.addOperation(operation);
    }

    public String getId() {
        return this.id;
    }

    public double payOffDebt(Date closeTempDate) {
        if(!this.isActive) return 0.0;

        long diff = closeTempDate.getTime() - createDate.getTime();
        long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        double returnAmount =  this.amount + (this.amount * (interestManager.getPercentage() / 100.0) * (diffDays / 365.0) * diffDays);

        return returnAmount;
    }

    public void closeCredit(double amount) {
        if(this.isActive) this.isActive = false;

        Operation operation = new Operation(OperationType.PAY_OFF_DEBT, this.bankAccount, this, amount);
        this.historyManager.addOperation(operation);
    }
}
