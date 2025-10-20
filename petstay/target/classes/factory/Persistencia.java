package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lohra
 */

public class Persistencia {

    private static Connection connection;
    private static final String URL = "jdbc:sqlite:petstay.db";

    private Persistencia() {

    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Conexao com o banco foi encerrada.");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar a conexao: " + e.getMessage(), e);
            } finally {
                connection = null;
            }
        }
    }
}
