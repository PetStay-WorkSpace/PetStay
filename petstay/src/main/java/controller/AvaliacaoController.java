package controller;

import model.Avaliacao;
import model.dao.AvaliacaoDAO;
import factory.DatabaseJPA;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class AvaliacaoController {

    private final AvaliacaoDAO avaliacaoDAO;
    private final EntityManager entityManager;

    public AvaliacaoController() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.avaliacaoDAO = new AvaliacaoDAO(entityManager);
    }

    public boolean save(int idHotel, double nota, String comentarios) {
        try {
            Avaliacao avaliacao = new Avaliacao(idHotel, 0, nota, comentarios, LocalDateTime.now());
            avaliacaoDAO.save(avaliacao);
            System.out.println(" Avaliação salva com sucesso!");
            return true;
        } catch (Exception e) {
            System.err.println(" Erro ao salvar avaliação: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int idAvaliacao) {
        try {
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setId_avaliacao(idAvaliacao);
            avaliacaoDAO.delete(avaliacao);
            System.out.println("️ Avaliação removida com sucesso!");
            return true;
        } catch (Exception e) {
            System.err.println(" Erro ao deletar avaliação: " + e.getMessage());
            return false;
        }
    }

    public Avaliacao find(int idAvaliacao) {
        try {
            return avaliacaoDAO.find(idAvaliacao);
        } catch (Exception e) {
            System.err.println(" Erro ao buscar avaliação: " + e.getMessage());
            return null;
        }
    }

    public List<Avaliacao> findAll() {
        try {
            return avaliacaoDAO.findAll();
        } catch (Exception e) {
            System.err.println(" Erro ao listar avaliações: " + e.getMessage());
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
