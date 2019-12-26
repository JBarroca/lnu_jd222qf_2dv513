package controller;

import model.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientListController implements Initializable {

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

    }
}
