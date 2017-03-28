import java.util.UUID;

/**
 * Created by Tomasz Gwoździk on 22.03.2017.
 */
public abstract class UniqueID {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
