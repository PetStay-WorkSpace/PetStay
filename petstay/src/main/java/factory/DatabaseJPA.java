package factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseJPA {
    
    private static DatabaseJPA INSTANCE = null;
    private EntityManagerFactory factory;
    
    public static DatabaseJPA getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseJPA();
        }
        
        return INSTANCE;
    }
    
    private DatabaseJPA() {
        factory = Persistence.createEntityManagerFactory("petstay");
    }
    
    public EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
    
    public void closeFactory() {
        if (this.factory != null && this.factory.isOpen()) {
            this.factory.close();
        }
    }


}

