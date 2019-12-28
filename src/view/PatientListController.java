package view;

import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.database.DBManager;

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

    }
}
