package model.dao;

import model.Fatura;

import java.util.List;
import javax.persistence.EntityManager;

public class FaturaDAO implements IDao<Fatura> {
    private final EntityManager entityManager;
    

    public FaturaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Fatura f) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(f);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if(entityManager.getTransaction().isActive()){
                entityManager.getTransaction().rollback();
            }
            throw e;
        } 
    }

    @Override
    public void delete(Fatura f) {
        
        try {
            entityManager.getTransaction().begin();
            Fatura managed = entityManager.find(Fatura.class, f.getId_fatura());
            if(managed != null) {
                entityManager.remove(managed);
            } 
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Fatura find(int id) {
        return entityManager.find(Fatura.class, id);
    }

    @Override
    public List<Fatura> findAll() {
        String jpql = "SELECT f FROM Fatura f ORDER BY f.data_emissao DESC";
        return entityManager.createQuery(jpql, Fatura.class).getResultList();
    }
}
