package model.database;

import model.Measurement;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;

public class DBPopulator {

    private DBManager dbManager = new DBManager();
    private ArrayList<String> patientPersonnummers = new ArrayList<>();
    private ArrayList<String> patientPostalCodes = new ArrayList<>();

    private ArrayList<String[]> personnummersAndPostalCodes = new ArrayList<>();
    private ArrayList<String[]> labNamesAndPostalCodes = new ArrayList<>();


    public void populateEntireDatabase() {
        System.out.println("populating 'patients' table...");
        populatePatientsTable(400);
        System.out.println("'patients' table populated.");

        System.out.println("populating 'laboratories' table...");
        populateLaboratoriesTable(100);
        System.out.println("'laboratories' table populated.");

        System.out.println("populating 'takes_measurement' table...");
        populateTakesMeasurementTable();
        System.out.println("'takes_measurement' table populated.");

        System.out.println("Database successfully populated and ready to use.");
    }

    public void populateTakesMeasurementTable() {
        Random rnd = new Random();
        //for each patient in the patients table...
        for (String[] patientData : personnummersAndPostalCodes) {
            String personnummer = patientData[0];
            String patientPostalCode = patientData[1];

            String labName;
            String labPostalCode;
            //if there is a lab in the patient's postal code, use it
            if (dbManager.findLabByPostalCode(patientPostalCode) != null) {
                labPostalCode = patientPostalCode;
                labName = dbManager.findLabByPostalCode(labPostalCode);
                System.out.println("lab " + labName + ", assigned by patient's postal code");

            //if there is a lab in the patient's city, use it
            } else if (dbManager.findLabByCity(patientPostalCode) != null) {
                //there is at least 1 lab in the patient's city, so use it:
                String[] labNameAndPostalCode = dbManager.findLabByCity(patientPostalCode);
                labName = labNameAndPostalCode[0];
                labPostalCode = labNameAndPostalCode[1];
                System.out.println("lab " + labName + ", assigned by patient's city");

            //if no lab nearby, use any lab from the created labs list
            } else {
                //selecting a random postal code among all the created labs
                int randomIndex = rnd.nextInt(labNamesAndPostalCodes.size());
                labPostalCode = labNamesAndPostalCodes.get(randomIndex)[1];
                labName = dbManager.findLabByPostalCode(labPostalCode);
                System.out.println("lab " + labName + ", assigned by 'other'");
            }

            //we have a patient(PN) and a suitable lab (name+postal code)
            //now we create measurements for that patient in that lab
            //how many measurements? - between 2 and 10 per patient
            int numberOfTests = 2 + rnd.nextInt(9);

            for (int i = 0; i < numberOfTests; i++) {
                //ONE INDIVIDUAL RECORD
                int packToTake = 1 + rnd.nextInt(6);
                MeasurementGenerator measurementGenerator = new MeasurementGenerator();
                ArrayList<Measurement> takenMeasurements = measurementGenerator.getSingleMeasurementPack(packToTake);

                LocalDate randomDate = getRandomMeasurementTimestamp();

                //RECORD PACK INTO DB!
                dbManager.addTakenMeasurementToDB(personnummer, labName, labPostalCode, takenMeasurements, randomDate);
            }
        }
    }

    public LocalDate getRandomMeasurementTimestamp() {
        Random rnd = new Random();
        long miliseconds2010Jan01 = 1262304000000L;
        //random date from 2010-Jan-01 until 10 years later:
        long milliseconds = miliseconds2010Jan01 + (Math.abs(rnd.nextLong()) % (10L * 365 * 24 * 60 * 60 * 1000));
        Date date = new Date(milliseconds);
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //Timestamp timestamp = new Timestamp(milliseconds);
        //return timestamp;
    }

    public void populatePatientsTable(int numberOfPatients) {
        PatientGenerator patientGenerator = new PatientGenerator();

        for (int i = 0; i < numberOfPatients; i++) {
            String[] patientData = patientGenerator.createRandomPatientData();
            //recording created patients' personnummers (key) and postal codes
            patientPersonnummers.add(patientData[0]);
            patientPostalCodes.add(patientData[5]);

            //alternative storage
            String[] pnAndPostalCode = new String[2];
            pnAndPostalCode[0] = patientData[0];
            pnAndPostalCode[1] = patientData[5];
            personnummersAndPostalCodes.add(pnAndPostalCode);

            //record patient in 'Patients' table
            dbManager.addPatientToDB(patientData);
        }

    }


    public void populateLaboratoriesTable(int numberOfLabs) {
        LaboratoryGenerator laboratoryGenerator = new LaboratoryGenerator();

        for (int i = 0; i < numberOfLabs; i++) {
            String[] laboratoryData = laboratoryGenerator.createRandomLaboratoryData();
            //recording created Laboratories' name and postal codes (keys for lab)
            String[] nameAndPostalCode = new String[2];
            nameAndPostalCode[0] = laboratoryData[0]; //lab name
            nameAndPostalCode[1] = laboratoryData[2]; //lab postal code
            labNamesAndPostalCodes.add(nameAndPostalCode);
            dbManager.addLaboratoryToDB(laboratoryData);
        }
    }

}
