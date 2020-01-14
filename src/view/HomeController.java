package view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Patient;
import model.database.DBManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML private TableView<Patient> patientTableView;
    @FXML private TableColumn<Patient, String> patientPersonnummerColumn;
    @FXML private TableColumn<Patient, String> patientNameColumn;
    @FXML private TableColumn<Patient, Integer> patientAgeColumn;
    @FXML private TableColumn<Patient, String> patientGenderColumn;
    @FXML private TableColumn<Patient, String> patientAddressColumn;
    @FXML private TableColumn<Patient, String> patientPhoneNumberColumn;

    @FXML private Label numberOfPatientsLabel;
    @FXML private TextField personnummerFilterInput;
    @FXML private TextField nameFilterInput;
    @FXML private RadioButton genderMFilterRadioButton;
    @FXML private RadioButton genderFFilterRadioButton;
    private ToggleGroup genderFilterToggleGroup;
    @FXML private TextField addressFilterInput;
    @FXML private TextField postalCodeFilterInput;
    @FXML private ComboBox<String> cityFilterComboBox;
    @FXML private TextField phoneNumberFilterInput;

    @FXML private TextField addPatientPNInput;
    @FXML private Label addPatientPNErrorLabel;
    @FXML private TextField addPatientFirstNameInput;
    @FXML private Label addPatientFirstNameErrorLabel;
    @FXML private TextField addPatientLastNameInput;
    @FXML private Label addPatientLastNameErrorLabel;
    @FXML private RadioButton addPatientGenderMRadioButton;
    @FXML private RadioButton addPatientGenderFRadioButton;
    private ToggleGroup addPatientGenderToggleGroup;
    @FXML private Label addPatientGenderErrorLabel;
    @FXML private DatePicker addPatientDateOfBirthPicker;
    @FXML private Label addPatientDOBErrorLabel;
    @FXML private TextField addPatientAddressInput;
    @FXML private Label addPatientAddressErrorLabel;
    @FXML private TextField addPatientPostalCodeInput;
    @FXML private Label addPatientPostalCodeErrorLabel;
    @FXML private TextField addPatientPhoneNumberInput;
    @FXML private Label addPatientPhoneNumberErrorLabel;
    @FXML private Label addPatientButtonLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearErrorLabels();

        //setting up ToggleGroups and their Radio Buttons
        genderFilterToggleGroup = new ToggleGroup();
        genderMFilterRadioButton.setToggleGroup(genderFilterToggleGroup);
        genderFFilterRadioButton.setToggleGroup(genderFilterToggleGroup);
        addPatientGenderToggleGroup = new ToggleGroup();
        addPatientGenderMRadioButton.setToggleGroup(addPatientGenderToggleGroup);
        addPatientGenderFRadioButton.setToggleGroup(addPatientGenderToggleGroup);

        //populating Filter Cities ComboBox
        DBManager dbManager = new DBManager();
        cityFilterComboBox.getItems().addAll(dbManager.getCitiesFromDatabase());

        //populating database patients into TableView
        loadPatientTableView();

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

    private void loadPatientTableView() {
        //defining TableView columns
        patientPersonnummerColumn.setCellValueFactory(new PropertyValueFactory<>("personnummer"));
        patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        patientAgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        patientGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        patientAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        patientPhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        DBManager dbManager = new DBManager();
        ObservableList<Patient> patients = dbManager.loadPatientsFromDatabase();
        patientTableView.getItems().addAll(patients);
        numberOfPatientsLabel.setText(patients.size() + " patients displayed");
    }

    public void onAddPatientButtonPressed(ActionEvent event) {
        clearErrorLabels();
        DBManager dbManager = new DBManager();
        Patient patient = new Patient();

        try {
            patient.setPersonnummer(addPatientPNInput.getText());
        } catch (IllegalArgumentException e) {
            addPatientPNErrorLabel.setText(e.getMessage());
        }

        try {
            patient.setFirstName(addPatientFirstNameInput.getText());
        } catch (Exception e) {
            addPatientFirstNameErrorLabel.setText(e.getMessage());
        }

        try {
            patient.setLastName(addPatientLastNameInput.getText());
        } catch (Exception e) {
            addPatientLastNameErrorLabel.setText(e.getMessage());
        }

        try {
            patient.setBirthday(addPatientDateOfBirthPicker.getValue());
        } catch (Exception e) {
            addPatientDOBErrorLabel.setText(e.getMessage());
        }

        try {
            String gender = null;
            if (addPatientGenderToggleGroup.getSelectedToggle() != null) {
                if (addPatientGenderToggleGroup.getSelectedToggle().equals(addPatientGenderMRadioButton)) {
                    gender = "M";
                } else if (addPatientGenderToggleGroup.getSelectedToggle().equals(addPatientGenderFRadioButton)) {
                    gender = "F";
                }
            }
            patient.setGender(gender);
        } catch (Exception e) {
            addPatientGenderErrorLabel.setText(e.getMessage());
        }

        try {
            patient.setAddress(addPatientAddressInput.getText());
        } catch (Exception e) {
            addPatientAddressErrorLabel.setText(e.getMessage());
        }

        try {
            patient.setPostalCode(addPatientPostalCodeInput.getText());
        } catch (Exception e) {
            addPatientPostalCodeErrorLabel.setText(e.getMessage());
        }

        try {
            patient.setPhoneNumber(addPatientPhoneNumberInput.getText());
        } catch (Exception e) {
            addPatientPhoneNumberErrorLabel.setText(e.getMessage());
        }

        try {
            dbManager.addPatientToDB(patient);
            clearAddPatientInputs();
            addPatientButtonLabel.setText("Patient successfully created");
        } catch (Exception e) {
            //if any SQL exception due to any sort of constraint violation
            addPatientButtonLabel.setText("Patient could not be created");
        }

    }

    public void clearErrorLabels() {
        addPatientPNErrorLabel.setText("");
        addPatientFirstNameErrorLabel.setText("");
        addPatientLastNameErrorLabel.setText("");
        addPatientGenderErrorLabel.setText("");
        addPatientDOBErrorLabel.setText("");
        addPatientAddressErrorLabel.setText("");
        addPatientPostalCodeErrorLabel.setText("");
        addPatientPhoneNumberErrorLabel.setText("");
        addPatientButtonLabel.setText("");
    }

    public void onApplyFilterButtonPressed(ActionEvent event) {
        //re-populate the table with the filtered patients
        DBManager dbManager = new DBManager();
        ObservableList<Patient> patients = dbManager.loadPatientsFromDatabase(collectFilters());
        patientTableView.getItems().setAll(patients);
        numberOfPatientsLabel.setText(patients.size() + " patients displayed");
    }

    public void onClearAllButtonPressed(ActionEvent event) {
        clearErrorLabels();
        clearAddPatientInputs();
    }

    private void clearAddPatientInputs() {
        addPatientPNInput.setText("");
        addPatientFirstNameInput.setText("");
        addPatientLastNameInput.setText("");
        addPatientDateOfBirthPicker.getEditor().setText("");
        addPatientGenderMRadioButton.setSelected(false);
        addPatientGenderFRadioButton.setSelected(false);
        addPatientAddressInput.setText("");
        addPatientPostalCodeInput.setText("");
        addPatientPhoneNumberInput.setText("");
    }

    private ArrayList<String> collectFilters() {
        ArrayList<String> filters = new ArrayList<>();
        filters.add(0, getInput(personnummerFilterInput)); //PN
        filters.add(1, getInput(nameFilterInput)); //name
        filters.add(2, getInput(addressFilterInput)); //address
        filters.add(3, getInput(postalCodeFilterInput)); //postalCode
        filters.add(4, getInput(cityFilterComboBox)); //city
        filters.add(5, getGenderInput()); //gender
        filters.add(6, getInput(phoneNumberFilterInput)); //phoneNumber
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
        if (genderFilterToggleGroup.getSelectedToggle() != null) {
            if (genderFilterToggleGroup.getSelectedToggle().equals(genderMFilterRadioButton)) {
                return "M";
            } else if (genderFilterToggleGroup.getSelectedToggle().equals(genderFFilterRadioButton)) {
                return "F";
            }
        }
        return "";
    }

    public void clearPersonnummerInput(ActionEvent event) {
        personnummerFilterInput.setText("");
        onApplyFilterButtonPressed(event);
    }
    public void clearNameInput(ActionEvent event) {
        nameFilterInput.setText("");
        onApplyFilterButtonPressed(event);
    }
    public void clearGenderInput(ActionEvent event) {
        genderMFilterRadioButton.setSelected(false);
        genderFFilterRadioButton.setSelected(false);
        onApplyFilterButtonPressed(event);
    }
    public void clearAddressInput(ActionEvent event) {
        addressFilterInput.setText("");
        onApplyFilterButtonPressed(event);
    }
    public void clearPostalCodeInput(ActionEvent event) {
        postalCodeFilterInput.setText("");
        onApplyFilterButtonPressed(event);
    }
    public void clearCityInput(ActionEvent event) {
        cityFilterComboBox.getSelectionModel().clearSelection();
        cityFilterComboBox.setValue(null);
        onApplyFilterButtonPressed(event);
    }
    public void clearPhoneNumberInput(ActionEvent event) {
        phoneNumberFilterInput.setText("");
        onApplyFilterButtonPressed(event);
    }




}
