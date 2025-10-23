package controller;

import model.Avaliacao;
import model.dao.AvaliacaoDAO;

import java.util.List;

/**
 * @author lohra
 */
public class AvaliacoesController {

    private AvaliacoesDAO AD;

    public AvaliacoesController() {
        this.AD = new AvaliacoesDAO();
    }

    public void save(int idProprietario, int idPet, String comentario, int nota) {
        Avaliacoes avaliacao = new Avaliacoes(0, idProprietario, idPet, comentario, nota);
        AD.save(avaliacao);
    }

    public List<Avaliacoes> findByHotel(int idHotel) {
        return AD.findByHotel(idHotel);
    }

    public List<Avaliacoes> findAll() {
        return AD.findAll();
    }

    public Avaliacoes findById(int id) {
        return AD.findById(id);
    }
}