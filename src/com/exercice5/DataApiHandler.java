package com.exercice5;

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

public class DataApiHandler implements HttpHandler {
    private Stack<List<JsonObject>> dataStack;
    private double temperature;
    private double humidity;
    private int photoresistor;
    private int sound;
    private String deviceId;

    public DataApiHandler(Stack<List<JsonObject>> dataStack) {
        this.dataStack = dataStack;
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

        if (dataStack.size() >= 10) {
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
            System.err.println("Malformed JSON: " + jsonObjectString);
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
            System.err.println("Malformed JSON: " + jsonObjectString);
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
            System.err.println("Malformed JSON: " + jsonObjectString);
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
            System.err.println("Malformed JSON: " + jsonObjectString);
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
            System.err.println("Malformed JSON: " + jsonObjectString);
        }
    }

    return null; 
}

    
}
