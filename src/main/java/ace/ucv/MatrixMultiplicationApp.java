package ace.ucv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MatrixMultiplicationApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ace/ucv/MatrixMultiplicationView.fxml")));
            primaryStage.setTitle("Matrix Multiplication App");
            primaryStage.setScene(new Scene(root, 400, 500));
            primaryStage.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
