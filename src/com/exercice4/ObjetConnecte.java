package com.exercice4;

public class ObjetConnecte {
    public String nom;
    public String deviceID;
    public String type;
    public String typeMesure;
    public Double temperature; 
    public Double humidite; 
    public int son; 
    public Double distance; 
    public int lumiere; 
    public String formattedDate;
    public String formattedTime;
    public String typeAction;
    public String status;

    public ObjetConnecte(String nom, String deviceID, String type, String typeMesure, Double temperature, Double humidite, int son, Double distance, int lumiere, String typeAction, String formattedDate, String formattedTime, String status) {
        this.nom = nom;
        this.deviceID = deviceID;
        this.type = type;
        this.typeMesure = typeMesure;
        this.temperature = temperature;
        this.humidite = humidite;
        this.son = son;
        this.distance = distance;
        this.lumiere = lumiere;
        this.formattedDate = formattedDate;
        this.formattedTime = formattedTime;
        this.typeAction = typeAction;
        this.status = status;
    }

    public void capteurInfo() {
        System.out.println("Nom: " + nom);
        System.out.println("Device ID: " + deviceID);
        System.out.println("Type: " + type);
        System.out.println("Type Mesure: " + typeMesure);
    }

    public void capteurAll() {
        System.out.println("Nom: " + nom);
        System.out.println("Device ID: " + deviceID);
        System.out.println("Type: " + type);
        System.out.println("Type Mesure: " + typeMesure);
        System.out.println("Temperature: " + temperature);
        System.out.println("Humidite: " + humidite);
        System.out.println("Son: " + son);
        System.out.println("Distance: " + distance);
        System.out.println("Lumiere: " + lumiere);
        System.out.println("Date: " + formattedDate);
        System.out.println("Time: " + formattedTime);
        System.out.println("Status: " + status);
    }

    public void actionneurInfo() {
        System.out.println("Nom: " + nom);
        System.out.println("Device ID: " + deviceID);
        System.out.println("Type: " + type);
        System.out.println("Type Action: " + typeAction);
        System.out.println("Date: " + formattedDate);
        System.out.println("Time: " + formattedTime);
        System.out.println("Status: " + status);
    }
}
