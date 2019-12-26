import model.database.LaboratoryGenerator;
import model.database.PatientGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/PatientListView.fxml"));
        primaryStage.setTitle("Patient List");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {

        PatientGenerator patientGenerator = new PatientGenerator();
        System.out.println(patientGenerator.getRandomPatient());
        System.out.println(patientGenerator.getRandomPatient());
        System.out.println(patientGenerator.getRandomPatient());

        LaboratoryGenerator laboratoryGenerator = new LaboratoryGenerator();
        System.out.println(laboratoryGenerator.getRandomLaboratory());
        System.out.println(laboratoryGenerator.getRandomLaboratory());
        System.out.println(laboratoryGenerator.getRandomLaboratory());

        launch(args);
    }
}
