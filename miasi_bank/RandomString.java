package miasi_bank;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by inf117182 on 10.03.2017.
 */
public class RandomString {
    private static SecureRandom random = new SecureRandom();

    public static String get() {
        return new BigInteger(130, random).toString(32);
    }
}
