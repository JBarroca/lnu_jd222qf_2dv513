package view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Patient;
import model.database.DBManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML private AnchorPane rootForPatientTableView;

    @FXML private TableView<Patient> patientTableView;
    @FXML private TableColumn<Patient, String> patientPersonnummerColumn;
    @FXML private TableColumn<Patient, String> patientNameColumn;
    @FXML private TableColumn<Patient, Integer> patientAgeColumn;
    @FXML private TableColumn<Patient, String> patientGenderColumn;
    @FXML private TableColumn<Patient, String> patientAddressColumn;
    @FXML private TableColumn<Patient, String> patientPhoneNumberColumn;

    @FXML private Label numberOfPatientsLabel;
    @FXML private TextField personnummerInput;
    @FXML private TextField nameInput;
    @FXML private RadioButton genderMRadioButton;
    @FXML private RadioButton genderFRadioButton;
    private ToggleGroup genderToggleGroup;
    @FXML private TextField addressInput;
    @FXML private TextField postalCodeInput;
    @FXML private ComboBox<String> cityComboBox;
    @FXML private TextField phoneNumberInput;
    @FXML private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");
        genderToggleGroup = new ToggleGroup();
        genderMRadioButton.setToggleGroup(genderToggleGroup);
        genderFRadioButton.setToggleGroup(genderToggleGroup);

        //defining columns
        patientPersonnummerColumn.setCellValueFactory(new PropertyValueFactory<>("personnummer"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patientAgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        patientGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        patientAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        patientPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        //load database patients into TableView
        DBManager dbManager = new DBManager();
        ObservableList<Patient> patients = dbManager.loadPatientsFromDatabase();
        patientTableView.getItems().addAll(patients);
        numberOfPatientsLabel.setText(patients.size() + "patients displayed");

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

        //populating Filter Cities ComboBox
        cityComboBox.getItems().addAll(dbManager.getCitiesFromDatabase());
    }

    private ArrayList<String> collectFilters() {
        ArrayList<String> filters = new ArrayList<>();

        filters.add(0, getInput(personnummerInput)); //PN
        System.out.println("input:" + filters.get(0));
        System.out.println("PN is blank: " + filters.get(0).isBlank());

        filters.add(1, getInput(nameInput)); //name
        System.out.println("input: " + filters.get(1));
        System.out.println("name is blank: " + filters.get(1).isBlank());

        filters.add(2, getInput(addressInput)); //address
        System.out.println("input: " + filters.get(2));
        System.out.println("address is blank: " + filters.get(2).isBlank());

        filters.add(3, getInput(postalCodeInput)); //postalCode
        System.out.println("input: " + filters.get(3));
        System.out.println("postalCode is blank: " + filters.get(3).isBlank());

        filters.add(4, getInput(cityComboBox)); //city
        System.out.println("input: " + filters.get(4));
        System.out.println("city is blank: " + filters.get(4).isBlank());

        filters.add(5, getGenderInput()); //gender
        System.out.println("input: " + filters.get(5));
        System.out.println("gender is blank: " + filters.get(5).isBlank());

        filters.add(6, getInput(phoneNumberInput)); //phoneNumber
        System.out.println("input: " + filters.get(6));
        System.out.println("phoneNumber is blank: " + filters.get(6).isBlank());

        return filters;
    }

    private String getInput(TextField textField) {
        if (!textField.getText().isBlank()) {
            return textField.getText();
        }
        return "";
    }

    private String getInput(ComboBox<String> comboBox) {
        if (!comboBox.getSelectionModel().isEmpty()) {
            return comboBox.getValue();
        }
        return "";
    }

    private String getGenderInput() {
        if (genderToggleGroup.getSelectedToggle() != null) {
            if (genderToggleGroup.getSelectedToggle().equals(genderMRadioButton)) {
                return "M";
            } else if (genderToggleGroup.getSelectedToggle().equals(genderFRadioButton)) {
                return "F";
            }
        }
        return "";
    }

    public void onApplyFilterButtonPressed(ActionEvent event) {
        errorLabel.setText("");
        //re-populate the table with the filtered patients
        DBManager dbManager = new DBManager();
        ObservableList<Patient> patients = dbManager.filterPatientsFromDatabase(collectFilters());
        patientTableView.getItems().setAll(patients);
        numberOfPatientsLabel.setText(patients.size() + "patients displayed");

    }
}
