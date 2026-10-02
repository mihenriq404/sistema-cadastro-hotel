package br.com.grupo.sistemacadastro.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Estabelece uma conexão com o banco de dados.
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost/sistema_hotel";
        //variáveis de ambiente
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        return DriverManager.getConnection(url, user, password);
    }
}
