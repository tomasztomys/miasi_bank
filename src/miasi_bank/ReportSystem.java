package miasi_bank;

import java.util.LinkedHashSet;
import java.util.Set;

public class ReportSystem {

    public Set<Product> getProductsUnderLimit(Set<Product> products, Integer limit) {
        Set<Product> result = new LinkedHashSet<>();
        System.out.println("ID - saldo");
        for(Product product: products) {
            if(product.getBalance() < limit) {
                System.out.println(product.getID() + " - " + product.getBalance());
                result.add(product);
            }
        }

        return result;
    }

    public Set<Product> getProductsAtLeastLimit(Set<Product> products, Integer limit) {
        Set<Product> result = new LinkedHashSet<>();
        System.out.println("ID - saldo");
        for(Product product: products) {
            if(product.getBalance() >= limit) {
                System.out.println(product.getID() + " - " + product.getBalance());
                result.add(product);
            }
        }

        return result;
    }
}
