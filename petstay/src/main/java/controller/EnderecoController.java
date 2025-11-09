package controller;

import factory.DatabaseJPA;
import model.Endereco;
import model.dao.EnderecoDAO;

import java.util.List;
import javax.persistence.EntityManager;

public class EnderecoController {
    
    private final EnderecoDAO enderecoDAO;
    private final EntityManager entityManager;


    public EnderecoController() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.enderecoDAO = new EnderecoDAO(entityManager);
    }

    public boolean save(String rua, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        try {
            Endereco endereco = new Endereco(0, rua, numero, complemento, bairro, cidade, estado, cep);
            enderecoDAO.save(endereco);
            System.out.println(" Endereco salvo com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println(" Erro ao salvar endereco" + e.getMessage());
            return  false;
        }
    }

    public boolean delete(int id_endereco) {
        try {
            Endereco endereco = new Endereco();
            endereco.setId_endereco(id_endereco);
            enderecoDAO.delete(endereco);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao deletar" + e.getMessage());
            return false;
        }
    }

    public Endereco find(int id_endereco) {
        return enderecoDAO.find(id_endereco);
    }

    public List<Endereco> findAll() {
        return enderecoDAO.findAll();
    }
    
    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            System.out.println(" EntityManager fechado com sucesso.");
        }
    }
}
