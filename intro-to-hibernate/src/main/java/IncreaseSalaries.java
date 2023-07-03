import Utils.Utils;
import entities.Employee;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

public class IncreaseSalaries {
    public static void main(String[] args) {

        EntityManager manager = Utils.createEntityManager();

        manager.getTransaction().begin();

        manager.createQuery("UPDATE Employee e SET e.salary = salary * 1.2" +
                "WHERE e.department.id IN :iDs").setParameter("iDs", Set.of(1, 2, 4, 11)).executeUpdate();

        manager.getTransaction().commit();

        List<Employee> employees = manager.createQuery("SELECT e FROM Employee e WHERE e.department.id IN :iDs", Employee.class)
                .setParameter("iDs", Set.of(1, 2, 4, 11)).getResultList();

        employees.forEach(employee -> {
            System.out.printf("%s %s ($%.2f)%n", employee.getFirstName(), employee.getLastName(), employee.getSalary());
        });
    }
}
