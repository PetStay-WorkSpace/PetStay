package model.dao;

import factory.Persistencia;
import model.Fatura;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FaturaDAO implements IDao<Fatura> {

    protected Connection connection;
    private PreparedStatement statement;
    private String sql;

    public FaturaDAO() {
        this.sql = "";
    }

    @Override
    public void save(Fatura f) {
        sql = "INSERT INTO fatura (data_emissao, valor_pagamento, metodo_pagamento, status) VALUES (?, ?, ?, ?)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);

      
            statement.setTimestamp(1, Timestamp.valueOf(f.getData_emissao()));
            statement.setDouble(2, f.getValor_pagamento());
            statement.setString(3, f.getMetodo_pagamento());
            statement.setString(4, f.getStatus());

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar Fatura: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    @Override
    public boolean delete(Fatura f) {
        sql = "DELETE FROM fatura WHERE id_fatura = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, f.getId_fatura());
            int linhas = statement.executeUpdate();
            statement.close();
            return linhas > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir Fatura: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
    }

    @Override
    public Fatura find(Fatura f) {
        Fatura resultado = null;
        sql = "SELECT * FROM fatura WHERE id_fatura = ?";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, f.getId_fatura());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                resultado = new Fatura(
                        rs.getInt("id_fatura"),
                        rs.getTimestamp("data_emissao").toLocalDateTime(),
                        rs.getDouble("valor_pagamento"),
                        rs.getString("metodo_pagamento"),
                        rs.getString("status")
                );
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Fatura: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
        return resultado;
    }

    @Override
    public List<Fatura> findAll() {
        List<Fatura> list = new ArrayList<>();
        sql = "SELECT * FROM fatura ORDER BY data_emissao DESC";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Fatura f = new Fatura(
                        rs.getInt("id_fatura"),
                        rs.getTimestamp("data_emissao").toLocalDateTime(),
                        rs.getDouble("valor_pagamento"),
                        rs.getString("metodo_pagamento"),
                        rs.getString("status")
                );
                list.add(f);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Faturas: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
        return list;
    }
}
