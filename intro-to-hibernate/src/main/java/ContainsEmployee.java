import Utils.Utils;
import entities.Employee;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class ContainsEmployee {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        EntityManager manager = Utils.createEntityManager();
        manager.getTransaction().begin();

        try {
            manager.createQuery("FROM Employee WHERE CONCAT_WS(' ', first_name, last_name) = :fullName", Employee.class)
                    .setParameter("fullName", name).getSingleResult();
            manager.getTransaction().commit();
            System.out.println("Yes");
        } catch (Exception e) {
            System.out.println("No");
        }
    }
}
