package com.exercice1;
import com.exercice2.Conn;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class NomExists {
    public static boolean nomExists(String checkNom) {
        Conn app = new Conn();
        boolean exists = false;
    
        try {
            String checkQuery = "SELECT COUNT(*) AS count FROM gestionapp WHERE nom = ?";
            PreparedStatement checkStatement = app.connect().prepareStatement(checkQuery);
            checkStatement.setString(1, checkNom);
            ResultSet resultSet = checkStatement.executeQuery();
    
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                exists = count > 0;
            }
    
            resultSet.close();
            checkStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return exists;
    }  
}
