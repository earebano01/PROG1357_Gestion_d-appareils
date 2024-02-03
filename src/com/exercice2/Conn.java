// package com.exercice2;

// import java.sql.Connection;
// import java.sql.DatabaseMetaData;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
 
//     public class Conn implements AutoCloseable {   
    
//     private Connection connection;

//     public Connection connect() {
//         String user = "postgres";
//         String password = "admin";
//         String url = "jdbc:postgresql://localhost:5432/postgres";

//         try {
//             connection = DriverManager.getConnection(url, user, password);
//             if (!tableExists(connection, "gestionapp")) {
//                 createTable(connection);
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//             System.out.println("Erreur de connexion à la base de données.");
//         }
//         return connection;
//     }

//     private static boolean tableExists(Connection connection, String tableName) throws SQLException {
//         DatabaseMetaData metadata = connection.getMetaData();
//         ResultSet tables = metadata.getTables(null, null, tableName, null);
//         return tables.next();
//     }

//     private static void createTable(Connection connection) throws SQLException {
//         Statement statement = connection.createStatement();

//         String createTableQuery = "CREATE TABLE gestionapp ("
//                 + "id SERIAL PRIMARY KEY,"
//                 + "nom VARCHAR(50),"
//                 + "deviceid VARCHAR(50),"
//                 + "type VARCHAR(50),"
//                 + "typeMesure VARCHAR(50),"
//                 + "temperature VARCHAR(10),"
//                 + "humidity VARCHAR(10),"
//                 + "typeAction VARCHAR(50),"
//                 + "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
//                 + "status VARCHAR(10)"
//                 + ")";

//         statement.executeUpdate(createTableQuery);
//         statement.close();
//         System.out.println("\nTable 'gestionapp' créée avec succès !");
//     }

//     @Override
//     public void close() throws Exception {
//         if (connection != null && !connection.isClosed()) {
//             connection.close();
//             System.out.println("Connexion fermee avec succes.");
//         }
//     }
// }

package com.exercice2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
            System.out.println("La base de données 'PROJET_GESTIONAPP' existe !");
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

    // public Connection connect() {
    //     String user = "postgres";
    //     String password = "admin";
    //     String url = "jdbc:postgresql://localhost:5432/projet_gestionapp";

    //     try {
    //         connection = DriverManager.getConnection(url, user, password);
    //         if (!tableExists(connection, "gestionapp")) {
    //             createTable(connection);
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         System.out.println("Erreur de connexion à la base de données.");
    //     }
    //     return connection;
    // }

    // private static boolean tableExists(Connection connection, String tableName) throws SQLException {
    //     DatabaseMetaData metadata = connection.getMetaData();
    //     ResultSet tables = metadata.getTables(null, null, tableName, null);
    //     return tables.next();
    // }

    // private static void createTable(Connection connection) throws SQLException {
    //     Statement statement = connection.createStatement();

    //     String createTableQuery = "CREATE TABLE gestionapp ("
    //             + "id SERIAL PRIMARY KEY,"
    //             + "nom VARCHAR(50),"
    //             + "deviceid VARCHAR(50),"
    //             + "type VARCHAR(50),"
    //             + "typeMesure VARCHAR(50),"
    //             + "temperature VARCHAR(10),"
    //             + "humidity VARCHAR(10),"
    //             + "typeAction VARCHAR(50),"
    //             + "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
    //             + "status VARCHAR(10)"
    //             + ")";

    //     statement.executeUpdate(createTableQuery);
    //     statement.close();
    //     System.out.println("\nTable 'gestionapp' créée avec succès !");
    // }

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
                    + "reading_value DECIMAL(10, 2),"
                    + "timestamp TIMESTAMP,"
                    + "FOREIGN KEY (objet_id) REFERENCES ObjetConnecte(objet_id)"
                    + ")");

            state.execute("CREATE TABLE Actionneur ("
                    + "actionneur_id SERIAL PRIMARY KEY,"
                    + "objet_id INT,"
                    + "status VARCHAR(10),"
                    + "timestamp TIMESTAMP,"
                    + "FOREIGN KEY (objet_id) REFERENCES ObjetConnecte(objet_id)"
                    + ")");
        } catch (SQLException e) {
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
