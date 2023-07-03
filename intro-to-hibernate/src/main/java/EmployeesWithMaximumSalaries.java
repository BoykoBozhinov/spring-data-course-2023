import Utils.Utils;
import entities.models.Result;


public class EmployeesWithMaximumSalaries {
    public static void main(String[] args) {
        Utils.createEntityManager()
                .createQuery("SELECT department.name, max(salary)" +
                        " FROM Employee " +
                        " GROUP BY department.name" +
                        " HAVING max(salary) NOT BETWEEN 30000 AND 70000", Object[].class)
                .getResultList()
                .forEach(objects -> System.out.println(objects[0] + " " + objects[1]));

        System.out.println(System.lineSeparator());

        // result with custom POJO
        Utils.createEntityManager()
                .createQuery("SELECT NEW entities.models.Result(department.name, MAX(salary))" +
                        " FROM Employee" +
                        " GROUP BY department.name" +
                        " HAVING MAX(salary) NOT BETWEEN 30000 AND 70000", Result.class)
                .getResultList()
                .forEach(System.out::println);
    }
}
