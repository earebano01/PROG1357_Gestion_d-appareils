package com.exercice2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn {    
    
    public Connection connect() {
        Connection connection = null;
        
        String user = "postgres";
        String password = "admin";
        String url = "jdbc:postgresql://localhost:5432/postgres";
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            if (!tableExiste(connection, "gestionapp")) {
                creerTable(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion a la base de donnees.");
        }
        return connection;
    }

    private static boolean tableExiste(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();

        ResultSet tables = metadata.getTables(null, null, tableName, null);

        return tables.next();
    }

    private static void creerTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
    
        String creerTableRequete = "CREATE TABLE gestionapp ("
                + "id SERIAL PRIMARY KEY,"
                + "nom VARCHAR(50),"
                + "deviceid VARCHAR(50),"
                + "type VARCHAR(50),"
                + "typeMesure VARCHAR(50),"     // ajouter pour l'heritage capteur
                + "typeAction VARCHAR(50),"     // ajouter pour l'heritage actuator
                + "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                + "status VARCHAR(10)"
                + ")";
    
        statement.executeUpdate(creerTableRequete);
        statement.close();
        System.out.println("Table 'gestionapp' creee avec succes !");
    }
        
}
