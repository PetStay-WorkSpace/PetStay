package controller;

import model.Funcionario;
import model.dao.FuncionarioDAO;

import java.util.List;

public class FuncionarioController {

    private FuncionarioDAO FD;

    public FuncionarioController() {
        this.FD = new FuncionarioDAO();
    }

    public void save(String nome, String email, String telefone, String senha, String cpf, boolean ativo, boolean permissao) {
        Funcionario funcionario = new Funcionario(0, nome, email, telefone, senha, cpf, ativo, permissao);
        FD.save(funcionario);
    }

    public boolean delete(int id) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        return FD.delete(funcionario);
    }

    public Funcionario find(int id) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        return FD.find(funcionario);
    }

    public List<Funcionario> findAll() {
        return FD.findAll();
    }
}
