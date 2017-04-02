import java.util.Date;

public class Client {
    private String id;
    private String name;
    private String surname;
    private String pesel;
    private Date creationDate;

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
