package controller;

import factory.DatabaseJPA;
import model.Funcionario;
import model.dao.FuncionarioDAO;

import javax.persistence.EntityManager;
import java.util.List;

public class FuncionarioController {

    private final FuncionarioDAO funcionarioDAO;
    private final EntityManager entityManager;

    public FuncionarioController() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.funcionarioDAO = new FuncionarioDAO(entityManager);
    }

    public boolean save(String nome, String email, String telefone, String senha, String cpf, boolean ativo, boolean permissao) {
        try {
            Funcionario funcionario = new Funcionario(0, nome, email, telefone, senha, cpf, ativo, permissao);
            funcionarioDAO.save(funcionario);
            System.out.println("👨‍? Funcionário salvo com sucesso!");
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao salvar funcionário: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            Funcionario funcionario = new Funcionario();
            funcionario.setId(id);
            funcionarioDAO.delete(funcionario);
            System.out.println("Funcionário removido com sucesso!");
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao deletar funcionário: " + e.getMessage());
            return false;
        }
    }

    public Funcionario find(int id) {
        try {
            return funcionarioDAO.find(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar funcionário: " + e.getMessage());
            return null;
        }
    }

    public List<Funcionario> findAll() {
        try {
            return funcionarioDAO.findAll();
        } catch (Exception e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
            return List.of();
        }
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            System.out.println("EntityManager fechado com sucesso.");
        }
    }
}
