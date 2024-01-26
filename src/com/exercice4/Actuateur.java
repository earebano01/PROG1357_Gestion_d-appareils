package com.exercice4;

import java.sql.Timestamp;

public class Actuateur extends ObjetConnecte {
    public String typeAction;

    public Actuateur(String nom, String deviceID, String type, Timestamp date, String status, String typeAction) {
        super(nom, deviceID, type, null, typeAction, date, status); 
        this.typeAction = typeAction;
    }

    public void actionner() {
        System.out.println("\nActionneur: " + nom + "\nEffectue une action de type: " + typeAction);
    }
}