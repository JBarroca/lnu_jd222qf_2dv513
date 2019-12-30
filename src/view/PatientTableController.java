package view;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.database.DBManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PatientTableController implements Initializable, PatientController {

    @FXML
    private TableView<Patient> patientTableView;
    @FXML
    private TableColumn<Patient, String> patientPersonnummerColumn;
    @FXML
    private TableColumn<Patient, String> patientNameColumn;
    @FXML
    private TableColumn<Patient, Integer> patientAgeColumn;
    @FXML
    private TableColumn<Patient, String> patientGenderColumn;
    @FXML
    private TableColumn<Patient, String> patientAddressColumn;
    @FXML
    private TableColumn<Patient, String> patientPhoneNumberColumn;


    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        //defining columns
        patientPersonnummerColumn.setCellValueFactory(new PropertyValueFactory<>("personnummer"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patientAgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        patientGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        patientAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        patientPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        //load database patients into TableView
        DBManager dbManager = new DBManager();
        patientTableView.getItems().addAll(dbManager.loadPatientsFromDatabase());

        //setting up double-click listener to select patient and switch to his/her view
        patientTableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && (event.getClickCount() == 2)) {
                Patient selectedPatient = patientTableView.getSelectionModel().getSelectedItem();
                if (selectedPatient != null) {
                    try {
                        SceneChanger sceneChanger = new SceneChanger();
                        sceneChanger.changeScene(event, "MeasurementsPreviousListView.fxml", "Patient measurements", selectedPatient);
                    } catch (IOException e) {
                        System.err.println("Error while changing to Patient's measurements view: ");
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void receivePatient(Patient patient) {

    }
}
