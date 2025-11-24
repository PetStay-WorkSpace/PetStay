package controller;

import model.Fatura;
import model.dao.FaturaDAO;
import factory.DatabaseJPA;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class FaturaController {

    private final FaturaDAO faturaDAO;
    private final EntityManager entityManager;

    public FaturaController() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.faturaDAO = new FaturaDAO(entityManager);
    }

    public boolean save(LocalDateTime dataEmissao, double valorPagamento, String metodoPagamento, String status) {
        try {
            Fatura fatura = new Fatura(0, dataEmissao, valorPagamento, metodoPagamento, status);
            faturaDAO.save(fatura);
            System.out.println("Fatura salva com sucesso!");
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao salvar fatura: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int idFatura) {
        try {
            Fatura fatura = new Fatura();
            fatura.setId_fatura(idFatura);
            faturaDAO.delete(fatura);
            System.out.println(" Fatura removida com sucesso!");
            return true;
        } catch (Exception e) {
            System.err.println(" Erro ao deletar fatura: " + e.getMessage());
            return false;
        }
    }

    public Fatura find(int idFatura) {
        try {
            return faturaDAO.find(idFatura);
        } catch (Exception e) {
            System.err.println(" Erro ao buscar fatura: " + e.getMessage());
            return null;
        }
    }

    public List<Fatura> findAll() {
        try {
            return faturaDAO.findAll();
        } catch (Exception e) {
            System.err.println(" Erro ao listar faturas: " + e.getMessage());
            return List.of(); 
        }
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            System.out.println(" EntityManager fechado com sucesso.");
        }
    }
}
