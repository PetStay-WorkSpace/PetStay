package model.dao;

import model.Servico;
import javax.persistence.EntityManager;
import java.util.List;


public class ServicoDAO implements IDao<Servico>{

    private final EntityManager entityManager;

    public ServicoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public void save(Servico s) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(s);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
           if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;

        } 
    }
    
    public void update(Servico s) {
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(s);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }
    
    @Override
    public void delete(Servico s) {
        try {
            entityManager.getTransaction().begin();
            Servico managed = entityManager.find(Servico.class, s.getId_servico());
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
    public Servico find(int id){
        return entityManager.find(Servico.class, id);
    }

    @Override
    public List<Servico> findAll() {
        String jpql = "SELECT s FROM Servico s ORDER BY s.id_servico DESC";
        return entityManager.createQuery(jpql, Servico.class).getResultList();
    }
}
