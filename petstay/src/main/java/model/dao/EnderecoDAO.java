package model.dao;

import factory.Persistencia;
import model.Endereco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDAO implements IDao<Endereco> {

    protected Connection connection;
    private PreparedStatement statement;
    private String sql;

    public EnderecoDAO() {
        this.sql = "";
    }

    @Override
    public void save(Endereco e) {
        sql = "INSERT INTO endereco (rua, numero, complemento, bairro, cidade, estado, cep) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, e.getRua());
            statement.setString(2, e.getNumero());
            statement.setString(3, e.getComplemento());
            statement.setString(4, e.getBairro());
            statement.setString(5, e.getCidade());
            statement.setString(6, e.getEstado());
            statement.setString(7, e.getCep());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar Endereco: " + ex.getMessage(), ex);
        } finally {
            try { if(statement != null) statement.close(); } catch(SQLException ignored) {}
            Persistencia.closeConnection();
        }
    }

    @Override
    public boolean delete(Endereco e) {
        sql = "DELETE FROM endereco WHERE id_endereco = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, e.getId_endereco());
            int linhas = statement.executeUpdate();
            return linhas > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao excluir Endereco: " + ex.getMessage(), ex);
        } finally {
            try { if(statement != null) statement.close(); } catch(SQLException ignored) {}
            Persistencia.closeConnection();
        }
    }

    
    @Override
    public Endereco find(Endereco e) {
        return findById(e.getId_endereco());
    }

    public Endereco findById(int id_endereco) {
        Endereco endereco = null;
        sql = "SELECT * FROM endereco WHERE id_endereco = ?";
        ResultSet rs = null;
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id_endereco);
            rs = statement.executeQuery();
            if (rs.next()) {
                endereco = new Endereco(
                        rs.getInt("id_endereco"),
                        rs.getString("rua"),
                        rs.getString("numero"),
                        rs.getString("complemento"),
                        rs.getString("bairro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("cep")
                );
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao buscar Endereco por ID: " + ex.getMessage(), ex);
        } finally {
            try { if(rs != null) rs.close(); } catch(SQLException ignored) {}
            try { if(statement != null) statement.close(); } catch(SQLException ignored) {}
            Persistencia.closeConnection();
        }
        return endereco;
    }

    @Override
    public List<Endereco> findAll() {
        List<Endereco> lista = new ArrayList<>();
        sql = "SELECT * FROM endereco ORDER BY UPPER(cidade)";
        ResultSet rs = null;
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while (rs.next()) {
                Endereco e = new Endereco(
                        rs.getInt("id_endereco"),
                        rs.getString("rua"),
                        rs.getString("numero"),
                        rs.getString("complemento"),
                        rs.getString("bairro"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("cep")
                );
                lista.add(e);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao listar Enderecos: " + ex.getMessage(), ex);
        } finally {
            try { if(rs != null) rs.close(); } catch(SQLException ignored) {}
            try { if(statement != null) statement.close(); } catch(SQLException ignored) {}
            Persistencia.closeConnection();
        }
        return lista;
    }
}
