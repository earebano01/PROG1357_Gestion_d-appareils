package com.exercice3;
import com.exercice2.Conn;
import com.exercice1.NomExists;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CRUD {

    public static void insertData(String nom, String deviceid, String type, String typeMesure, Double temperature, Double humidity, String typeAction, Timestamp date, String status) {
        // Conn app = new Conn();
        try (Conn conn = new Conn()) {
    
        try {
            String insertQuery = "INSERT INTO gestionapp(nom, deviceid, type, typeMesure, temperature, humidity, typeAction, date, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.connect().prepareStatement(insertQuery);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, deviceid);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, typeMesure);
            preparedStatement.setDouble(5, temperature);
            preparedStatement.setDouble(6, humidity);
            preparedStatement.setString(7, typeAction);
            preparedStatement.setTimestamp(8, date);
            preparedStatement.setString(9, status);
    
            int rowsAffected = preparedStatement.executeUpdate();
    
            preparedStatement.close();
    
            System.out.println("\nL'appareil a ete ajoute avec succes !");
            System.out.println("");
    
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("\nErreur lors de l'ajout de l'appareil.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public static void updateData(String oldnom, String newnom, String newdeviceid, String newtype, String newtypeMesure, String newtypeAction, String newstatus) {
        // Conn app = new Conn();
        try (Conn conn = new Conn()) {
    
        if (!NomExists.nomExists(oldnom)) {
            System.out.println("appareil inexistant. La mise a jour a échoue.");
            return;
        }
    
        try {
            String updateQuery = "UPDATE gestionapp SET nom = ?, deviceid = ?, type = ?, typeMesure = ?, typeAction = ?, status = ? WHERE nom = ?";
            PreparedStatement preparedStatement = conn.connect().prepareStatement(updateQuery);
            preparedStatement.setString(1, newnom);
            preparedStatement.setString(2, newdeviceid);
            preparedStatement.setString(3, newtype);
            preparedStatement.setString(4, newtypeMesure);
            preparedStatement.setString(5, newtypeAction);
            preparedStatement.setString(6, newstatus);
            preparedStatement.setString(7, oldnom);
    
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
        // Conn app = new Conn();
        try (Conn conn = new Conn()) {
    
        if (!NomExists.nomExists(nom)) {
            System.out.println("Appareil inexistant. La mise a jour a echoue.");
            return;
        }

        try {
            String deleteQuery = "DELETE FROM gestionapp WHERE nom = ?";
            PreparedStatement preparedStatement = conn.connect().prepareStatement(deleteQuery);
            preparedStatement.setString(1, nom);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();

            System.out.println("L'appareil a ete supprime avec succes ! ");
            System.out.println("");

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la suppression de l'appareil.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public static void readData() {
        // Conn app = new Conn();
        try (Conn conn = new Conn()) {
    
        try {
            String selectQuery = "SELECT * FROM gestionapp";
            PreparedStatement preparedStatement = conn.connect().prepareStatement(selectQuery);
    
            ResultSet resultSet = preparedStatement.executeQuery();
    
            System.out.println(toString(resultSet));
    
            preparedStatement.close();
    
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la lecture des appareils.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    public static String toString(ResultSet resultSet) {
        StringBuilder resultString = new StringBuilder();
        resultString.append("------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        resultString.append(String.format("| %-21s| %-13s| %-20s| %-18s| %-18s| %-18s| %-18s| %-21s| %-11s|%n", "Nom", "Device ID", "Type", "Type Mesure", "Temperature", "Humidity", "Type Action", "Date", "Status"));
        resultString.append("------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    
        try {
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String deviceid = resultSet.getString("deviceid");
                String type = resultSet.getString("type");
                String typeMesure = resultSet.getString("typeMesure");
                Double temperature = resultSet.getDouble("temperature");
                Double humidity = resultSet.getDouble("humidity");
                String typeAction = resultSet.getString("typeAction");
    
                Timestamp timestamp = resultSet.getTimestamp("date");
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp);
    
                String status = resultSet.getString("status");
    
                resultString.append(String.format("| %-21s| %-13s| %-20s| %-18s| %-18s| %-18s| %-18s| %-21s| %-11s|%n", nom, deviceid, type, typeMesure, temperature, humidity, typeAction, date, status));
            }
            resultString.append("------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            resultString.append("Erreur lors de la lecture des appareils.\n");
        }
    
        return resultString.toString();
    }
    

}
