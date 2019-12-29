package model.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Measurement;
import model.MeasurementTaken;
import model.Patient;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
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

    // -------- TAKE_MEASUREMENTS -------
    public void addTakenMeasurementToDB(String personnummer, String labName, String labPostalCode, ArrayList<Measurement> takenMeasurements, LocalDate randomDate) {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;

        for (Measurement m : takenMeasurements) {
            String sql =
                    "INSERT INTO takes_measurement " +
                    "(patientPN, labName, labPostalCode, measurementCode, measurementValue, takenAt) " +
                    "VALUES (?, ?, ?, ?, ?, ?) ";
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, personnummer);
                preparedStatement.setString(2, labName);
                preparedStatement.setString(3, labPostalCode);
                preparedStatement.setInt(4, m.getMeasurementCode());
                preparedStatement.setDouble(5, m.getValue());
                preparedStatement.setDate(6, Date.valueOf(randomDate));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConnections(connection, preparedStatement);

    }


    // ------- POSTAL CODES -------
    //used solely to populate the 'postalCodes' table when creating the database
    public void fillPostalCodesTable() {
        Connection connection = connectToDB();
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
            closeConnections(connection, preparedStatement);
        }
    }


    // SELECT OPERATIONS
    // ------- PATIENTS -------------

    public Patient getPatientFromDatabase(String personnummer) {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT pn, CONCAT(firstName, ' ', lastName) AS name, birthDate," +
                "CONCAT(streetAddress, ', ', p.postalCode, ' - ', city) AS address," +
                "gender, phoneNumber " +
                "FROM patients" +
                "WHERE pn = ? ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Patient patient = new Patient(
                        resultSet.getString("pn"),
                        resultSet.getString("name"),
                        resultSet.getDate("birthDate").toLocalDate(),
                        resultSet.getString("address"),
                        resultSet.getString("gender"),
                        resultSet.getString("phoneNumber")
                );
                return patient;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
        return null;
    }

    public ObservableList<Patient> loadPatientsFromDatabase() {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql =
                "SELECT pn, CONCAT(firstName, ' ', lastName) AS name, birthDate," +
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

    // ------- LABORATORIES ------------

    public String findLabByPostalCode(String postalCode) {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql =
                "SELECT name, postalCode " +
                "FROM laboratories " +
                "WHERE postalCode = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, postalCode);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            } else {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
        return null;
    }

    public String[] findLabByCity(String patientPostalCode) {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String[] labNameAndPostalCode = new String[2];
        String sql =
                "SELECT name, l.postalCode " +
                "FROM laboratories l " +
                "JOIN postalCodes pc ON l.postalCode = pc.postalCode " +
                "WHERE city = (" +
                    "SELECT city " +
                    "FROM postalCodes " +
                    "WHERE postalCode = ?" +
                ");";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patientPostalCode);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            } else {
                labNameAndPostalCode[0] = resultSet.getString("name");
                labNameAndPostalCode[1] = resultSet.getString("postalCode");
                return labNameAndPostalCode;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
        return null;
    }

    // ------- MEASUREMENTS ------------

    public String getMeasurementName(Measurement.MeasurementCode code) {
        String name = null;
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql =
                "SELECT name " +
                        "FROM measurements " +
                        "WHERE code = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, code.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                name = resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
        return name;
    }

    public String getMeasurementUnits(Measurement.MeasurementCode code) {
        String units = null;
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql =
                "SELECT name " +
                        "FROM measurements " +
                        "WHERE code = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, code.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                units = resultSet.getString("units");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
        return units;
    }

    public double getMinValue(Measurement.MeasurementCode code) {
        double minValue = 0;
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql =
                "SELECT minimumValue " +
                        "FROM measurements " +
                        "WHERE code = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, code.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                minValue = resultSet.getDouble("minimumValue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
        return minValue;
    }

    public double getMaxValue(Measurement.MeasurementCode code) {
        double maxValue = 0;
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql =
                "SELECT maximumValue " +
                "FROM measurements " +
                "WHERE code = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, code.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                maxValue = resultSet.getDouble("maximumValue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
        return maxValue;
    }

    // -------- TAKEN MEASUREMENTS ------------

    //gets selected information about previous tests for a given patient
    public ArrayList<MeasurementTaken> loadPreviousMeasurementsByPatient(String personnummer) {
        ArrayList<MeasurementTaken> measurements = new ArrayList<>();
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT DISTINCT(takenAt), labName " +
                "FROM takes_measurement " +
                "WHERE patientPN = ? " +
                "ORDER BY takenAt asc";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, personnummer);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MeasurementTaken mt = new MeasurementTaken(
                        resultSet.getString("labName"),
                        resultSet.getString("takenAt")
                );
                measurements.add(mt);
            }
            return measurements;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
        return null;
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

    private void closeConnections(Connection connection, PreparedStatement preparedStatement) {
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