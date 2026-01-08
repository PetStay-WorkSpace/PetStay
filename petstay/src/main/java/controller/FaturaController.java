package controller;

import model.Fatura;
import model.dao.FaturaDAO;
import factory.DatabaseJPA;

import javax.persistence.EntityManager;
import java.util.List;

public class FaturaController {

    private final FaturaDAO faturaDAO;

    public FaturaController() {
        EntityManager em = DatabaseJPA.getInstance().getEntityManager();
        this.faturaDAO = new FaturaDAO(em);
    }

    public void save(Fatura fatura) {
        if (fatura == null) {
            throw new IllegalArgumentException("Fatura inválida.");
        }

        if (fatura.getStatus() == null || fatura.getStatus().isBlank()) {
            fatura.setStatus("PENDENTE");
        }
        
        fatura.setStatus(fatura.getStatus().toUpperCase().trim());
        
        faturaDAO.save(fatura);
    }

    public void delete(int idFatura) {
        Fatura fatura = faturaDAO.find(idFatura);

        if (fatura == null) {
            throw new IllegalArgumentException("Fatura não encontrada.");
        }

        faturaDAO.delete(fatura);
    }

    public Fatura find(int idFatura) {
       return faturaDAO.find(idFatura);
    }

    public List<Fatura> findAll() {
        return faturaDAO.findAll();
    }

}
