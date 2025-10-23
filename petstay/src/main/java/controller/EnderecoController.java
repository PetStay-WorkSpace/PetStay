package controller;

import model.Endereco;
import model.dao.EnderecoDAO;

import java.util.List;

public class EnderecoController {

    private EnderecoDAO ED;

    public EnderecoController() {
        this.ED = new EnderecoDAO();
    }

    public void save(String rua, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        Endereco endereco = new Endereco(0, rua, numero, complemento, bairro, cidade, estado, cep);
        ED.save(endereco);
    }

    public boolean delete(int id_endereco) {
        Endereco endereco = new Endereco();
        endereco.setId_endereco(id_endereco);
        return ED.delete(endereco);
    }

    public Endereco find(int id_endereco) {
        Endereco endereco = new Endereco();
        endereco.setId_endereco(id_endereco);
        return ED.find(endereco);
    }

    public List<Endereco> findAll() {
        return ED.findAll();
    }
}
