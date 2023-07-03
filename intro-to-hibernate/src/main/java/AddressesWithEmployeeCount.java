import Utils.Utils;
import entities.Address;

import javax.persistence.EntityManager;
import java.util.List;

public class AddressesWithEmployeeCount {
    private static final String PRINT_FORMAT = "%s, %s - %d employees%n";

    public static void main(String[] args) {

        EntityManager entityManager = Utils.createEntityManager();

        List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a " +
                        "ORDER BY a.employees.size DESC", Address.class).setMaxResults(10)
                .getResultList();

        addresses.forEach(address -> {
            System.out.printf(PRINT_FORMAT,
                    address.getText(),
                    address.getTown() == null
                            ? "Uknown" : address.getTown().getName(),
                    address.getEmployees().size());
        });
    }
}
