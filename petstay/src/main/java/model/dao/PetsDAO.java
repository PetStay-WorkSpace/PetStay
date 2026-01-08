package model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import model.Pets;

public class PetsDAO implements IDao<Pets> {

    private final EntityManager entityManager;

    public PetsDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Pets p) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(p);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw ex;
        }
    }

    @Override
    public void delete(Pets p) {
        try {
            entityManager.getTransaction().begin();
            Pets managed = entityManager.find(Pets.class, p.getId());
            if (managed != null) {
                entityManager.remove(managed);
            } 
            entityManager.getTransaction().commit();

        } catch (Exception ex) {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw ex;
        }
    }

    @Override
    public Pets find(int id) {
        return entityManager.find(Pets.class, id);
    }

    @Override
    public List<Pets> findAll() {
        String jpql = "SELECT p FROM Pets p ORDER BY p.id DESC";
        return entityManager.createQuery(jpql, Pets.class).getResultList();
    }
}
