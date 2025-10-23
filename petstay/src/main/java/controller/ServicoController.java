package controller;

import model.Servico;
import model.dao.ServicoDAO;

import java.util.List;

public class ServicoController {

    private ServicoDAO SD;

    public ServicoController() {
        this.SD = new ServicoDAO();
    }

    public void save(String nome, String descricao, String tipo, double valor, boolean ativo) {
        Servico servico = new Servico(0, nome, descricao, tipo, valor, ativo);
        SD.save(servico);
    }

    public boolean delete(int id_servico) {
        Servico servico = new Servico();
        servico.setId_servico(id_servico);
        return SD.delete(servico);
    }

    public Servico find(int id_servico) {
        Servico servico = new Servico();
        servico.setId_servico(id_servico);
        return SD.find(servico);
    }

    public List<Servico> findAll() {
        return SD.findAll();
    }
}
