package com.exercice4;

import java.sql.Timestamp;

public class Capteur extends ObjetConnecte {
    public String typeMesure;
    public Double temperature;
    public Double humidity;

    public Capteur(String nom, String deviceID, String type, String typeMesure, Double temperature, Double humidity, Timestamp date, String status) {
        super(nom, deviceID, type, typeMesure, temperature, humidity, null, date, status);  
        this.typeMesure = typeMesure;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public void mesurer(Simulator sim) {
        System.out.println("\nCapteur: " + nom + "\nType de Mesure: " + typeMesure);
    }
    
}