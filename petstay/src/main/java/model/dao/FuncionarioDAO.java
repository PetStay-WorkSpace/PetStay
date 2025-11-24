package model.dao;

import model.Funcionario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
            entityManager.getTransaction().rollback();
            throw e;
        } 
    }

    @Override
    public void delete(Funcionario f) {
        try {
            entityManager.getTransaction().begin();
            Funcionario ref = entityManager.find(Funcionario.class, f.getId());
            if(ref != null){
                entityManager.remove(f);
            } else {
                System.out.println(" Funcionario nao encontrado");
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } 
    }

    @Override
    public Funcionario find(int id) {
        return entityManager.find(Funcionario.class, id);
    }

    @Override
    public List<Funcionario> findAll() {
        String jpql = "SELECT * FROM Avaliacao a ORDER BY a.nome DESC";
        TypedQuery<Funcionario> query = entityManager.createQuery(jpql, Funcionario.class);
        return query.getResultList();
    }
}
