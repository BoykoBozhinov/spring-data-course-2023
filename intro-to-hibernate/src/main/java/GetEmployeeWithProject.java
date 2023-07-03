import Utils.Utils;
import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import java.util.Scanner;

public class GetEmployeeWithProject {
    private static final String PRINT_FORMAT = "%s %s - %s%n";
    public static void main(String[] args) {

       Scanner scanner = new Scanner(System.in);

        int employeeId = getEmployeeId(scanner);

        EntityManager manager = Utils.createEntityManager();

        Employee employee = manager.createQuery("FROM Employee e WHERE e.id = :emId", Employee.class)
                .setParameter("emId", employeeId).getSingleResult();

        System.out.printf(PRINT_FORMAT, employee.getFirstName(), employee.getLastName(), employee.getJobTitle());
        employee.getProjects().stream().map(Project::getName).sorted().toList().forEach(System.out::println);
    }

    private static int getEmployeeId(Scanner scanner) {
        return Integer.parseInt(scanner.nextLine());
    }
}
