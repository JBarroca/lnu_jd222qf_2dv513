import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.database.DBPopulator;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/HomeView.fxml"));
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {

        //uncomment the following code to populate the entire database,
        //providing the desired number of random patients and labs:
        //the current number of patients (400) and laboratories (100) generated almost 52000 unique measurements

        /*
        DBPopulator dbPopulator = new DBPopulator();
        int nummberOfRandomPatientsToCreate = 400;
        int numberOfRandomLaboratoriesToCreate = 100;
        dbPopulator.populateEntireDatabase(
                nummberOfRandomPatientsToCreate,
                numberOfRandomLaboratoriesToCreate
        );
        */

        launch(args);
    }
}
