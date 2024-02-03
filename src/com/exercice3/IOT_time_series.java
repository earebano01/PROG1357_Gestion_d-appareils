package com.exercice3;

import com.exercice2.Conn;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class IOT_time_series {
    public static void tempDataASC() {

        try (Conn conn = new Conn()) {

            try {
                String selectQuery = "SELECT * FROM iot_time_series.temperatures ORDER BY value_in_celcius ASC LIMIT 10";
                PreparedStatement preparedStatement = conn.connect().prepareStatement(selectQuery);

                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println(
                        "--------------------------------------------------------------------------------------------");
                System.out.println(
                        "|   Time              |  Device ID                              |  Location     |  Value   |");
                System.out.println(
                        "--------------------------------------------------------------------------------------------");

                while (resultSet.next()) {
                    String time = resultSet.getString("time");
                    String deviceid = resultSet.getString("deviceid");
                    String location = resultSet.getString("location");
                    String value_in_celcius = resultSet.getString("value_in_celcius");

                    System.out.printf("| %-20s| %-40s| %-14s| %-9s|%n", time, deviceid, location, value_in_celcius);
                }
                System.out.println(
                        "--------------------------------------------------------------------------------------------");

                resultSet.close();
                preparedStatement.close();

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la lecture des utilisateurs.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tempDataDESC() {

        try (Conn conn = new Conn()) {

            try {
                String selectQuery = "SELECT * FROM iot_time_series.temperatures ORDER BY value_in_celcius DESC LIMIT 10";
                PreparedStatement preparedStatement = conn.connect().prepareStatement(selectQuery);

                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println(
                        "--------------------------------------------------------------------------------------------");
                System.out.println(
                        "|   Time              |  Device ID                              |  Location     |  Value   |");
                System.out.println(
                        "--------------------------------------------------------------------------------------------");

                while (resultSet.next()) {
                    String time = resultSet.getString("time");
                    String deviceid = resultSet.getString("deviceid");
                    String location = resultSet.getString("location");
                    String value_in_celcius = resultSet.getString("value_in_celcius");

                    System.out.printf("| %-20s| %-40s| %-14s| %-9s|%n", time, deviceid, location, value_in_celcius);
                }

                System.out.println(
                        "--------------------------------------------------------------------------------------------");

                resultSet.close();
                preparedStatement.close();

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erreur lors de la lecture des utilisateurs.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
