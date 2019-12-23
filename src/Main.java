import Model.Database.PatientRandomizer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
/*
        File input = new File("resources/swedish_postal_codes.txt");
        File output = new File("resources/postalCodesAndCities.txt");

        try {
            Scanner sc = new Scanner(input);
            PrintWriter pw = new PrintWriter(output);
            String postalCode1;
            String postalCode2;
            String city;

            do {
                sc.next();
                postalCode1 = sc.next();
                pw.print(postalCode1);
                postalCode2 = sc.next();
                pw.print(postalCode2);
                pw.print("*");
                city = sc.next();
                pw.println(city);
                sc.nextLine();
            } while (sc.hasNextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
 */
        PatientRandomizer patientRandomizer = new PatientRandomizer();
        System.out.println(patientRandomizer.getRandomPatient());
        System.out.println(patientRandomizer.getRandomPatient());
        System.out.println(patientRandomizer.getRandomPatient());

        launch(args);
    }
}
