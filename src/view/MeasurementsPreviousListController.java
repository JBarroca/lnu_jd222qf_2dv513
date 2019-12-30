package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.MeasurementTaken;
import model.Patient;
import model.database.DBManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MeasurementsPreviousListController implements Initializable, PatientController {

    @FXML private Label patientNameLabel;
    @FXML private Label patientAgeLabel;
    @FXML private Label patientGenderLabel;
    @FXML private Label patientPNLabel;
    @FXML private Button backButton;

    @FXML private ListView<MeasurementTaken> testsList;
    @FXML private AnchorPane rootToMeasurementDetails;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @Override
    public void receivePatient(Patient patient) {
        patientNameLabel.setText("Name: " + patient.getName());
        patientAgeLabel.setText("Age: " + patient.getAge());
        patientGenderLabel.setText("Gender: " + patient.getGender());
        patientPNLabel.setText("Personnummer: " + patient.getPersonnummer());

        fillTestsList(patient);

        SceneChanger sceneChanger = new SceneChanger();
        testsList.getSelectionModel().selectedIndexProperty().addListener(e -> {
            try {
                sceneChanger.setView(rootToMeasurementDetails, "MeasurementDetailedView.fxml", testsList.getSelectionModel().getSelectedItem());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

    private void fillTestsList(Patient patient) {
        DBManager dbManager = new DBManager();
        testsList.getItems().addAll(dbManager.loadPreviousMeasurementSummary(patient.getPersonnummer()));
    }


    public void onBackButtonPressed(ActionEvent event) {
        SceneChanger sceneChanger = new SceneChanger();
        try {
            sceneChanger.changeScene(event, "HomeView.fxml", "Main Screen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
