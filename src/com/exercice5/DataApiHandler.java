package com.exercice5;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Stack;

public class DataApiHandler implements HttpHandler {
    private Stack<String> dataStack;

    public DataApiHandler(Stack<String> dataStack) {
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
                requestData.append(scanner.nextLine());
            }
            scanner.close();
            receiveData(requestData.toString()); 
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

    public void receiveData(String newData) {
        if (dataStack.size() >= 10) {
            dataStack.remove(0);
        }
        dataStack.add(newData);
    }

    public String viewAvailableData() {
        StringBuilder responseData = new StringBuilder();
        responseData.append("Donnees recues :\n");
        if (!dataStack.isEmpty()) {
            for (String data : dataStack) {
                responseData.append(data).append("\n");
            }
        } else {
            responseData.append("Aucune donnee disponible. Veuillez reessayer apres avoir recu des donnees.");
        }
        return responseData.toString();
    }
}
