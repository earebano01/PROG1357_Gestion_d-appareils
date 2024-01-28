package com.exercice1;
import com.exercice2.Conn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NomExists {
    public static boolean nomExists(String checkNom) {
        try (Conn conn = new Conn()) {
            boolean exists = false;

            try {
                String checkQuery = "SELECT COUNT(*) AS count FROM gestionapp WHERE nom = ?";
                PreparedStatement checkStatement = conn.connect().prepareStatement(checkQuery);
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

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }  
}
