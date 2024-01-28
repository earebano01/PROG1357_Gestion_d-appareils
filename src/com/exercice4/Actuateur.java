package com.exercice4;

import java.sql.Timestamp;

public class Actuateur extends ObjetConnecte {
    public String typeAction;
    public Double temperature;

    public Actuateur(String nom, String deviceID, String type, Timestamp date, String status, String typeAction, Double temperature) {
        super(nom, deviceID, type, null, temperature, typeAction, date, status); 
        this.typeAction = typeAction;
        this.temperature = temperature;
    }

    public void actionner() {
        System.out.println("\nActionneur: " + nom + "\nEffectue une action de type: " + typeAction);
    }
}