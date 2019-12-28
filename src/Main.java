import model.Patient;
import model.database.DBManager;
import model.database.PatientGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/PatientListView.fxml"));
        primaryStage.setTitle("Patient List");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {

        /*
        PatientGenerator patientGenerator = new PatientGenerator();
        DBManager dbManager = new DBManager();

        for (int i = 0; i < 1500; i++) {
            String[] patientData = patientGenerator.createRandomPatientData();
            dbManager.addPatientToDB(patientData);
        }
         */

        launch(args);
    }
}
