package model.dao;

import factory.Persistencia;
import model.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO implements IDao<Reserva> {

    protected Connection connection;
    private PreparedStatement statement;
    private String sql;

    public ReservaDAO() {
        this.sql = "";
    }

    @Override
    public void save(Reserva r) {
        sql = "INSERT INTO reserva (id_pet, nome, raca, especie, peso, ativo) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, r.getId_pet());
            statement.setString(2, r.getNome());
            statement.setString(3, r.getRaca());
            statement.setString(4, r.getEspecie());
            statement.setDouble(5, r.getPeso());
            statement.setBoolean(6, r.isAtivo());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar Reserva: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    @Override
    public boolean delete(Reserva r) {
        sql = "DELETE FROM reserva WHERE id_reserva = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, r.getId_reserva());
            int linhas = statement.executeUpdate();
            statement.close();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir Reserva: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    @Override
    public Reserva find(Reserva r) {
        Reserva resultado = null;
        sql = "SELECT * FROM reserva WHERE id_reserva = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, r.getId_reserva());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                resultado = new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_pet"),
                        rs.getString("nome"),
                        rs.getString("raca"),
                        rs.getString("especie"),
                        rs.getDouble("peso"),
                        rs.getBoolean("ativo")
                );
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Reserva: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
        return resultado;
    }

    @Override
    public List<Reserva> findAll() {
        List<Reserva> list = new ArrayList<>();
        sql = "SELECT * FROM reserva ORDER BY UPPER(nome)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Reserva r = new Reserva(
                        rs.getInt("id_reserva"),
                        rs.getInt("id_pet"),
                        rs.getString("nome"),
                        rs.getString("raca"),
                        rs.getString("especie"),
                        rs.getDouble("peso"),
                        rs.getBoolean("ativo")
                );
                list.add(r);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Reservas: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
        return list;
    }
}
