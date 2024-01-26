package com.exercice4;

import java.sql.Timestamp;

public class ObjetConnecte {
    public String nom;
    public String deviceID;
    public String type;
    public String typeMesure;
    public String typeAction;
    public Timestamp date;
    public String status;

    public ObjetConnecte(String nom, String deviceID, String type, String typeMesure, String typeAction, Timestamp date, String status) {
        this.nom = nom;
        this.deviceID = deviceID;
        this.type = type;
        this.typeMesure = typeMesure;
        this.typeAction = typeAction;
        this.date = date;
        this.status = status;
    }

    public void displayInfo() {
        System.out.println("Nom: " + nom);
        System.out.println("Device ID: " + deviceID);
        System.out.println("Type: " + type);
        System.out.println("Type Mesure: " + typeMesure);
        System.out.println("Type Action: " + typeAction);
        System.out.println("Date: " + date);
        System.out.println("Status: " + status);
    }
}

