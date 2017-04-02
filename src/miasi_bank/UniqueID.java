package miasi_bank;

import java.util.UUID;

public abstract class UniqueID {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
