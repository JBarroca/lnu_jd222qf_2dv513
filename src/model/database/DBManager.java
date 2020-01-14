package model.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Measurement;
import model.MeasurementTaken;
import model.Patient;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
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

    public void addPatientToDB(Patient patient) throws SQLException {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO patients (pn, firstName, lastName, birthDate, streetAddress, postalCode, gender, phoneNumber)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient.getPersonnummer());
            preparedStatement.setString(2, patient.getFirstName());
            preparedStatement.setString(3, patient.getLastName());
            preparedStatement.setDate(4, Date.valueOf(patient.getBirthday()));
            preparedStatement.setString(5, patient.getAddress());
            preparedStatement.setString(6, patient.getPostalCode());
            preparedStatement.setString(7, patient.getGender());
            preparedStatement.setString(8, patient.getPhoneNumber());
            preparedStatement.executeUpdate();
        } finally {
            closeConnections(connection, preparedStatement);
        }
    }

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
    public void addTakenMeasurementToDB(String personnummer, String labName, String labPostalCode, ArrayList<Measurement> takenMeasurements, LocalDate date) {
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
                preparedStatement.setDate(6, Date.valueOf(date));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        closeConnections(connection, preparedStatement);

    }


    // ------- POSTAL CODES -------
    //was used solely to populate the 'postalCodes' table when creating the database
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

    // ------- POSTAL CODES -------------
    public ArrayList<String> getPostalCodesList() {
        ArrayList<String> postalCodesInDatabase = new ArrayList<>();
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT postalCode FROM postalCodes";

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                postalCodesInDatabase.add(resultSet.getString("postalCode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
        return postalCodesInDatabase;
    }

    // ------- PATIENTS -------------
    public Patient getPatientFromDatabase(String personnummer) {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT pn, CONCAT(firstName, ' ', lastName) AS name, birthDate," +
                "CONCAT(streetAddress, ', ', p.postalCode, ' - ', city) AS address," +
                "gender, phoneNumber " +
                "FROM patients p " +
                "JOIN postalCodes pc ON p.postalCode = pc.postalCode " +
                "WHERE pn = ? ";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, personnummer);
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

    //no arguments = all patients
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
                "ON p.postalCode = pc.postalCode ";

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

    //overload with list = filtering database
    public ObservableList<Patient> loadPatientsFromDatabase(ArrayList<String> filters) {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = buildPatientFilterString(filters);

        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
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
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
        return patients;
    }

    private String buildPatientFilterString(ArrayList<String> filters) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT pn, CONCAT(firstName, ' ', lastName) AS name, birthDate, " +
                "CONCAT(streetAddress, ', ', p.postalCode, ' - ', city) AS address, " +
                "gender, phoneNumber FROM patients p ").
                append("JOIN postalCodes pc ON p.postalCode = pc.postalCode ");

        //if every filter is blank, return SQL string as is
        if (noFiltersUsed(filters)) {
            System.out.println("No filters used");
            return sb.toString();
        }
        sb.append("WHERE ");
        boolean isFirst = true;

        if (!filters.get(0).isBlank()) {
            if (!isFirst) {
                sb.append(" AND ");
            }
            sb.append("pn LIKE '%").append(filters.get(0)).append("%'");
            isFirst = false;
        }

        if (!filters.get(1).isBlank()) {
            if (!isFirst) {
                sb.append(" AND ");
            }
            sb.append("(firstName LIKE '%").append(filters.get(1)).append("%'");
            sb.append(" OR lastName LIKE '%").append(filters.get(1)).append("%')");
            isFirst = false;
        }

        if (!filters.get(2).isBlank()) {
            if (!isFirst) {
                sb.append(" AND ");
            }
            sb.append("streetAddress LIKE '%").append(filters.get(2)).append("%'");
            isFirst = false;
        }

        if (!filters.get(3).isBlank()) {
            if (!isFirst) {
                sb.append(" AND ");
            }
            sb.append("p.postalCode LIKE '%").append(filters.get(3)).append("%'");
            isFirst = false;
        }

        if (!filters.get(4).isBlank()) {
            if (!isFirst) {
                sb.append(" AND ");
            }
            sb.append("pc.city = '").append(filters.get(4)).append("'");
            isFirst = false;
        }

        if (!filters.get(5).isBlank()) {
            if (!isFirst) {
                sb.append(" AND ");
            }
            sb.append("gender = '").append(filters.get(5)).append("'");
            isFirst = false;
        }

        if (!filters.get(6).isBlank()) {
            if (!isFirst) {
                sb.append(" AND ");
            }
            sb.append("phoneNumber LIKE '%").append(filters.get(6)).append("%'");
        }

        System.out.println("SQL string from Filter: " + sb.toString());
        return sb.toString();
    }

    private boolean noFiltersUsed(ArrayList<String> filters) {
        ArrayList<String> filters1 = new ArrayList<>(filters);
        filters1.removeIf(s -> s.isBlank());
        return filters1.size() == 0;
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

    public String getMeasurementName(int code) {
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
            preparedStatement.setInt(1, code);
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

    public String getMeasurementUnits(int code) {
        String units = null;
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql =
                "SELECT units " +
                "FROM measurements " +
                "WHERE code = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, code);
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
        //some measurements have no units (ex: INR)
        if (units != null && !units.isEmpty()) {
            return units;
        }
        return "";
    }

    public double getMinValue(int code) {
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
            preparedStatement.setInt(1, code);
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

    public double getMaxValue(int code) {
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
            preparedStatement.setInt(1, code);
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
    //gets every test for a given measurement on a given patient
    public ArrayList<MeasurementTaken> loadMeasurementsOfATest(String personnummer, String date) {
        ArrayList<MeasurementTaken> measurements = new ArrayList<>();
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql =
                "SELECT * FROM takes_measurement " +
                "WHERE patientPN = ? AND takenAt LIKE ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, personnummer);
            preparedStatement.setString(2, "%".concat(date).concat("%"));

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MeasurementTaken mt = new MeasurementTaken(
                        resultSet.getString("patientPN"),
                        resultSet.getString("labName"),
                        resultSet.getString("labPostalCode"),
                        resultSet.getString("takenAt"),
                        resultSet.getDouble("measurementValue"),
                        resultSet.getInt("measurementCode")
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

    //gets selected information about previous tests for a given patient
    public ArrayList<MeasurementTaken> loadPreviousMeasurementSummary(String personnummer) {
        ArrayList<MeasurementTaken> measurements = new ArrayList<>();
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT DISTINCT(takenAt), labName, patientPN " +
                "FROM takes_measurement " +
                "WHERE patientPN = ? " +
                "ORDER BY takenAt asc";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, personnummer);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MeasurementTaken mt = new MeasurementTaken(
                        resultSet.getString("patientPN"),
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

    // -------- CITIES ------

    public ArrayList<String> getCitiesFromDatabase() {
        ArrayList<String> cities = new ArrayList<>();
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet = null;

        String sqlCreateView = "CREATE VIEW populatedCities " +
                "AS SELECT city, pc.postalCode, pn FROM postalCodes pc " +
                "JOIN patients p ON pc.postalCode = p.postalCode ";

        String sqlQueryView = "SELECT DISTINCT(city) FROM populatedCities " +
                "ORDER BY city ASC ";

        try {
            //testing querying the view
            preparedStatement1 = connection.prepareStatement(sqlQueryView);
            preparedStatement1.executeQuery();
        } catch (Exception e) {
            //if view does not exist yet, we now create it before querying again
            try {
                preparedStatement = connection.prepareStatement(sqlCreateView);
                preparedStatement.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        try {
            preparedStatement1 = connection.prepareStatement(sqlQueryView);
            resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                cities.add(resultSet.getString("city"));
            }
            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement, preparedStatement1, resultSet);
        }
        return null;
    }


    // Overloaded methods for closing JDBC connections and objects

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

    private void closeConnections(Connection connection, PreparedStatement preparedStatement, PreparedStatement preparedStatement1, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (preparedStatement1 != null) {
                preparedStatement1.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.out.println("Error when closing connection to DB: ");
            e.printStackTrace();
        }
    }
}