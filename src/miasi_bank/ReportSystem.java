package miasi_bank;

import java.util.LinkedHashSet;
import java.util.Set;

public class ReportSystem {

    public Set<IAccount> getProductsBalanceUnderLimit(Set<IAccount> products, Integer limit) {
        Set<IAccount> result = new LinkedHashSet<>();
        System.out.println("ID - saldo");
        for(IAccount product: products) {
            if(product.getBalance() < limit) {
                System.out.println(product.getID() + " - " + product.getBalance());
                result.add(product);
            }
        }

        return result;
    }

    public Set<IAccount> getProductsBalanceAtLeastLimit(Set<IAccount> products, Integer limit) {
        Set<IAccount> result = new LinkedHashSet<>();
        System.out.println("ID - saldo");
        for(IAccount product: products) {
            if(product.getBalance() >= limit) {
                System.out.println(product.getID() + " - " + product.getBalance());
                result.add(product);
            }
        }

        return result;
    }
}
