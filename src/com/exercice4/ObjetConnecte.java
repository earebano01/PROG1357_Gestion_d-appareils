package com.exercice4;

import java.sql.Timestamp;

public class ObjetConnecte {
    public String nom;
    public String deviceID;
    public String type;
    public String typeMesure;
    public Double reading_value; // Changed to reading_value
    public Timestamp timestamp; // Changed to timestamp
    public String typeAction;
    public String actuation_status; // Changed to actuation_status
    public String status;

    public ObjetConnecte(String nom, String deviceID, String type, String typeMesure, Double reading_value, String typeAction, String actuation_status, Timestamp timestamp, String status) {
        this.nom = nom;
        this.deviceID = deviceID;
        this.type = type;
        this.typeMesure = typeMesure;
        this.reading_value = reading_value;
        this.timestamp = timestamp;
        this.typeAction = typeAction;
        this.actuation_status = actuation_status;
        this.status = status;
    }

    public void capteurInfo() {
        System.out.println("Nom: " + nom);
        System.out.println("Device ID: " + deviceID);
        System.out.println("Type: " + type);
        System.out.println("Type Mesure: " + typeMesure);
        System.out.println("Reading Value: " + reading_value);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Type Action: " + typeAction);
        System.out.println("Actuation Status: " + actuation_status);
        System.out.println("Status: " + status);
    }

    public void actionneurInfo() {
        System.out.println("Nom: " + nom);
        System.out.println("Device ID: " + deviceID);
        System.out.println("Type: " + type);
        // System.out.println("Type Mesure: " + typeMesure);
        System.out.println("Type Action: " + typeAction);
        System.out.println("Actuation Status: " + actuation_status);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Status: " + status);
    }
}
