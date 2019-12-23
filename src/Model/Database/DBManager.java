package Model.Database;

import Model.Measurement;
import Model.Patient;

import java.sql.*;

public class DBManager {

    public Connection connectToDB() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/2dv513_Ass3?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", "testing", "testing");
        } catch (SQLException e) {
            System.out.println("Error when establishing connection to DB");
            e.printStackTrace();
        }
        return connection;
    }

    // INSERT OPERATIONS
    // ------ PATIENTS ----------

    public void addPatientToDB(Patient patient) {
        Connection connection = connectToDB();
        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO patients (pn, firstName, lastName, birthDate, streetAddress, postalCode, city, gender, phoneNumber)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient.getPersonnummer());
            preparedStatement.setString(2, patient.getFirstName());
            preparedStatement.setString(3, patient.getLastName());
            preparedStatement.setDate(4, Date.valueOf(patient.getBirthday()));
            preparedStatement.setString(5, patient.getStreetAddress());
            preparedStatement.setString(6, patient.getPostalCode());
            preparedStatement.setString(7, patient.getCity());
            preparedStatement.setString(8, patient.getGender());
            preparedStatement.setString(9, patient.getPhoneNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections(connection, preparedStatement);
        }
    }


    // SELECT OPERATIONS
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
