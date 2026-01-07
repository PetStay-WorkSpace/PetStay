package model.dao;

import model.Proprietario;

import java.util.List;
import javax.persistence.EntityManager;


/**
 * @author lohra
 */
public class ProprietarioDAO implements IDao<Proprietario> {

    private final EntityManager entityManager;

    public ProprietarioDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Proprietario p) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(p);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } 
    }

    @Override
    public void delete(Proprietario p) {
        try {
            entityManager.getTransaction().begin();
            Proprietario managed = entityManager.find(Proprietario.class, p.getId());
            if(managed != null) {
                entityManager.remove(managed);
            }
           
            entityManager.getTransaction().commit();
        } catch(Exception e){
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Proprietario find(int id) {
        return entityManager.find(Proprietario.class, id);
    }

    @Override
    public List<Proprietario> findAll() {
        String jpql = "SELECT p FROM Proprietario p ORDER BY p.id ASC";
        return entityManager.createQuery(jpql, Proprietario.class).getResultList();
    }

    public Proprietario findByEmail(String email) {
        try {
            return entityManager.createQuery(
                    "SELECT p FROM Proprietario p WHERE p.email = :email", Proprietario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null; 
        }
    }

    public Proprietario findByCpf(String cpf) {
        try {
            return entityManager.createQuery(
                    "SELECT p FROM Proprietario p WHERE p.cpf = :cpf", Proprietario.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (Exception e) {
            return null; 
        }
    }

}

