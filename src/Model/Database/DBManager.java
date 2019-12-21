package Model.Database;

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

    public void addPatientToDB(Patient patient) {
        Connection connection = connectToDB();
        String sql = "INSERT INTO patients (pn, firstName, lastName, birthDate, address, gender, phoneNumber)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, patient.getPersonnummer());
            preparedStatement.setString(2, patient.getFirstName());
            preparedStatement.setString(3, patient.getLastName());
            preparedStatement.setDate(4, Date.valueOf(patient.getBirthday()));
            preparedStatement.setString(5, patient.getAddress());
            preparedStatement.setString(6, patient.getGender());
            preparedStatement.setString(7, patient.getPhoneNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
