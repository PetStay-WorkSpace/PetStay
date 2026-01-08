package controller;

import factory.DatabaseJPA;
import model.Funcionario;
import model.dao.FuncionarioDAO;

import javax.persistence.EntityManager;
import java.util.List;
import security.PasswordUtil;

public class FuncionarioController {

    private final FuncionarioDAO funcionarioDAO;

    public FuncionarioController() {
        EntityManager em = DatabaseJPA.getInstance().getEntityManager();
        this.funcionarioDAO = new FuncionarioDAO(em);
    }

    public void save(Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário inválido.");
        }

        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        if (funcionario.getEmail() == null || funcionario.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório.");
        }

        if (funcionario.getCpf() == null || funcionario.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório.");
        }
        
        funcionario.setEmail(funcionario.getEmail().toLowerCase().trim());
        funcionario.setCpf(funcionario.getCpf().replaceAll("\\D", ""));
        
        String senhaHash = PasswordUtil.criptografar(funcionario.getSenha());
        funcionario.setSenha(senhaHash);

        funcionarioDAO.save(funcionario);
    }

    public void delete(int idFuncionario) {
        
        Funcionario funcionario = funcionarioDAO.find(idFuncionario);
        
        if (funcionario == null){
            throw new IllegalArgumentException("Funcionário não encontrado");
        }
        
        funcionarioDAO.delete(funcionario);
    }

    public Funcionario find(int idFuncionario) {
        return funcionarioDAO.find(idFuncionario);
    }

    public List<Funcionario> findAll() {
        return funcionarioDAO.findAll();
    }

}
