import Utils.Utils;
import entities.Employee;

import java.math.BigDecimal;

public class EmployeesWithSalaryOver50000 {

    public static void main(String[] args) {

        Utils.createEntityManager().
                createQuery("FROM Employee WHERE salary > :amount", Employee.class)
                .setParameter("amount", new BigDecimal(50000L))
                .getResultStream().map(Employee::getFirstName).forEach(System.out::println);
    }
}
