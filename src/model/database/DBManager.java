package model.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Measurement;
import model.Patient;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DBManager {

    public Connection connectToDB() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/2dv513_Ass3?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "testing", "testing");
        } catch (SQLException e) {
            System.out.println("Error when establishing connection to DB:");
            e.printStackTrace();
        }
        return connection;
    }

    // INSERT OPERATIONS
    // ------ PATIENTS ----------

    public void addPatientToDB(String[] patientData) {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO patients (pn, firstName, lastName, birthDate, streetAddress, postalCode, gender, phoneNumber)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patientData[0]);
            preparedStatement.setString(2, patientData[1]);
            preparedStatement.setString(3, patientData[2]);
            preparedStatement.setDate(4, Date.valueOf(patientData[3]));
            preparedStatement.setString(5, patientData[4]);
            preparedStatement.setString(6, patientData[5]);
            preparedStatement.setString(7, patientData[6]);
            preparedStatement.setString(8, patientData[7]);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when inserting patient into Database: ");
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement);
        }
    }

    // ------- LABORATORIES -------

    public void addLaboratoryToDB(String[] laboratoryData) {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO laboratories (name, streetAddress, postalCode, phoneNumber) " +
                "VALUES (?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, laboratoryData[0]);
            preparedStatement.setString(2, laboratoryData[1]);
            preparedStatement.setString(3, laboratoryData[2]);
            preparedStatement.setString(4, laboratoryData[3]);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when inserting laboratory into Database: ");
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement);
        }
    }


    // SELECT OPERATIONS
    // ------- PATIENTS -------------

    public ObservableList<Patient> loadPatientsFromDatabase() {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT pn, CONCAT(firstName, ' ', lastName) AS name, birthDate," +
                "CONCAT(streetAddress, ', ', p.postalCode, ' - ', city) AS address," +
                "gender, phoneNumber " +
                "FROM patients p " +
                "JOIN postalCodes pc " +
                "WHERE p.postalCode = pc.postalCode ";

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            //creating a Patient object from each returned result:
            while (resultSet.next()) {
                Patient patient = new Patient(
                        resultSet.getString("pn"),
                        resultSet.getString("name"),
                        resultSet.getDate("birthDate").toLocalDate(),
                        resultSet.getString("address"),
                        resultSet.getString("gender"),
                        resultSet.getString("phoneNumber")
                );
                patients.add(patient);
            }
        } catch (SQLException e) {
            System.out.println("Error when fetching Patients from database: \n");
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }

        return patients;
    }



    // ------- MEASUREMENTS ------------

    public double getMaxValue(Measurement.MeasurementCode code) {
        double maxValue = 0;
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT maxValue FROM measurements WHERE code = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, code.getId());
            resultSet = preparedStatement.executeQuery();
            maxValue = resultSet.getDouble("maxValue");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
        return maxValue;
    }

    public double getMinValue(Measurement.MeasurementCode code) {
        double minValue = 0;
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT minValue FROM measurements WHERE code = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, code.getId());
            resultSet = preparedStatement.executeQuery();
            minValue = resultSet.getDouble("minValue");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
        return minValue;
    }

    private void closeConnections(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("Error when closing connection to DB: ");
            e.printStackTrace();
        }
    }

    public void closeConnections(Connection connection, PreparedStatement preparedStatement) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println("Error when closing connection to DB: ");
            e.printStackTrace();
        }
    }



}
