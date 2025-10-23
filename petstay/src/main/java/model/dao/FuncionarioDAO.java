package model.dao;

import factory.Persistencia;
import model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO implements IDao<Funcionario> {

    protected Connection connection;
    private PreparedStatement statement;
    private String sql;

    public FuncionarioDAO() {
        this.sql = "";
    }

    @Override
    public void save(Funcionario f) {
        sql = "INSERT INTO funcionario (nome, email, telefone, senha, cpf, ativo, permissao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, f.getNome());
            statement.setString(2, f.getEmail());
            statement.setString(3, f.getTelefone());
            statement.setString(4, f.getSenha());
            statement.setString(5, f.getCpf());
            statement.setBoolean(6, f.isAtivo());
            statement.setBoolean(7, f.isPermissao());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar Funcionario: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    @Override
    public boolean delete(Funcionario f) {
        sql = "DELETE FROM funcionario WHERE id = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, f.getId());
            int linhas = statement.executeUpdate();
            statement.close();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir Funcionario: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    @Override
    public Funcionario find(Funcionario f) {
        Funcionario resultado = null;
        sql = "SELECT * FROM funcionario WHERE id = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, f.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                resultado = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getString("cpf"),
                        rs.getBoolean("ativo"),
                        rs.getBoolean("permissao")
                );
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Funcionario: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
        return resultado;
    }

    @Override
    public List<Funcionario> findAll() {
        List<Funcionario> list = new ArrayList<>();
        sql = "SELECT * FROM funcionario ORDER BY UPPER(nome)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Funcionario f = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getString("cpf"),
                        rs.getBoolean("ativo"),
                        rs.getBoolean("permissao")
                );
                list.add(f);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Funcionarios: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
        return list;
    }
}
