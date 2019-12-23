package Model.Database;

import Model.Measurement;

import java.util.Random;

public class MeasurementRandomizer {

    private DBManager dbManager;

    public MeasurementRandomizer() {
        this.dbManager = new DBManager();
    }

    public double getRandomValue(Measurement.MeasurementCode code) {
        double minValue = dbManager.getMinValue(code);
        double minRange = minValue - minValue*0.18;

        double maxValue = dbManager.getMaxValue(code);
        double maxRange = maxValue + maxValue*0.18;

        Random rnd = new Random();
        double randomValue = minRange + maxRange*rnd.nextDouble();
        return randomValue;
    }

}
