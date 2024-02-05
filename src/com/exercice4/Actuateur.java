package com.exercice4;

import java.sql.Timestamp;

public class Actuateur extends ObjetConnecte {
    public String typeAction;
    public String actuation_status; // Added to represent the state of the actuator
    public Timestamp timestamp; // Changed to timestamp


    public Actuateur(String nom, String deviceID, String type, String typeAction, Timestamp timestamp, String actuation_status, String status) {
        super(nom, deviceID, type, null, null, typeAction, actuation_status, timestamp, status);
        this.typeAction = typeAction;
        this.timestamp = timestamp;
        this.actuation_status = actuation_status;
    }


    public void actionner() {
        System.out.println("\nActionneur: " + nom + "\nEffectue une action de type: " + typeAction
                + "\nTimestamp: " + timestamp + "\nActuation Status: " + actuation_status);
    }
}

