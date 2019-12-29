package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MeasurementTaken;
import model.Patient;
import model.database.DBManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MeasurementsPreviousController implements Initializable, PatientController {

    @FXML private Label patientNameLabel;
    @FXML private Label patientAgeLabel;
    @FXML private Label patientGenderLabel;
    @FXML private Label patientPNLabel;
    @FXML private Button backButton;

    @FXML private TableView<MeasurementTaken> previousTestsTable;
    @FXML private TableColumn<MeasurementTaken, String> testDateColumn;
    @FXML private TableColumn<MeasurementTaken, String> testLabColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        testDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        testLabColumn.setCellValueFactory(new PropertyValueFactory<>("labName"));
    }

    @Override
    public void receivePatient(Patient patient) {
        patientNameLabel.setText("Name: " + patient.getName());
        patientAgeLabel.setText("Age: " + patient.getAge());
        patientGenderLabel.setText("Gender: " + patient.getGender());
        patientPNLabel.setText("Personnummer: " + patient.getPersonnummer());

        DBManager dbManager = new DBManager();
        previousTestsTable.getItems().addAll(dbManager.loadPreviousMeasurementsByPatient(patient.getPersonnummer()));
    }

    public void onBackButtonPressed(ActionEvent event) {
        SceneChanger sceneChanger = new SceneChanger();
        try {
            sceneChanger.changeScene(event, "PatientListView.fxml", "Patients in database");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
