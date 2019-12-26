package model.database;

import model.Measurement;

import java.util.Random;

public class MeasurementGenerator {

    private DBManager dbManager;

    public MeasurementGenerator() {
        this.dbManager = new DBManager();
    }

    //generates a random value for a given measurement
    //generates abnormal values
    public double getRandomValue(Measurement.MeasurementCode code) {
        double minValue = dbManager.getMinValue(code);
        double minRange = minValue - minValue*0.18;
        if (minRange < 0) {
            minRange = 0;
        }

        double maxValue = dbManager.getMaxValue(code);
        double maxRange = maxValue + maxValue*0.18;

        Random rnd = new Random();
        double randomValue = minRange + maxRange*rnd.nextDouble();
        return randomValue;
    }

}
