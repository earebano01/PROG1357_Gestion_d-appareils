package com.exercice2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn implements AutoCloseable {

    private Connection connection;

    public Connection connect() {
        String user = "postgres";
        String password = "admin";
        String url = "jdbc:postgresql://localhost:5432/postgres";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (!tableExists(connection, "gestionapp")) {
                createTable(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion à la base de données.");
        }
        return connection;
    }

    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet tables = metadata.getTables(null, null, tableName, null);
        return tables.next();
    }

    private static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        String createTableQuery = "CREATE TABLE gestionapp ("
                + "id SERIAL PRIMARY KEY,"
                + "nom VARCHAR(50),"
                + "deviceid VARCHAR(50),"
                + "type VARCHAR(50),"
                + "typeMesure VARCHAR(50),"
                + "temperature VARCHAR(10),"
                + "typeAction VARCHAR(50),"
                + "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "status VARCHAR(10)"
                + ")";

        statement.executeUpdate(createTableQuery);
        statement.close();
        System.out.println("\nTable 'gestionapp' créée avec succès !");
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Connexion fermee avec succes.");
        }
    }
}
