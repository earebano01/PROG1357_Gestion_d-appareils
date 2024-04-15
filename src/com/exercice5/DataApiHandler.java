package com.exercice5;

import com.exercice2.Conn;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.Date;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataApiHandler implements HttpHandler {
    private Stack<List<JsonObject>> dataStack;
    private int lastProcessedIndex; 

    public DataApiHandler(Stack<List<JsonObject>> dataStack) {
        this.dataStack = dataStack;
        this.lastProcessedIndex = 0;
        scheduleDatabaseUpdate();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";
        int status = 200;

        if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            response = viewAvailableData();
        } else if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            Scanner scanner = new Scanner(exchange.getRequestBody());
            StringBuilder requestData = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    JsonObject jsonData = parseJsonObject(line);
                    receiveData(jsonData);
                }
            }
            scanner.close();
            response = "Objet connecte ajoute.";
        } else {
            status = 405;
            response = "Methode non autorisee";
        }

        exchange.sendResponseHeaders(status, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private JsonObject parseJsonObject(String jsonObject) {
        return JsonParser.parseString(jsonObject).getAsJsonObject();
    }

    public void receiveData(JsonObject newData) {
        List<JsonObject> dataList = new ArrayList<>();
        dataList.add(newData);

        if (dataStack.size() >= 30) {
            dataStack.remove(0);
        }
        dataStack.push(dataList);
    }

    public String viewAvailableData() {
        StringBuilder responseData = new StringBuilder();
        if (!dataStack.isEmpty()) {
            for (List<JsonObject> dataList : dataStack) {
                for (JsonObject jsonObject : dataList) {
                    responseData.append(jsonObject.toString()).append("\n");
                }
            }
        } else {
            responseData.append("Aucune donnee disponible. Veuillez reessayer apres avoir recu des donnees.");
        }
        return responseData.toString();
    }

    public JsonObject exTempData(String jsonData) {
        String[] jsonObjects = jsonData.split("\n");

        for (String jsonObjectString : jsonObjects) {
            try {
                JsonObject jsonObject = JsonParser.parseString(jsonObjectString).getAsJsonObject();
                if (jsonObject.has("deviceid") && jsonObject.has("temperature")) {
                    return jsonObject;
                }
            } catch (JsonSyntaxException e) {
                System.err.println("JSON mal forme: " + jsonObjectString);
            }
        }

        return null;
    }

    public JsonObject extHumData(String jsonData) {
        String[] jsonObjects = jsonData.split("\n");

        for (String jsonObjectString : jsonObjects) {
            try {
                JsonObject jsonObject = JsonParser.parseString(jsonObjectString).getAsJsonObject();
                if (jsonObject.has("deviceid") && jsonObject.has("humidity")) {
                    return jsonObject;
                }
            } catch (JsonSyntaxException e) {
                System.err.println("JSON mal forme: " + jsonObjectString);
            }
        }

        return null;
    }

    public JsonObject extTempHumData(String jsonData) {
        String[] jsonObjects = jsonData.split("\n");

        for (String jsonObjectString : jsonObjects) {
            try {
                JsonObject jsonObject = JsonParser.parseString(jsonObjectString).getAsJsonObject();
                if (jsonObject.has("deviceid") && jsonObject.has("temperature") && jsonObject.has("humidity")) {
                    return jsonObject;
                }
            } catch (JsonSyntaxException e) {
                System.err.println("JSON mal forme: " + jsonObjectString);
            }
        }

        return null;
    }

    public JsonObject exSonData(String jsonData) {
        String[] jsonObjects = jsonData.split("\n");

        for (String jsonObjectString : jsonObjects) {
            try {
                JsonObject jsonObject = JsonParser.parseString(jsonObjectString).getAsJsonObject();
                if (jsonObject.has("deviceid") && jsonObject.has("sound")) {
                    return jsonObject;
                }
            } catch (JsonSyntaxException e) {
                System.err.println("JSON mal forme: " + jsonObjectString);
            }
        }

        return null;
    }

    public JsonObject exPhotoData(String jsonData) {
        String[] jsonObjects = jsonData.split("\n");

        for (String jsonObjectString : jsonObjects) {
            try {
                JsonObject jsonObject = JsonParser.parseString(jsonObjectString).getAsJsonObject();
                if (jsonObject.has("deviceid") && jsonObject.has("photoresistor")) {
                    return jsonObject;
                }
            } catch (JsonSyntaxException e) {
                System.err.println("JSON mal forme: " + jsonObjectString);
            }
        }

        return null;
    }
    
    public JsonObject exLedStatus(String jsonData) {
        String[] jsonObjects = jsonData.split("\n");

        for (String jsonObjectString : jsonObjects) {
            try {
                JsonObject jsonObject = JsonParser.parseString(jsonObjectString).getAsJsonObject();
                if (jsonObject.has("led")) {
                    return jsonObject;
                }
            } catch (JsonSyntaxException e) {
                System.err.println("JSON mal forme: " + jsonObjectString);
            }
        }

        return null;
    }
    
    public JsonObject exRelayStatus(String jsonData) {
        String[] jsonObjects = jsonData.split("\n");

        for (String jsonObjectString : jsonObjects) {
            try {
                JsonObject jsonObject = JsonParser.parseString(jsonObjectString).getAsJsonObject();
                if (jsonObject.has("relay")) {
                    return jsonObject;
                }
            } catch (JsonSyntaxException e) {
                System.err.println("JSON mal forme: " + jsonObjectString);
            }
        }

        return null;
    }

    public void scheduleDatabaseUpdate() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> saveDataToDatabase(), 0, 20, TimeUnit.SECONDS);
    }

    public void saveDataToDatabase() {
        try (Conn conn = new Conn(); Connection connection = conn.connect()) {
            if (connection != null) {
                List<JsonObject> dataList = new ArrayList<>();
                synchronized (dataStack) {
                    for (int i = lastProcessedIndex; i < dataStack.size(); i++) {
                        dataList.addAll(dataStack.get(i));
                    }
                    lastProcessedIndex = dataStack.size();
                }

                if (!dataList.isEmpty()) {
                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDateTime = sdf.format(now);
    
                    for (JsonObject jsonObject : dataList) {
                        saveData(connection, jsonObject, formattedDateTime);
                    }
                }
            } else {
                System.out.println("Failed to establish connection to the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while saving data to the database.");
        }
    }
    

    public void saveData(Connection connection, JsonObject jsonObject, String formattedDateTime) {
        try (Statement statement = connection.createStatement()) {
            if (jsonObject.has("temperature")) {
                saveTemperatureData(statement, jsonObject, formattedDateTime);
            }
            if (jsonObject.has("humidity")) {
                saveHumidityData(statement, jsonObject, formattedDateTime);
            }
            if (jsonObject.has("temperature") && jsonObject.has("humidity")) {
                saveTempHumData(statement, jsonObject, formattedDateTime);
            }
            if (jsonObject.has("sound")) {
                saveSoundData(statement, jsonObject, formattedDateTime);
            }
            if (jsonObject.has("photoresistor")) {
                savePhotoData(statement, jsonObject, formattedDateTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while saving data to the database.");
        }
    }

    public void saveTemperatureData(Statement statement, JsonObject jsonObject, String formattedDateTime) throws SQLException {
        String deviceId = jsonObject.get("deviceid").getAsString();
        double temperature = jsonObject.get("temperature").getAsDouble();

        String sql = String.format("INSERT INTO Capteur (objet_id, temperature, formatted_date, formatted_time) " +
                "SELECT objet_id, %f, '%s', '%s' FROM ObjetConnecte WHERE device_id = '%s'", temperature, formattedDateTime,
                formattedDateTime, deviceId);
                statement.executeUpdate(sql);
    }

    public void saveHumidityData(Statement statement, JsonObject jsonObject, String formattedDateTime) throws SQLException {
        String deviceId = jsonObject.get("deviceid").getAsString();
        double humidity = jsonObject.get("humidity").getAsDouble();

        String sql = String.format("INSERT INTO Capteur (objet_id, humidite, formatted_date, formatted_time) " +
                "SELECT objet_id, %f, '%s', '%s' FROM ObjetConnecte WHERE device_id = '%s'", humidity, formattedDateTime,
                formattedDateTime, deviceId);
                statement.executeUpdate(sql);
    }

    public void saveTempHumData(Statement statement, JsonObject jsonObject, String formattedDateTime) throws SQLException {
        String deviceId = jsonObject.get("deviceid").getAsString();
        double temperature = jsonObject.get("temperature").getAsDouble();
        double humidity = jsonObject.get("humidity").getAsDouble();

        String sql = String.format("INSERT INTO Capteur (objet_id, temperature, humidite, formatted_date, formatted_time) " +
                "SELECT objet_id, %f, %f, '%s', '%s' FROM ObjetConnecte WHERE device_id = '%s'", temperature, humidity, formattedDateTime,
                formattedDateTime, deviceId);
                statement.executeUpdate(sql);
    }

    public void saveSoundData(Statement statement, JsonObject jsonObject, String formattedDateTime) throws SQLException {
        String deviceId = jsonObject.get("deviceid").getAsString();
        double soundLevel = jsonObject.get("sound").getAsDouble();

        String sql = String.format("INSERT INTO Capteur (objet_id, son, formatted_date, formatted_time) " +
                "SELECT objet_id, %f, '%s', '%s' FROM ObjetConnecte WHERE device_id = '%s'", soundLevel, formattedDateTime,
                formattedDateTime, deviceId);
                statement.executeUpdate(sql);
    }

    public void savePhotoData(Statement statement, JsonObject jsonObject, String formattedDateTime) throws SQLException {
        String deviceId = jsonObject.get("deviceid").getAsString();
        int photoresistorValue = jsonObject.get("photoresistor").getAsInt();
    
        String sql = String.format("INSERT INTO Capteur (objet_id, lumiere, formatted_date, formatted_time) " +
                "SELECT objet_id, %d, '%s', '%s' FROM ObjetConnecte WHERE device_id = '%s'", photoresistorValue, formattedDateTime,
                formattedDateTime, deviceId);
                statement.executeUpdate(sql);
    }

}
