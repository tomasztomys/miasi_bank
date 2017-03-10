import java.util.Date;

/**
 * Created by inf117182 on 10.03.2017.
 */
public class UserAccount {
    private String Name;
    private String Surname;
    private String Pesel;
    private Date CreatedDate;

    public UserAccount(String Name, String Surname, String Pesel) {
        this.Name = Name;
        this.Surname = Surname;
        this.Pesel = Pesel;
        this.CreatedDate = new Date();
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
}
