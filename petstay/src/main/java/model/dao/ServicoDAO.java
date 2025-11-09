package model.dao;

import model.Servico;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
            entityManager.getTransaction().rollback();
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
            Servico ref = entityManager.find(Servico.class, s.getId_servico());
            if(ref != null) {
                entityManager.remove(ref);
            } else {
                System.out.println("Serviço não encontrado para exclusão");
            }
            entityManager.getTransaction().commit();
                
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
  
    }

    @Override
    public Servico find(int id){
        return entityManager.find(Servico.class, id);
    }

    @Override
    public List<Servico> findAll() {
        String jpql = "SELECT s FROM Servico s ORDER BY s.id_servico DESC";
        TypedQuery<Servico> query = entityManager.createQuery(jpql, Servico.class);
        return query.getResultList();
    }
}
