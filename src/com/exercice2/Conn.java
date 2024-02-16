package com.exercice2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn implements AutoCloseable {

    private Connection connection;

    static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    static final String USER = "postgres";
    static final String PASS = "admin";

    public Conn() {
        try {
            createDatabase();
        } catch (SQLException e) {
            // e.printStackTrace();
            // System.out.println("La base de données 'PROJET_GESTIONAPP' existe !");
        }
    }

    private void createDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement state = conn.createStatement()) {
            String sql = "CREATE DATABASE PROJET_GESTIONAPP";
            state.executeUpdate(sql);
            System.out.println("La base de données 'PROJET_GESTIONAPP' a été créée avec succès !");
        }
    }

    public Connection connect() {
        String user = "postgres";
        String password = "admin";
        String url = "jdbc:postgresql://localhost:5432/projet_gestionapp";

        try {
            connection = DriverManager.getConnection(url, user, password);
            String[] tablesToCheck = {"ObjetConnecte", "Capteur", "Actionneur"};
            if (!tablesExist(connection, tablesToCheck)) {
                createTables(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de connexion à la base de données.");
        }
        return connection;
    }

    private boolean tablesExist(Connection connection, String[] tableNames) throws SQLException {
        try (Statement state = connection.createStatement()) {
            for (String tableName : tableNames) {
                state.execute("SELECT 1 FROM " + tableName + " LIMIT 1");
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private void createTables(Connection connection) throws SQLException {
        try (Statement state = connection.createStatement()) {
            state.execute("CREATE TABLE ObjetConnecte ("
                    + "objet_id SERIAL PRIMARY KEY,"
                    + "nom VARCHAR(255),"
                    + "device_id VARCHAR(50),"
                    + "type VARCHAR(50),"
                    + "typemesure VARCHAR(50),"
                    + "typeaction VARCHAR(50)"
                    + ")");
    
            state.execute("CREATE TABLE Capteur ("
                    + "capteur_id SERIAL PRIMARY KEY,"
                    + "objet_id INT,"
                    + "status VARCHAR(10),"
                    + "temperature DECIMAL(10, 2),"
                    + "humidite DECIMAL(10, 2),"
                    + "son DECIMAL(10, 2),"
                    + "distance DECIMAL(10, 2),"
                    + "lumiere DECIMAL(10, 2),"
                    + "formatted_date VARCHAR(50),"
                    + "formatted_time VARCHAR(50),"
                    + "FOREIGN KEY (objet_id) REFERENCES ObjetConnecte(objet_id)"
                    + ")");
    
            state.execute("ALTER TABLE capteur DROP CONSTRAINT IF EXISTS capteur_objet_id_fkey");
            state.execute("ALTER TABLE capteur ADD CONSTRAINT capteur_objet_id_fkey "
                    + "FOREIGN KEY (objet_id) REFERENCES ObjetConnecte(objet_id) ON DELETE CASCADE");
            
            state.execute("CREATE TABLE Actionneur ("
                    + "actionneur_id SERIAL PRIMARY KEY,"
                    + "objet_id INT,"
                    + "status VARCHAR(10),"
                    + "formatted_date VARCHAR(50),"
                    + "formatted_time VARCHAR(50),"
                    + "FOREIGN KEY (objet_id) REFERENCES ObjetConnecte(objet_id)"
                    + ")");

            state.execute("ALTER TABLE actionneur DROP CONSTRAINT IF EXISTS actionneur_objet_id_fkey");
            state.execute("ALTER TABLE actionneur ADD CONSTRAINT actionneur_objet_id_fkey "
                    + "FOREIGN KEY (objet_id) REFERENCES ObjetConnecte(objet_id) ON DELETE CASCADE");
        }
    
        catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la création des tables.");
            }
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Connexion fermée avec succès.");
        }
    }
}
