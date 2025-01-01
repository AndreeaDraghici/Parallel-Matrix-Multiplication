package ace.ucv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MatrixMultiplicationApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file for the UI
            Parent root = FXMLLoader.load(getClass().getResource("/main_view.fxml"));


            // Set the title and scene
            primaryStage.setTitle("Matrix Multiplication App");
            primaryStage.setScene(new Scene(root, 400, 500));
            primaryStage.show();
        } catch (Exception e) {
            // Log the exception if the FXML file could not be loaded
            e.printStackTrace();
            System.err.println("Error loading the FXML file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
