package model.dao;

import factory.Persistencia;
import model.Proprietario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lohra
 */
public class ProprietarioDAO implements IDao<Proprietario> {

    protected Connection connection;
    private PreparedStatement statement;
    private String sql;

    public ProprietarioDAO() {
        this.sql = "";
    }

    @Override
    public void save(Proprietario p) {
        sql = "INSERT INTO proprietario(nome, email, telefone, senha, cpf, ativo) VALUES (?,?,?,?,?,?)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, p.getNome());
            statement.setString(2, p.getEmail());
            statement.setString(3, p.getTelefone());
            statement.setString(4, p.getSenha());
            statement.setString(5, p.getCpf());
            statement.setBoolean(6, p.isAtivo());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar Proprietário: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    @Override
    public boolean delete(Proprietario p) {
        sql = "DELETE FROM proprietario WHERE id = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, p.getId());
            int linhas = statement.executeUpdate();
            statement.close();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir Proprietário: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    @Override
    public Proprietario find(Proprietario p) {
        Proprietario resultado = null;
        sql = "SELECT * FROM proprietario WHERE id = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, p.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                resultado = new Proprietario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getString("cpf"),
                        rs.getBoolean("ativo")
                );
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Proprietário: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
        return resultado;
    }

    @Override
    public List<Proprietario> findAll() {
        List<Proprietario> list = new ArrayList<>();
        sql = "SELECT * FROM proprietario ORDER BY UPPER(nome)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Proprietario p = new Proprietario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getString("cpf"),
                        rs.getBoolean("ativo")
                );
                list.add(p);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Proprietários: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
        return list;
    }

    public Proprietario validateLogin(String email, String senha) {
        Proprietario resultado = null;
        sql = "SELECT * FROM proprietario WHERE email = ? AND senha = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, senha);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                resultado = new Proprietario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getString("cpf"),
                        rs.getBoolean("ativo")
                );
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Proprietário por email/senha: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
        return resultado;
    }
}
