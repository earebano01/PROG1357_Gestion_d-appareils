package com.exercice3;

import com.exercice2.Conn;
import com.exercice1.NomExists;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CRUD {

    public static void insertData(String nom, String deviceid, String type, Timestamp date, String status) {
        Conn app = new Conn();
    
        try {
            String insertQuery = "INSERT INTO gestionapp(nom, deviceid, type, date, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = app.connect().prepareStatement(insertQuery);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, deviceid);
            preparedStatement.setString(3, type);
            preparedStatement.setTimestamp(4, date);
            preparedStatement.setString(5, status);
    
            int rowsAffected = preparedStatement.executeUpdate();
    
            preparedStatement.close();
    
            System.out.println("L'appareil a été ajouté avec succès !");
            System.out.println("");
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'appareil.");
        }
    }
    

    public static void updateData(String oldnom, String newnom, String newdeviceid, String newtype, String newstatus) {
        Conn app = new Conn();
    
        if (!NomExists.nomExists(oldnom)) {
            System.out.println("appareil inexistant. La mise a jour a echoue.");
            return;
        }
    
        try {
            String updateQuery = "UPDATE gestionapp SET nom = ?, deviceid = ?, type = ?, status = ? WHERE nom = ?";
            PreparedStatement preparedStatement = app.connect().prepareStatement(updateQuery);
            preparedStatement.setString(1, newnom);
            preparedStatement.setString(2, newdeviceid);
            preparedStatement.setString(3, newtype);
            preparedStatement.setString(4, newstatus);
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
            System.out.println("Erreur lors de la mise à jour de l'appareil.");
        }
    }

    public static void deleteData(String nom) {
        Conn app = new Conn();

        if (!NomExists.nomExists(nom)) {
            System.out.println("Appareil inexistant. La mise a jour a echoue.");
            return;
        }

        try {
            String deleteQuery = "DELETE FROM gestionapp WHERE nom = ?";
            PreparedStatement preparedStatement = app.connect().prepareStatement(deleteQuery);
            preparedStatement.setString(1, nom);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();

            System.out.println("L'appareil a ete supprime avec succes ! ");
            System.out.println("");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de l'appareil.");
        }
    }

    // public static void readData() {
    //     Conn app = new Conn();
    
    //     try {
    //         String selectQuery = "SELECT * FROM gestionapp";
    //         PreparedStatement preparedStatement = app.connect().prepareStatement(selectQuery);
    
    //         ResultSet resultSet = preparedStatement.executeQuery();
    
    //         System.out.println("---------------------------------------------------------------------------------");
    //         System.out.printf("| %-13s| %-13s| %-12s| %-21s| %-11s|%n", "Nom", "Device ID", "Type", "Date", "Status");
    //         System.out.println("---------------------------------------------------------------------------------");
    
    //         while (resultSet.next()) {
    //             String nom = resultSet.getString("nom");
    //             String deviceid = resultSet.getString("deviceid");
    //             String type = resultSet.getString("type");
                
    //             Timestamp timestamp = resultSet.getTimestamp("date");
    //             String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
                
    //             String status = resultSet.getString("status");
    
    //             System.out.printf("| %-13s| %-13s| %-12s| %-21s| %-11s|%n", nom.toString(), deviceid.toString(), type.toString(), date.toString(), status.toString());
    //         }
    
    //         System.out.println("---------------------------------------------------------------------------------");
    
    //         resultSet.close();
    //         preparedStatement.close();
    
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         System.out.println("Erreur lors de la lecture des appareils.");
    //     }
    // }

    public static void readData() {
        Conn app = new Conn();
    
        try {
            String selectQuery = "SELECT * FROM gestionapp";
            PreparedStatement preparedStatement = app.connect().prepareStatement(selectQuery);
    
            ResultSet resultSet = preparedStatement.executeQuery();
    
            System.out.println(toString(resultSet));
    
            preparedStatement.close();
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la lecture des appareils.");
        }
    }
    
    public static String toString(ResultSet resultSet) {
        StringBuilder resultString = new StringBuilder();
        resultString.append("---------------------------------------------------------------------------------\n");
        resultString.append(String.format("| %-13s| %-13s| %-12s| %-21s| %-11s|%n", "Nom", "Device ID", "Type", "Date", "Status"));
        resultString.append("---------------------------------------------------------------------------------\n");
    
        try {
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String deviceid = resultSet.getString("deviceid");
                String type = resultSet.getString("type");
    
                Timestamp timestamp = resultSet.getTimestamp("date");
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    
                String status = resultSet.getString("status");
    
                resultString.append(String.format("| %-13s| %-13s| %-12s| %-21s| %-11s|%n", nom, deviceid, type, date, status));
            }
            resultString.append("---------------------------------------------------------------------------------\n");
    
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resultString.append("Erreur lors de la lecture des appareils.\n");
        }
    
        return resultString.toString();
    }

    
}
