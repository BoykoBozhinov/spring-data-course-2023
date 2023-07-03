import Utils.Utils;
import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class AddingNewAddressAndUpdatingTheEmployee {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String lastName = scanner.nextLine();

        EntityManager entityManager = Utils.createEntityManager();

        Employee employee = entityManager.createQuery("SELECT e FROM Employee e WHERE e.lastName = :lastName", Employee.class)
                .setParameter("lastName", lastName).getSingleResult();

        Address address = createAddress("Vitoshka 15", entityManager);

        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();
    }
    public static Address createAddress(String addressText, EntityManager entityManager) {
        Address address = new Address();
        address.setText(addressText);
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;
    }
}
