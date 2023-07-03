import Utils.Utils;
import entities.Project;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;

public class FindTheLatest10Projects {
    private static final String PROJECT = "Project name: %s%n";
    private static final String DESCRIPTION = "Project Description: %s%n";
    private static final String START_DATE = "Project Start Date:%s%n";
    private static final String END_DATE = "Project End Date: %s%n";
    public static void main(String[] args) {

        EntityManager manager = Utils.createEntityManager();

        List<Project> resultList = manager.createQuery("SELECT p FROM Project p ORDER BY p.startDate DESC, p.name", Project.class)
                .setMaxResults(10).getResultList();

        resultList.stream().sorted(Comparator.comparing(Project::getName)).forEach(project -> {
            System.out.printf(PROJECT, project.getName());
            System.out.printf(DESCRIPTION, project.getDescription());
            System.out.printf(START_DATE, project.getStartDate());
            System.out.printf(END_DATE, project.getEndDate());
        });
    }
}
