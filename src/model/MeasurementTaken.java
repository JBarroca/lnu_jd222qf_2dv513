package model;

import model.database.DBManager;

import java.math.BigDecimal;

public class MeasurementTaken {

    private String personnummer;
    private Patient patient;

    private String labName;
    private String labPostalCode;
    private String date;

    private String measurementName;
    private Double measurementValue;
    private String measurementUnits;
    private Double measurementMinValue;
    private Double measurementMaxValue;

    public MeasurementTaken(String personnummer, String labName, String labPostalCode, String date, Double measurementValue, int codeId) {
        this.personnummer = personnummer;
        this.labName = labName;
        this.labPostalCode = labPostalCode;
        this.date = date;
        this.measurementValue = measurementValue;
        //name, units, min and max are obtained from the 'measurements' table
        getMeasurementInfo(codeId);
        getCurrentPatient(personnummer);
    }

    public MeasurementTaken(String personnummer, String labName, String date) {
        this.personnummer = personnummer;
        this.labName = labName;
        this.date = date;
    }

    private void getMeasurementInfo(int codeId) {
        DBManager dbManager = new DBManager();
        this.measurementName = dbManager.getMeasurementName(codeId);
        this.measurementUnits = dbManager.getMeasurementUnits(codeId);
        this.measurementMinValue = dbManager.getMinValue(codeId);
        this.measurementMaxValue = dbManager.getMaxValue(codeId);
    }

    private void getCurrentPatient(String personnummer) {
        DBManager dbManager = new DBManager();
        this.patient = dbManager.getPatientFromDatabase(personnummer);
    }

    public String getPersonnummer() {
        return personnummer;
    }

    public String getLabName() {
        return labName;
    }

    public String getLabPostalCode() {
        return labPostalCode;
    }

    public String getDateSimplified() {
        return date.substring(0, 10);
    }

    public Double getMeasurementValue() {
        //rounding double to 2 decimal places
        //adapted from: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
        return new BigDecimal(measurementValue).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public String getMeasurementName() {
        return measurementName;
    }

    public String getMeasurementUnits() {
        return measurementUnits;
    }

    public Double getMeasurementMaxValue() {
        return measurementMaxValue;
    }

    public Double getMeasurementMinValue() {
        return measurementMinValue;
    }

    @Override
    public String toString() {
        return getDateSimplified().concat(" (").concat(getLabName().concat(")"));
    }
}
