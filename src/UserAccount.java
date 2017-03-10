import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by inf117182 on 10.03.2017.
 */
public class UserAccount {
    private String Name;
    private String Surname;
    private String Pesel;
    private Date CreatedDate;
    private Set<BankAccount> BankAccounts;

    public UserAccount(String Name, String Surname, String Pesel) {
        this.Name = Name;
        this.Surname = Surname;
        this.Pesel = Pesel;
        this.CreatedDate = new Date();
        this.BankAccounts = new HashSet<>();
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getPesel() {
        return Pesel;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public Set<BankAccount> getBankAccounts() {
        return BankAccounts;
    }

    public boolean addBankAccount() {
        return this.BankAccounts.add(new BankAccount());
    }
}
