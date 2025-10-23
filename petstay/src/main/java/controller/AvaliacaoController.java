package controller;

import model.Avaliacao;
import model.dao.AvaliacaoDAO;

import java.time.LocalDateTime;
import java.util.List;


public class AvaliacaoController {

    private AvaliacaoDAO AD;

    public AvaliacaoController() {
        this.AD = new AvaliacaoDAO();
    }

    public void save(int id_hotel, double nota, String comentarios) {
        Avaliacao avaliacao = new Avaliacao(id_hotel, 0, nota, comentarios, LocalDateTime.now());
        AD.save(avaliacao);
    }

    public boolean delete(int id_avaliacao) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId_avaliacao(id_avaliacao);
        return AD.delete(avaliacao);
    }

    public Avaliacao find(int id_avaliacao) {
        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setId_avaliacao(id_avaliacao);
        return AD.find(avaliacao);
    }

    public List<Avaliacao> findAll() {
        return AD.findAll();
    }
    
    
}
