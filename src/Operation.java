import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Created by TG & MM on 10.03.2017.
 */
public class Operation {

    String id = new BigInteger(130, new SecureRandom()).toString(32);
    String type;
    UserAccount source;
    UserAccount destination;
    Date date;
    Double amount;


    public Operation(String type, UserAccount source, UserAccount destination, Double amount){
        this.type = type;
        this.source=source;
        this.destination=destination;
        this.date= new Date();
        this.amount = amount;
    }


}
