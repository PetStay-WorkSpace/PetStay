package controller;

import model.Avaliacao;
import model.dao.AvaliacaoRepository;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;

public class AvaliacaoController {

    private AvaliacaoRepository AD;
    private EntityManager em;

    public AvaliacaoController() {
        em = factory.JPAUtil.getEntityManager();
        AD = new AvaliacaoRepository(em);
    }

    public void save(int id_hotel, double nota, String comentarios) {
        Avaliacao avaliacao = new Avaliacao(id_hotel, 0, nota, comentarios, LocalDateTime.now());
        AD.save(avaliacao);
    }

    public void delete(int id_avaliacao) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId_avaliacao(id_avaliacao);
        AD.delete(avaliacao);
    }

    public Avaliacao find(int id_avaliacao) {
        return AD.find(id_avaliacao);
    }

    public List<Avaliacao> findAll() {
        return AD.findAll();
    }

    public void close() {
        if (em != null) em.close();
    }
}
