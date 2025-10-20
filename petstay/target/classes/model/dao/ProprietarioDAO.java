package model.dao;

import factory.Persistencia;
import model.Proprietario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lohra
 */
public class ProprietarioDAO implements IDao {

    protected Connection connection;
    private PreparedStatement statement;
    private String sql;

    public ProprietarioDAO() {
        this.sql = "";
    }

    @Override
    public void save(Object obj) {
        Proprietario proprietario = (Proprietario) obj;

        sql = "INSERT INTO proprietario(nome, email, telefone, senha, cpf, ativo) VALUES (?,?,?,?,?,?)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, proprietario.getNome());
            statement.setString(2, proprietario.getEmail());
            statement.setString(3, proprietario.getTelefone());
            statement.setString(4, proprietario.getSenha());
            statement.setString(5, proprietario.getCpf());
            statement.setBoolean(6, proprietario.isAtivo());

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar Proprietário: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    @Override
    public boolean delete(Object obj) {
        Proprietario proprietario = (Proprietario) obj;
        sql = "DELETE FROM proprietario WHERE id = ?";

        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, proprietario.getId());

            int linhasAfetadas = statement.executeUpdate();
            statement.close();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir Proprietário: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    @Override
    public Object find(Object obj) {
        Proprietario proprietario = (Proprietario) obj;
        Proprietario resultado = null;

        sql = "SELECT * FROM proprietario WHERE id = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, proprietario.getId());
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
    public List<Object> findAll() {
        List<Object> list = new ArrayList<>();

        sql = "SELECT * FROM proprietario ORDER BY UPPER(nome)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Proprietario proprietario = new Proprietario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("senha"),
                        rs.getString("cpf"),
                        rs.getBoolean("ativo")
                );
                list.add(proprietario);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Proprietários: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }

        return list;
    }
}
