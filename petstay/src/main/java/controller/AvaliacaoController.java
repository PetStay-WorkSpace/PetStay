package controller;

import model.Avaliacao;
import model.dao.AvaliacaoDAO;
import factory.DatabaseJPA;

import javax.persistence.EntityManager;
import java.util.List;

public class AvaliacaoController {

    private final AvaliacaoDAO avaliacaoDAO;

    public AvaliacaoController() {
        EntityManager em = DatabaseJPA.getInstance().getEntityManager();
        this.avaliacaoDAO = new AvaliacaoDAO(em);
    }

    public void save(Avaliacao avaliacao) {
        avaliacaoDAO.save(avaliacao);
    }

    public void delete(int idAvaliacao) throws IllegalAccessException {
        
        Avaliacao avaliacao = avaliacaoDAO.find(idAvaliacao);
        
        if(avaliacao == null){
            throw new IllegalAccessException("Avaliação não encontrada.");
        }
        
        avaliacaoDAO.delete(avaliacao);
    }

    public Avaliacao find(int idAvaliacao) {
        return avaliacaoDAO.find(idAvaliacao);
    }

    public List<Avaliacao> findAll() {
       return avaliacaoDAO.findAll();
    }

   
}
