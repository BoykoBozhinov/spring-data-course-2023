import Utils.Utils;
import entities.Employee;

import javax.persistence.EntityManager;

public class EmployeesFromDepartment {
    private static final String PRINT_FORMAT = "%s %s from %s - $%.2f%n";

    public static void main(String[] args) {

        EntityManager entityManager = Utils.createEntityManager();

        entityManager.createQuery("FROM Employee WHERE department.name = :department ORDER BY salary, id", Employee.class)
                .setParameter("department", "Research and Development").getResultList()
                .forEach(e -> System.out.printf(PRINT_FORMAT,
                        e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));
    }
}