package controller;

import factory.DatabaseJPA;
import model.Endereco;
import model.dao.EnderecoDAO;

import java.util.List;
import javax.persistence.EntityManager;

public class EnderecoController {
    
    private final EnderecoDAO enderecoDAO;

    public EnderecoController() {
        EntityManager em = DatabaseJPA.getInstance().getEntityManager();
        this.enderecoDAO = new EnderecoDAO(em);
    }

    public void save(Endereco endereco) {
        
        if (endereco == null) {
            throw new IllegalArgumentException("Endereço inválido.");
        }

        if (endereco.getRua() == null || endereco.getRua().isBlank()) {
            throw new IllegalArgumentException("Rua é obrigatória.");
        }

        if (endereco.getNumero() == null || endereco.getNumero().isBlank()) {
            throw new IllegalArgumentException("Número é obrigatório.");
        }

        if (endereco.getCidade() == null || endereco.getCidade().isBlank()) {
            throw new IllegalArgumentException("Cidade é obrigatória.");
        }

        if (endereco.getEstado() == null || endereco.getEstado().isBlank()) {
            throw new IllegalArgumentException("Estado é obrigatório.");
        }

        if (endereco.getCep() == null || endereco.getCep().isBlank()) {
            throw new IllegalArgumentException("CEP é obrigatório.");
        }

        endereco.setCep(endereco.getCep().replaceAll("\\D", ""));
        endereco.setEstado(endereco.getEstado().toUpperCase().trim());

        enderecoDAO.save(endereco);
    }

    public void delete(int id_endereco) {
        Endereco endereco = enderecoDAO.find(id_endereco);
        
        if(endereco == null) {
            throw new IllegalArgumentException("Endereço não encontrado");
        }
        enderecoDAO.delete(endereco);
    }

    public Endereco find(int id_endereco) {
        return enderecoDAO.find(id_endereco);
    }

    public List<Endereco> findAll() {
        return enderecoDAO.findAll();
    }
    
}
