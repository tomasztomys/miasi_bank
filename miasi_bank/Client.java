package miasi_bank;

import java.util.Date;

/**
 * Created by Tomasz Gwoździk on 22.03.2017.
 */
public class Client {
    String id;
    String name;
    String surname;
    String pesel;
    Date creationDate;

    public Client(String name, String surname, String pesel) {
        this.id = UniqueID.generate();
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.creationDate = new Date();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPesel() {
        return pesel;
    }
}
