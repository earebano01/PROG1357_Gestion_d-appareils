package com.exercice4;

import java.sql.Timestamp;

public class ObjetConnecte {
    public String nom;
    public String deviceID;
    public String type;
    public String typeMesure;
    public Double temperature;
    public String typeAction;
    public Timestamp date;
    public String status;

    public ObjetConnecte(String nom, String deviceID, String type, String typeMesure, Double temperature, String typeAction, Timestamp date, String status) {
        this.nom = nom;
        this.deviceID = deviceID;
        this.type = type;
        this.typeMesure = typeMesure;
        this.temperature = temperature;
        this.typeAction = typeAction;
        this.date = date;
        this.status = status;
    }

    public void capteurInfo() {
        System.out.println("Nom: " + nom);
        System.out.println("Device ID: " + deviceID);
        System.out.println("Type: " + type);
        System.out.println("Type Mesure: " + typeMesure);
        System.out.println("Temperature: " + temperature + " °C");
        System.out.println("Type Action: " + typeAction);
        System.out.println("Date: " + date);
        System.out.println("Status: " + status);
    }
    public void actionneurInfo() {
        System.out.println("Nom: " + nom);
        System.out.println("Device ID: " + deviceID);
        System.out.println("Type: " + type);
        System.out.println("Type Mesure: " + typeMesure);
        System.out.println("Type Action: " + typeAction);
        System.out.println("Date: " + date);
        System.out.println("Status: " + status);
    }
}

