package se.lu.ics;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import se.lu.ics.controllers.MainViewController;
import se.lu.ics.models.PersonRegister;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create an instance of the CustomerRegister class
        PersonRegister personRegister = new PersonRegister();
        // Get the location of the MainView.fxml file
        // The location is relative to the root of the classpath (target/classes)
        URL location = getClass().getResource("/fxml/MainView.fxml");
        // Create a new FXMLLoader with the location of the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        // Load the FXML file to create the UI layout as defined in MainView.fxml
        AnchorPane root = fxmlLoader.load();
        // Get the controller associated with the FXML file
        // that has been automatically instantiated by the FXMLLoader
        MainViewController controller = fxmlLoader.getController();
        // Set the personregister on the controller
        controller.setPersonRegister(personRegister);
        // Create a scene with the root node and set it on the primary stage
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("gigaLogoIcon.png"));
        primaryStage.setScene(scene);
        // Set the title of the primary stage (window title) and display the stage
        primaryStage.setTitle("GIGABANK™");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}