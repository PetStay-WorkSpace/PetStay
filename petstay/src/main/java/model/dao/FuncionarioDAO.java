package model.dao;

import model.Funcionario;
import java.util.List;
import javax.persistence.EntityManager;

public class FuncionarioDAO implements IDao<Funcionario> {

    private EntityManager entityManager;

    public FuncionarioDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Funcionario f) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(f);
            entityManager.getTransaction().commit();
        
        } catch (Exception e) {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } 
    }

    @Override
    public void delete(Funcionario f) {
        try {
            entityManager.getTransaction().begin();
            Funcionario managed = entityManager.find(Funcionario.class, f.getId());
            if(managed != null){
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
    public Funcionario find(int id) {
        return entityManager.find(Funcionario.class, id);
    }

    @Override
    public List<Funcionario> findAll() {
        String jpql = "SELECT f FROM Funcionario f ORDER BY f.nome DESC";
        return entityManager.createQuery(jpql, Funcionario.class).getResultList();
    }
}
