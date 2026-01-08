package model.dao;


import model.Endereco;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class EnderecoDAO implements IDao<Endereco> {

    private final EntityManager entityManager;

    public EnderecoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Endereco e) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(e);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw ex;
        }
 
    }

    @Override
    public void delete(Endereco e) {
        try {
            entityManager.getTransaction().begin();
            Endereco ref = entityManager.find(Endereco.class, e.getId_endereco());
            if(ref != null){
                entityManager.remove(ref);
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
    public Endereco find(int id) {
        return entityManager.find(Endereco.class, id);
    }

    @Override
    public List<Endereco> findAll() {
        String jpql = "SELECT e FROM Endereco e ORDER BY e.id_endereco DESC";
        return entityManager.createQuery(jpql, Endereco.class).getResultList();
    }
}
