package controller;

import factory.DatabaseJPA;
import model.Proprietario;
import model.dao.ProprietarioDAO;

import javax.persistence.EntityManager;
import java.util.List;

public class ProprietarioController {

    private final ProprietarioDAO proprietarioDAO;
    private final EntityManager entityManager;

    public ProprietarioController() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.proprietarioDAO = new ProprietarioDAO(entityManager);
    }

    public boolean save(String nome, String email, String telefone, String senha, String cpf, boolean ativo) {
        try {
            Proprietario proprietario = new Proprietario(0, nome, email, telefone, senha, cpf, ativo);
            proprietarioDAO.save(proprietario);
            System.out.println("👤Proprietário salvo com sucesso!");
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao salvar proprietário: " + e.getMessage());
            return false;
        }
    }

    public List<Proprietario> findAll() {
        try {
            return proprietarioDAO.findAll();
        } catch (Exception e) {
            System.err.println("Erro ao listar proprietários: " + e.getMessage());
            return List.of();
        }
    }

    public Proprietario login(String email, String senha) {
        try {
            Proprietario proprietario = proprietarioDAO.validateLogin(email, senha);
            if (proprietario != null) {
                System.out.println("Login realizado com sucesso!");
            } else {
                System.out.println(" Email ou senha incorretos.");
            }
            return proprietario;
        } catch (Exception e) {
            System.err.println("Erro ao validar login: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            System.out.println("EntityManager fechado com sucesso.");
        }
    }
}
