import Utils.Utils;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ChangeCasing {
    public static void main(String[] args) {

        EntityManager manager = Utils.createEntityManager();

        manager.getTransaction().begin();
        Query query = manager.createQuery("UPDATE Town t SET t.name = UPPER(t.name) WHERE CHAR_LENGTH(t.name) < 5");
        query.executeUpdate();
        manager.getTransaction().commit();
    }
}
