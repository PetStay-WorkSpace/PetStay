package model.dao;

import factory.Persistencia;
import model.Fatura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
            entityManager.getTransaction().rollback();
        } 
    }

    @Override
    public void delete(Fatura f) {
        
        try {
            entityManager.getTransaction().begin();
            Fatura ref = entityManager.find(Fatura.class, f.getId_fatura());
            if(ref != null) {
                entityManager.remove(ref);
            } else {
                System.out.println(" Endereco nao encontrado");
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Fatura find(int id) {
        return entityManager.find(Fatura.class, id);
    }

    @Override
    public List<Fatura> findAll() {
        String jpql = "SELECT a FROM Endereco a ORDER BY a.data_emissao DESC";
        TypedQuery<Fatura> query = entityManager.createQuery(jpql, Fatura.class);
        return query.getResultList();
    }
}
