package com.exercice4;
import java.util.Random;

public class Simulator {
    private Random random;

    public Simulator() {
        this.random = new Random();
    }

    public double readTemperature() {
        double temperature = 2 + random.nextDouble() * 32;
        return Double.parseDouble(String.format("%.2f", temperature));
    }

    public double readHumidity() {
        double humidity = 40 + random.nextDouble() * 20;
        return Double.parseDouble(String.format("%.2f", humidity));
    }
}
