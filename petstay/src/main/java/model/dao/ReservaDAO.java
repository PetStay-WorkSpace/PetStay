package model.dao;

import model.Reserva;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ReservaDAO implements IDao<Reserva> {

    private final EntityManager entityManager;

    public ReservaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Reserva r) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(r);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao salvar reserva: " + e.getMessage());
        }
    }

    public void update(Reserva r) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(r);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao atualizar reserva: " + e.getMessage());
        }
    }

    @Override
    public void delete(Reserva r) {
        try {
            entityManager.getTransaction().begin();
            Reserva ref = entityManager.find(Reserva.class, r.getId_reserva());
            if (ref != null) {
                entityManager.remove(ref);
            } else {
                System.out.println("Reserva não encontrada para exclusão");
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            System.err.println("Erro ao excluir reserva: " + e.getMessage());
        }
    }

    @Override
    public Reserva find(int id) {
        return entityManager.find(Reserva.class, id);
    }

    @Override
    public List<Reserva> findAll() {
        String jpql = "SELECT r FROM Reserva r ORDER BY r.id_reserva DESC";
        TypedQuery<Reserva> query = entityManager.createQuery(jpql, Reserva.class);
        return query.getResultList();
    }

    public List<Reserva> findByModelo(int modelo) {
        try {
            String jpql = "SELECT r FROM Reserva r WHERE r.modelo = :modelo ORDER BY r.id_reserva ASC";
            TypedQuery<Reserva> query = entityManager.createQuery(jpql, Reserva.class);
            query.setParameter("modelo", modelo);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar reservas por modelo: " + e.getMessage());
            return List.of();
        }
    }
    
    public List<Reserva> findByIdPet(int idPet) {
        try {
            String jpql = "SELECT r FROM Reserva r WHERE r.id_pet = :idPet ORDER BY r.id_reserva DESC";
            TypedQuery<Reserva> query = entityManager.createQuery(jpql, Reserva.class);
            query.setParameter("idPet", idPet);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar reservas por id_pet: " + e.getMessage());
            return List.of();
        }
    }
}
