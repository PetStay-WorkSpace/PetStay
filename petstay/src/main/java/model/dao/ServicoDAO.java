package model.dao;

import factory.Persistencia;
import model.Servico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServicoDAO implements IDao<Servico> {

    protected Connection connection;
    private PreparedStatement statement;
    private String sql;

    public ServicoDAO() {
        this.sql = "";
    }

    @Override
    public void save(Servico s) {
        sql = "INSERT INTO servicos (nome, descricao, tipo, valor, ativo) VALUES (?, ?, ?, ?, ?)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, s.getNome());
            statement.setString(2, s.getDescricao());
            statement.setString(3, s.getTipo());
            statement.setDouble(4, s.getValor());
            statement.setBoolean(5, s.isAtivo());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar Serviço: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    @Override
    public boolean delete(Servico s) {
        sql = "DELETE FROM servicos WHERE id_servico = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, s.getId_servico());
            int linhas = statement.executeUpdate();
            statement.close();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir Serviço: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    @Override
    public Servico find(Servico s) {
        Servico resultado = null;
        sql = "SELECT * FROM servicos WHERE id_servico = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, s.getId_servico());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                resultado = new Servico(
                        rs.getInt("id_servico"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("tipo"),
                        rs.getDouble("valor"),
                        rs.getBoolean("ativo")
                );
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Serviço: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
        return resultado;
    }

    @Override
    public List<Servico> findAll() {
        List<Servico> list = new ArrayList<>();
        sql = "SELECT * FROM servicos ORDER BY UPPER(nome)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Servico s = new Servico(
                        rs.getInt("id_servico"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("tipo"),
                        rs.getDouble("valor"),
                        rs.getBoolean("ativo")
                );
                list.add(s);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Serviços: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
        return list;
    }
}
