package view;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.MeasurementTaken;
import model.Patient;

import java.io.IOException;

/**
 * Utility class with methods to change Scenes and add/set views to Panes, with the
 * possibility of passing in Patient or Measurement objects.
 *
 * Methods cannot be static due to the use of the static FXMLLoader.load() method,
 * so the class needs to be instantiated.
 */
public class SceneChanger {

    /**
     * changes the current scene following an ActionEvent without passing
     * data between the scenes.
     * @param event         The Event that triggers the Scene change
     * @param viewName      Relative path to the view's fxml file
     * @param sceneTitle    Title of the new Scene
     * @throws IOException
     */
    public void changeScene(Event event, String viewName, String sceneTitle) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(sceneTitle);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * changes the current scene following an Event, passing in a Patient object
     * to the destination Scene controller (which implements PatientController interface)
     * @param event         The Event that triggers the Scene change
     * @param viewName      Relative path to the view's fxml file
     * @param sceneTitle    Title of the new Scene
     * @param patient       The passed-in Patient object
     * @throws IOException
     */
    public void changeScene(Event event, String viewName, String sceneTitle, Patient patient) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent viewParent = loader.load();
        PatientController controller = loader.getController();
        controller.receivePatient(patient);

        Scene scene = new Scene(viewParent);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(sceneTitle);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * sets a subScene as the Children of a Pane in the current Scene, without
     * needing to pass any data.
     * @param rootPane      Pane to which the fxml view will be added
     * @param viewName      Relative path to the view's fxml file
     * @throws IOException
     */
    public void setView(Pane rootPane, String viewName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent viewParent = loader.load();
        rootPane.getChildren().setAll(viewParent);
    }

    /**
     * sets a subScene as the Children of a Pane in the current Scene, passing
     * a Patient object into that subscene
     * @param rootPane      Pane to which the fxml view will be added
     * @param viewName      Relative path to the view's fxml file
     * @param patient       The Patient object to be passed to the view's controller
     * @throws IOException
     */
    public void setView(Pane rootPane, String viewName, Patient patient) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent viewParent = loader.load();
        PatientController controller = loader.getController();
        controller.receivePatient(patient);
        rootPane.getChildren().setAll(viewParent);
    }

    /**
     * sets a subScene as the Children of a Pane in the current Scene, passing
     * a MeasurementTaken object into that subscene
     * @param rootPane      Pane to which the fxml view will be added
     * @param viewName      Relative path to the view's fxml file
     * @param measurementTaken       The MeasurementTaken object to be passed to the view's controller
     * @throws IOException
     */
    public void setView(Pane rootPane, String viewName, MeasurementTaken measurementTaken) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent viewParent = loader.load();
        MeasurementController controller = loader.getController();
        controller.receiveMeasurement(measurementTaken);
        rootPane.getChildren().setAll(viewParent);
    }

}
