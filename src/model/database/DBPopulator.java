package model.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DBPopulator {

    private DBManager dbManager = new DBManager();
    private ArrayList<String> personnummers = new ArrayList<>();
    private ArrayList<String[]> labNamesAndPostalCodes = new ArrayList<>();


    public void populateEntireDatabase() {
        populatePatientsTable();
        populateLaboratoriesTable();
    }


    public void populatePatientsTable() {
        PatientGenerator patientGenerator = new PatientGenerator();

        for (int i = 0; i < 1500; i++) {
            String[] patientData = patientGenerator.createRandomPatientData();
            personnummers.add(patientData[0]); //recording created personnummers
            dbManager.addPatientToDB(patientData);
        }

    }


    public void populateLaboratoriesTable() {
        LaboratoryGenerator laboratoryGenerator = new LaboratoryGenerator();
        String[] nameAndPostalCode = new String[2];

        for (int i = 0; i < 500; i++) {
            String[] laboratoryData = laboratoryGenerator.createRandomLaboratoryData();
            nameAndPostalCode[0] = laboratoryData[0];
            nameAndPostalCode[1] = laboratoryData[2];
            labNamesAndPostalCodes.add(nameAndPostalCode);
            dbManager.addLaboratoryToDB(laboratoryData);
        }
    }




    // ------- POSTAL CODES -------
    //used solely to populate the 'postalCodes' table when creating the database
    public void fillPostalCodesTable() {
        Connection connection = dbManager.connectToDB();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO postalCodes (postalCode, city) VALUES (?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            File postalCodesFile = new File("resources/postalCodesAndCities.txt");
            Scanner sc = new Scanner(postalCodesFile);

            while (sc.hasNextLine()) {
                String[] pair = sc.nextLine().split("\\*");
                preparedStatement.setString(1, pair[0]);
                preparedStatement.setString(2, pair[1]);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeConnections(connection, preparedStatement);
        }
    }
}
