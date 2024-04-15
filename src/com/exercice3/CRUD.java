package com.exercice3;
import com.exercice2.Conn;
import com.exercice1.NomExists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CRUD {

    public static int insertObjetConnecte(String nom, String device_id, String type, String typemesure, String typeaction) {
        int objet_id = -1; 
    
        try (Conn conn = new Conn()) {
            try {
                String insertQuery = "INSERT INTO objetconnecte(nom, device_id, type, typemesure, typeaction) VALUES (?, ?, ?, ?, ?) RETURNING objet_id";
                PreparedStatement preparedStatement = conn.connect().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, device_id);
                preparedStatement.setString(3, type);
                preparedStatement.setString(4, typemesure);
                preparedStatement.setString(5, typeaction);
    
                int rowsAffected = preparedStatement.executeUpdate();
    
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        objet_id = generatedKeys.getInt(1);
                    }
                }
    
                preparedStatement.close();
    
                System.out.println("\nL'appareil connecte a ete ajoute avec succès !");
                System.out.println("");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("\nErreur lors de l'ajout de l'appareil connecte.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return objet_id;
    }
    
    public static void insertCapteur(int objet_id, String status, Double temperature, Double humidite, int son, Double distance, int lumiere, String formatted_date, String formatted_time) {
        try (Conn conn = new Conn()) {
            try {
                String insertQuery = "INSERT INTO Capteur(objet_id, status, temperature, humidite, son, distance, lumiere, formatted_date, formatted_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.connect().prepareStatement(insertQuery);
                preparedStatement.setInt(1, objet_id);
                preparedStatement.setString(2, status);
                preparedStatement.setDouble(3, temperature);
                preparedStatement.setDouble(4, humidite);
                preparedStatement.setDouble(5, son);
                preparedStatement.setDouble(6, distance);
                preparedStatement.setDouble(7, lumiere);
                preparedStatement.setString(8, formatted_date);
                preparedStatement.setString(9, formatted_time);
    
                int rowsAffected = preparedStatement.executeUpdate();
    
                preparedStatement.close();
    
                System.out.println("\nLe capteur a ete ajoute avec succès !");
                System.out.println("");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("\nErreur lors de l'ajout du capteur.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertActionneur(int objet_id, String status, String formatted_date, String formatted_time) {
        try (Conn conn = new Conn()) {
            try {
                String insertQuery = "INSERT INTO Actionneur(objet_id, status, formatted_date, formatted_time) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.connect().prepareStatement(insertQuery);
                preparedStatement.setInt(1, objet_id);
                preparedStatement.setString(2, status);
                preparedStatement.setString(3, formatted_date);
                preparedStatement.setString(4, formatted_time);

                int rowsAffected = preparedStatement.executeUpdate();

                preparedStatement.close();

                System.out.println("\nL'actionneur a ete ajoute avec succès !");
                System.out.println("");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("\nErreur lors de l'ajout de l'actionneur.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateCapteur(String oldnom, String newnom, String newdeviceid, String newtype, String newtypemesure) {
        try (Conn conn = new Conn()) {
    
        if (!NomExists.nomExists(oldnom)) {
            System.out.println("appareil inexistant. La mise a jour a echoue.");
            return;
        }
    
        try {
            String updateQuery = "UPDATE objetconnecte SET nom = ?, device_id = ?, type = ?, typemesure = ? WHERE nom = ?";
            PreparedStatement preparedStatement = conn.connect().prepareStatement(updateQuery);
            preparedStatement.setString(1, newnom);
            preparedStatement.setString(2, newdeviceid);
            preparedStatement.setString(3, newtype);
            preparedStatement.setString(4, newtypemesure);
            preparedStatement.setString(5, oldnom);
    
            int rowsAffected = preparedStatement.executeUpdate();
    
            preparedStatement.close();
    
            if (rowsAffected > 0) {
                System.out.println("L'appareil a ete mis a jour avec succes !");
                System.out.println("");
            } else {
                System.out.println("Aucune modification effectuee. Veuillez verifier les donnees.");
            }
    
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la mise a jour de l'appareil.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public static void updateActuateur(String oldnom, String newnom, String newdeviceid, String newtype, String newtypeaction) {
        try (Conn conn = new Conn()) {
    
            if (!NomExists.nomExists(oldnom)) {
                System.out.println("Appareil inexistant. La mise a jour a echoue.");
                return;
            }
    
            try {
                String updateQuery = "UPDATE objetconnecte SET nom = ?, device_id = ?, type = ?, typeaction = ? WHERE nom = ?";
                PreparedStatement preparedStatement = conn.connect().prepareStatement(updateQuery);
                preparedStatement.setString(1, newnom);
                preparedStatement.setString(2, newdeviceid);
                preparedStatement.setString(3, newtype);
                preparedStatement.setString(4, newtypeaction);
                preparedStatement.setString(5, oldnom);
    
                int rowsAffected = preparedStatement.executeUpdate();
    
                preparedStatement.close();
    
                if (rowsAffected > 0) {
                    System.out.println("L'appareil a ete mis a jour avec succes !");
                    System.out.println("");
                } else {
                    System.out.println("Aucune modification effectuee. Veuillez verifier les donnees.");
                }
    
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la mise a jour de l'appareil.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   

    public static void deleteData(String nom) {
        try (Conn conn = new Conn()) {
    
            if (!NomExists.nomExists(nom)) {
                System.out.println("Appareil inexistant. La suppression a echoue.");
                return;
            }
    
            try {
                String selectQuery = "SELECT * FROM objetconnecte WHERE nom = ?";
                PreparedStatement selectStatement = conn.connect().prepareStatement(selectQuery);
                selectStatement.setString(1, nom);
                ResultSet resultSet = selectStatement.executeQuery();
    
                System.out.println("\nLes donnees suivantes vont etre supprimees :");
                System.out.println("------------------------------------------------------------------------------------------------");
                System.out.println("| Obj ID | Nom            | Device ID        | Type    | Type Mesure             | Type Action |");
                System.out.println("------------------------------------------------------------------------------------------------");
    
                while (resultSet.next()) {
                    int objId = resultSet.getInt("objet_id");
                    String name = resultSet.getString("nom");
                    String deviceId = resultSet.getString("device_id");
                    String type = resultSet.getString("type");
                    String typeMesure = resultSet.getString("typemesure");
                    String typeAction = resultSet.getString("typeaction");
    
                    System.out.printf("| %-6d | %-14s | %-10s | %-6s | %-23s | %-11s |%n",
                            objId, name, deviceId, type, typeMesure, typeAction);
                }
    
                System.out.println("------------------------------------------------------------------------------------------------");
    
                selectStatement.close();
    
                Scanner scanner = new Scanner(System.in);
    
                String confirmation;
            do {
                System.out.print("\nConfirmez-vous la suppression ? (O/N): ");
                confirmation = scanner.nextLine();

                if (confirmation.equalsIgnoreCase("O")) {
                    int objID;
                    do {
                        System.out.print("\nEntrez le objet ID: ");
                        objID = scanner.nextInt();

                        if (!isObjectIDValid(conn.connect(), objID)) {
                            System.out.println("ID d'objet non valide. Veuillez réessayer.");
                        }

                    } while (!isObjectIDValid(conn.connect(), objID));

                    String deleteQuery = "DELETE FROM objetconnecte WHERE objet_id = ?";
                    try (PreparedStatement preparedStatement = conn.connect().prepareStatement(deleteQuery)) {
                        preparedStatement.setInt(1, objID);
                        int rowsAffected = preparedStatement.executeUpdate();
                        preparedStatement.close();
                        System.out.println("L'appareil a été supprimé avec succès !");
                        System.out.println("");
                    }
                } else if (!confirmation.equalsIgnoreCase("N")) {
                    System.out.println("Veuillez entrer une réponse valide (O/N).");
                } else {
                    System.out.println("Suppression annulée.");
                }
            } while (!confirmation.equalsIgnoreCase("O") && !confirmation.equalsIgnoreCase("N"));

    
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la suppression de l'appareil.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public static boolean isObjectIDValid(Connection conn, int objID) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM objetconnecte WHERE objet_id = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, objID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }
    
    
    public static void readOB() {
        try (Conn conn = new Conn()) {
            try {
                String selectQuery = "SELECT * FROM objetconnecte";
                PreparedStatement preparedStatement = conn.connect().prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery();
    
                System.out.println(toStringAB(resultSet));
    
                preparedStatement.close();
    
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la lecture des appareils.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String toStringAB(ResultSet resultSet) {
        StringBuilder resultString = new StringBuilder();
        resultString.append("------------------------------------------------------------------------------------------------------------------\n");
        resultString.append(String.format("| %-21s | %-13s | %-20s | %-23s | %-21s |\n", "Nom", "Device ID", "Type", "Type Mesure", "Type Action"));
        resultString.append("------------------------------------------------------------------------------------------------------------------\n");
    
        try {
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String deviceid = resultSet.getString("device_id");
                String type = resultSet.getString("type");
                String typeMesure = resultSet.getString("typemesure");
                String typeAction = resultSet.getString("typeaction");
    
                resultString.append(String.format("| %-21s | %-13s | %-20s | %-23s | %-21s |\n", nom, deviceid, type, typeMesure, typeAction));
            }
            resultString.append("------------------------------------------------------------------------------------------------------------------\n");
    
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resultString.append("Erreur lors de la lecture des appareils.\n");
        }
    
        return resultString.toString();
    }
    
    public static void readCA() {
        try (Conn conn = new Conn()) {
            try {
                String selectQuery = "SELECT c.nom, c.device_id, c.type, ca.status, ca.temperature, ca.humidite, ca.son, ca.distance, ca.lumiere, ca.formatted_date, ca.formatted_time FROM objetconnecte c INNER JOIN capteur ca ON c.objet_id = ca.objet_id";
                PreparedStatement preparedStatement = conn.connect().prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery();
    
                System.out.println(toStringCA(resultSet));
    
                preparedStatement.close();
    
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la lecture des capteurs.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String toStringCA(ResultSet resultSet) {
        StringBuilder resultString = new StringBuilder();
        resultString.append("------------------------------------------------------------------------------------------------------------------\n");
        resultString.append(String.format("| %-21s | %-13s | %-20s | %-10s | %-12s | %-10s | %-10s | %-12s | %-10s | %-12s | %-12s |\n", "Nom", "Device ID", "Type", "Status", "Temperature", "Humidite", "Son", "Distance", "Lumiere", "Date", "Time"));
        resultString.append("------------------------------------------------------------------------------------------------------------------\n");
    
        try {
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String deviceid = resultSet.getString("device_id");
                String type = resultSet.getString("type");
                String status = resultSet.getString("status");
                double temperature = resultSet.getDouble("temperature");
                double humidite = resultSet.getDouble("humidite");
                double son = resultSet.getDouble("son");
                double distance = resultSet.getDouble("distance");
                double lumiere = resultSet.getDouble("lumiere");
                String formattedDate = resultSet.getString("formatted_date");
                String formattedTime = resultSet.getString("formatted_time");
    
                resultString.append(String.format("| %-21s | %-13s | %-20s | %-10s | %-12.2f | %-10.2f | %-10.2f | %-12.2f | %-10.2f | %-12s | %-12s |\n", nom, deviceid, type, status, temperature, humidite, son, distance, lumiere, formattedDate, formattedTime));
            }
            resultString.append("-------------------------------------------------------------------------------------------------------------\n");
    
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resultString.append("Erreur lors de la lecture des capteurs.\n");
        }
    
        return resultString.toString();
    }

    public static void readAC() {
        try (Conn conn = new Conn()) {
            try {
                String selectQuery = "SELECT c.nom, c.device_id, c.type, c.typeaction, a.status, a.formatted_date, a.formatted_time FROM ObjetConnecte c INNER JOIN Actionneur a ON c.objet_id = a.objet_id";
                PreparedStatement preparedStatement = conn.connect().prepareStatement(selectQuery);
                ResultSet resultSet = preparedStatement.executeQuery();
    
                System.out.println(toStringAC(resultSet));
    
                preparedStatement.close();
    
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la lecture des actionneurs.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static String toStringAC(ResultSet resultSet) {
        StringBuilder resultString = new StringBuilder();
        resultString.append("----------------------------------------------------------------------------------------------------------------------------------\n");
        resultString.append(String.format("| %-21s | %-13s | %-20s | %-20s | %-10s | %-12s | %-12s |\n", "Nom", "Device ID", "Type", "Type Action", "Status", "Date", "Time"));
        resultString.append("----------------------------------------------------------------------------------------------------------------------------------\n");
    
        try {
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String deviceid = resultSet.getString("device_id");
                String type = resultSet.getString("type");
                String typeaction = resultSet.getString("typeaction");
                String status = resultSet.getString("status");
                String formattedDate = resultSet.getString("formatted_date");
                String formattedTime = resultSet.getString("formatted_time");
    
                resultString.append(String.format("| %-21s | %-13s | %-20s | %-20s | %-10s | %-12s | %-12s |\n", nom, deviceid, type, typeaction, status, formattedDate, formattedTime));
            }
            resultString.append("----------------------------------------------------------------------------------------------------------------------------------\n");
    
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resultString.append("Erreur lors de la lecture des actionneurs.\n");
        }
    
        return resultString.toString();
    }
    
    
}
