package model.dao;

import factory.Persistencia;
import model.Avaliacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lohra
 */
public class ProprietarioDAO implements IDao<Avaliacao> {

    protected Connection connection;
    private PreparedStatement statement;
    private String sql;

    public ProprietarioDAO() {
        this.sql = "";
    }

    public List<Avaliacao> findByHotel(int idHotel) {
        List<Avaliacao> list = new ArrayList<>();
        sql = "SELECT * FROM avaliacao WHERE id_hotel = ? ORDER BY UPPER(comentarios)";
        try {
            connection = Persistencia.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idHotel);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Avaliacao a = new Avaliacao(
                        rs.getInt("id"),
                        rs.getInt("id_hotel"),
                        rs.getInt("id_pet"),
                        rs.getString("comentarios"),
                        rs.getInt("nota")
                );
                list.add(a);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar Avaliações: " + e.getMessage(), e);
        } finally {
            Persistencia.closeConnection();
        }
        return list;
    }
}
