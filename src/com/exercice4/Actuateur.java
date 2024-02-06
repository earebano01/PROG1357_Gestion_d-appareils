package com.exercice4;

public class Actuateur extends ObjetConnecte {
    public String typeAction;
    public String formattedDate;
    public String formattedTime;
    
        public Actuateur(String nom, String deviceID, String type, String typeAction, String formattedDate, String formattedTime, String status) {
            super(nom, deviceID, type, null, null, null, null, null, null, typeAction, formattedDate, formattedTime, status);
            this.typeAction = typeAction;
            this.formattedDate = formattedDate;
            this.formattedTime = formattedTime;
        }
}

