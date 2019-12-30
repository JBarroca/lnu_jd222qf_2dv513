package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.MeasurementTaken;
import model.database.DBManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MeasurementDetailedController implements Initializable, MeasurementController {

    @FXML private ListView<String> testsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        testsList.setEditable(false);
    }

    @Override
    public void receiveMeasurement(MeasurementTaken measurementTaken) {

        DBManager dbManager = new DBManager();
        String personnummer = measurementTaken.getPersonnummer();
        String date = measurementTaken.getDateSimplified();

        ArrayList<MeasurementTaken> measurements = dbManager.loadMeasurementsOfATest(personnummer, date);
        ArrayList<String> measurementTexts = buildMeasurementTexts(measurements);
        testsList.getItems().addAll(measurementTexts);
    }

    public ArrayList<String> buildMeasurementTexts(ArrayList<MeasurementTaken> measurements) {
        ArrayList<String> texts = new ArrayList<>();
        for (MeasurementTaken mt : measurements) {
            StringBuilder sb = new StringBuilder();

            sb.append(mt.getMeasurementName()).append(" - ");
            sb.append(mt.getMeasurementValue()).append(" ");
            sb.append(mt.getMeasurementUnits()).append(" (");
            sb.append(mt.getMeasurementMinValue()).append(" - ");
            sb.append(mt.getMeasurementMaxValue()).append(" ");
            sb.append(mt.getMeasurementUnits()).append(")");

            if (mt.getMeasurementValue() > mt.getMeasurementMaxValue()) {
                sb.append("    ** HIGH! **");
            }
            if (mt.getMeasurementValue() < mt.getMeasurementMinValue()) {
                sb.append("    ** LOW! **");
            }

            texts.add(sb.toString());
        }
        return texts;
    }

}
