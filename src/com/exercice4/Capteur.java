package com.exercice4;

public class Capteur extends ObjetConnecte {
    public String typeMesure;
    public Double temperature; 
    public Double humidite; 
    public Double son; 
    public Double distance; 
    public Double lumiere; 
    public String formattedDate;
    public String formattedTime;

    public Capteur(String nom, String deviceID, String type, String typeMesure, Double temperature, Double humidite, Double son, Double distance, Double lumiere, String formattedDate, String formattedTime, String status) {
        super(nom, deviceID, type, typeMesure, temperature, humidite, son, distance, lumiere, null, formattedDate, formattedTime, status);
        this.typeMesure = typeMesure;
        this.temperature = temperature;
        this.humidite = humidite;
        this.son = son;
        this.distance = distance;
        this.lumiere = lumiere;
        this.formattedDate = formattedDate;
        this.formattedTime = formattedTime;
    }

    public void mesurer(Simulator sim) {
        System.out.println("\n=========================================");
        System.out.println("CAPTEUR");
        System.out.println("=========================================");
        System.out.println("Nom: " + nom);
        System.out.println("Type Mesure: " + typeMesure);
        System.out.println("Temperature: " + temperature);
        System.out.println("Humidite: " + humidite);
        System.out.println("Son: " + son);
        System.out.println("Distance: " + distance);
        System.out.println("Lumiere: " + lumiere);
        System.out.println("Date: " + formattedDate);
        System.out.println("Time: " + formattedTime);
    }

}
