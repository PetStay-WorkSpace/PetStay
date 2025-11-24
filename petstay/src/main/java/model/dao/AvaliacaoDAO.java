package model.dao;

import model.Avaliacao;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class AvaliacaoDAO implements IDao<Avaliacao> {

    private final EntityManager entityManager;

    public AvaliacaoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Override
    public void save(Avaliacao a) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(a);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
    
    public void update(Avaliacao a) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(a);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
    
    @Override
    public void delete(Avaliacao a) {
        try {
            entityManager.getTransaction().begin();
            Avaliacao ref = entityManager.find(Avaliacao.class, a.getId_avaliacao());
            if (ref != null) {
                entityManager.remove(ref);
            } else {
                System.out.println("Avaliação não encontrada para exclusão.");
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
    
    @Override
    public Avaliacao find(int id) {
        return entityManager.find(Avaliacao.class, id);
    }
    
    @Override
    public List<Avaliacao> findAll() {
        String jpql = "SELECT * FROM Avaliacao a ORDER BY a.data DESC";
        TypedQuery<Avaliacao> query = entityManager.createQuery(jpql, Avaliacao.class);
        return query.getResultList();
    }
}
