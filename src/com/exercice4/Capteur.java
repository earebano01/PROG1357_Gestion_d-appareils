package com.exercice4;

import java.sql.Timestamp;

public class Capteur extends ObjetConnecte {
    public String typeMesure;

    public Capteur(String nom, String deviceID, String type, Timestamp date, String status, String typeMesure) {
        super(nom, deviceID, type, typeMesure, null, date, status);  
        this.typeMesure = typeMesure;
    }

    public void mesurer() {
        System.out.println("\nCapteur: " + nom + "\nType de Mesure: " + typeMesure);
    }
}