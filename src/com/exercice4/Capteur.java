// package com.exercice4;

// import java.sql.Timestamp;

// public class Capteur extends ObjetConnecte {
//     public String typeMesure;
//     public Double temperature;
//     public Double humidity;

//     public Capteur(String nom, String deviceID, String type, String typeMesure, Double temperature, Double humidity, Timestamp date, String status) {
//         super(nom, deviceID, type, typeMesure, temperature, humidity, null, date, status);  
//         this.typeMesure = typeMesure;
//         this.temperature = temperature;
//         this.humidity = humidity;
//     }

//     public void mesurer(Simulator sim) {
//         System.out.println("\nCapteur: " + nom + "\nType de Mesure: " + typeMesure);
//     }
    
// }

package com.exercice4;

import java.sql.Timestamp;

public class Capteur extends ObjetConnecte {
    public String typeMesure;
    public Double reading_value; // Changed to reading_value
    public Timestamp timestamp; // Changed to timestamp

    public Capteur(String nom, String deviceID, String type, String typeMesure, Double reading_value, Timestamp timestamp, String status) {
        super(nom, deviceID, type, typeMesure, reading_value, null, null, timestamp, status);
        this.typeMesure = typeMesure;
        this.reading_value = reading_value;
        this.timestamp = timestamp;
    }

    public void mesurer(Simulator sim) {
        // Assuming you want to print the measurement type and reading value
        System.out.println("\nCapteur: " + nom + "\nType de Mesure: " + typeMesure
                + "\nReading Value: " + sim.readTemperature() + "\nTimestamp: " + timestamp);
    }
}
