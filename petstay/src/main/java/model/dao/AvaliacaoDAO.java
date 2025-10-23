package model.dao;

import factory.Persistencia;
import model.Avaliacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO implements IDao<Avaliacao> {

    protected Connection connection;
    private PreparedStatement statement;
    private String sql;

    public AvaliacaoDAO() {
        this.sql = "";
    }

    @Override
    public void save(Avaliacao a) {
        sql = "INSERT INTO avaliacao (id_hotel, nota, comentarios, data) VALUES (?, ?, ?, ?)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, a.getId_avaliacao()); 
            statement.setDouble(2, a.getNota());
            statement.setString(3, a.getComentarios());
            statement.setTimestamp(4, Timestamp.valueOf(a.getData()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar Avaliação: " + e.getMessage(), e);
        } finally {
            try { if(statement != null) statement.close(); } catch(SQLException ignored) {}
            Persistencia.closeConnection();
        }
    }

    @Override
    public boolean delete(Avaliacao a) {
        sql = "DELETE FROM avaliacao WHERE id_avaliacao = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, a.getId_avaliacao());
            int linhas = statement.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir Avaliação: " + e.getMessage(), e);
        } finally {
            try { if(statement != null) statement.close(); } catch(SQLException ignored) {}
            Persistencia.closeConnection();
        }
    }

    @Override
    public Avaliacao find(Avaliacao a) {
        Avaliacao resultado = null;
        sql = "SELECT * FROM avaliacao WHERE id_avaliacao = ?";
        ResultSet rs = null;
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, a.getId_avaliacao());
            rs = statement.executeQuery();
            if (rs.next()) {
                resultado = new Avaliacao(
                        rs.getInt("id_hotel"),
                        rs.getInt("id_avaliacao"),
                        rs.getDouble("nota"),
                        rs.getString("comentarios"),
                        rs.getTimestamp("data").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Avaliação: " + e.getMessage(), e);
        } finally {
            try { if(rs != null) rs.close(); } catch(SQLException ignored) {}
            try { if(statement != null) statement.close(); } catch(SQLException ignored) {}
            Persistencia.closeConnection();
        }
        return resultado;
    }

    @Override
    public List<Avaliacao> findAll() {
        List<Avaliacao> list = new ArrayList<>();
        sql = "SELECT * FROM avaliacao ORDER BY data DESC";
        ResultSet rs = null;
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                Avaliacao a = new Avaliacao(
                        rs.getInt("id_hotel"),
                        rs.getInt("id_avaliacao"),
                        rs.getDouble("nota"),
                        rs.getString("comentarios"),
                        rs.getTimestamp("data").toLocalDateTime()
                );
                list.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Avaliações: " + e.getMessage(), e);
        } finally {
            try { if(rs != null) rs.close(); } catch(SQLException ignored) {}
            try { if(statement != null) statement.close(); } catch(SQLException ignored) {}
            Persistencia.closeConnection();
        }
        return list;
    }
}
