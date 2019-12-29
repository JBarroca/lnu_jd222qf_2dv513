package model;

import model.database.DBManager;

public class MeasurementTaken {

    private String personnummer;
    private Patient patient;

    private String labName;
    private String labPostalCode;
    private String date;

    private int numberOfMeasurements;
    private String measurementName;
    private Double measurementValue;
    private String measurementUnits;
    private Double measurementMinValue;
    private Double measurementMaxValue;

    public MeasurementTaken(String personnummer, String labName, String labPostalCode, String date, int numberOfMeasurements, Double measurementValue, Measurement.MeasurementCode code) {
        this.personnummer = personnummer;
        this.labName = labName;
        this.labPostalCode = labPostalCode;
        this.date = date;
        this.numberOfMeasurements = numberOfMeasurements;
        this.measurementValue = measurementValue;
        //name, units, min and max are obtained from the 'measurements' table
        getMeasurementInfo(code);
        getCurrentPatient(personnummer);
    }

    public MeasurementTaken(String labName, String date) {
        this.labName = labName;
        this.date = date;
    }

    private void getMeasurementInfo(Measurement.MeasurementCode code) {
        DBManager dbManager = new DBManager();
        this.measurementName = dbManager.getMeasurementName(code);
        this.measurementUnits = dbManager.getMeasurementUnits(code);
        this.measurementMinValue = dbManager.getMinValue(code);
        this.measurementMaxValue = dbManager.getMaxValue(code);
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

    public String getDate() {
        return date.substring(0, 10);
    }

    public Double getMeasurementValue() {
        return measurementValue;
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


}
