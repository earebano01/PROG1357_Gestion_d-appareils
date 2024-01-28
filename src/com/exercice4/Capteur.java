package com.exercice4;

import java.sql.Timestamp;

public class Capteur extends ObjetConnecte {
    public String typeMesure;
    public Double temperature;

    public Capteur(String nom, String deviceID, String type, Timestamp date, String status, String typeMesure, Double temperature) {
        super(nom, deviceID, type, typeMesure, temperature, null, date, status);  
        this.typeMesure = typeMesure;
        this.temperature = temperature;
    }

    public void mesurer(Simulator sim) {
        System.out.println("\nCapteur: " + nom + "\nType de Mesure: " + typeMesure);
    }
    
}