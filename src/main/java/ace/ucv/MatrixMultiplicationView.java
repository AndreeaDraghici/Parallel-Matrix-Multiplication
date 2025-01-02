package ace.ucv;

import ace.ucv.model.Matrix;
import ace.ucv.parallel.ParallelMatrixMultiplication;
import ace.ucv.strassen.StrassenMatrixMultiplication;
import ace.ucv.sequential.SequentialMatrixMultiplication;
import ace.ucv.service.generator.RandomMatrixGenerator;
import ace.ucv.service.output.MatrixPrinter;
import ace.ucv.service.output.PerformanceMetricsRecorder;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Files;
import java.util.Random;

public class MatrixMultiplicationView {

    @FXML
    private RadioButton classicSequentialButton;
    @FXML
    private RadioButton parallelButton;
    @FXML
    private RadioButton strassenButton;
    @FXML
    private TextField rowsMinField;
    @FXML
    private TextField rowsMaxField;
    @FXML
    private TextField colsMinField;
    @FXML
    private TextField colsMaxField;
    @FXML
    private TextArea outputArea;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab outputTab;
    @FXML
    private Tab saveTab;

    private final MatrixPrinter printer = new MatrixPrinter();
    private final PerformanceMetricsRecorder metricsRecorder = new PerformanceMetricsRecorder("performance_metrics.xlsx");

    @FXML
    public void initialize() {
        ToggleGroup methodGroup = new ToggleGroup();
        classicSequentialButton.setToggleGroup(methodGroup);
        parallelButton.setToggleGroup(methodGroup);
        strassenButton.setToggleGroup(methodGroup);

        classicSequentialButton.setSelected(true); // Default to Sequential Method
    }

    @FXML
    private void startMultiplication() {
        try {
            // Get input values
            int rowsMin = Integer.parseInt(rowsMinField.getText());
            int rowsMax = Integer.parseInt(rowsMaxField.getText());
            int colsMin = Integer.parseInt(colsMinField.getText());
            int colsMax = Integer.parseInt(colsMaxField.getText());

            String selectedMethod = getSelectedMethod();

            // Validate method selection
            if (selectedMethod == null) {
                outputArea.setText("No method selected. Please choose a method.");
                return;
            }

            // Create a Task for computation
            Task<String> multiplicationTask = new Task<String>() {
                @Override
                protected String call() {
                    long startTime = System.nanoTime();
                    String result = performMultiplication(rowsMin, rowsMax, colsMin, colsMax, selectedMethod);
                    long endTime = System.nanoTime();

                    double executionTime = (endTime - startTime) / 1_000_000_000.0;
                    metricsRecorder.recordMetric("Timings", selectedMethod + " Execution Time", executionTime);
                    result += "\nExecution Time: " + executionTime + " seconds";
                    return result;
                }
            };

            // Set Task event handlers
            multiplicationTask.setOnSucceeded(event -> outputArea.setText(multiplicationTask.getValue()));
            multiplicationTask.setOnFailed(event -> outputArea.setText("An error occurred: " + multiplicationTask.getException().getMessage()));

            // Run the Task on a new Thread
            new Thread(multiplicationTask).start();
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid input. Please enter valid integers for rows and columns.");
        }
    }

    private String getSelectedMethod() {
        if (classicSequentialButton.isSelected()) return "Sequential";
        if (parallelButton.isSelected()) return "Parallel";
        if (strassenButton.isSelected()) return "Strassen";
        return null;
    }

    private String performMultiplication(int rowsMin, int rowsMax, int colsMin, int colsMax, String method) {
        // Generate random dimensions for matrices
        Random random = new Random();
        int rowsA = random.nextInt(rowsMax - rowsMin + 1) + rowsMin;
        int colsA = random.nextInt(colsMax - colsMin + 1) + colsMin;
        int rowsB = colsA;  // Ensuring matrix multiplication is valid
        int colsB = random.nextInt(colsMax - colsMin + 1) + colsMin;

        RandomMatrixGenerator generator = new RandomMatrixGenerator();
        Matrix matrixA = generator.generateRandomMatrix(rowsA, colsA);
        Matrix matrixB = generator.generateRandomMatrix(rowsB, colsB);

        Matrix result;
        switch (method) {
            case "Sequential":
                SequentialMatrixMultiplication sequentialMultiplication = new SequentialMatrixMultiplication();
                result = sequentialMultiplication.multiply(matrixA, matrixB);
                break;

            case "Parallel":
                ParallelMatrixMultiplication parallelMultiplication = new ParallelMatrixMultiplication();
                try {
                    result = parallelMultiplication.multiply(matrixA, matrixB);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Parallel multiplication interrupted.", e);
                }
                break;

            case "Strassen":
                StrassenMatrixMultiplication strassenMultiplication = new StrassenMatrixMultiplication();
                result = strassenMultiplication.multiply(matrixA, matrixB);
                break;

            default:
                throw new IllegalArgumentException("Unknown method: " + method);
        }

        return formatOutput(matrixA, matrixB, result);
    }

    private String formatOutput(Matrix matrixA, Matrix matrixB, Matrix result) {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrix A:\n").append(matrixToString(matrixA)).append("\n");
        sb.append("Matrix B:\n").append(matrixToString(matrixB)).append("\n");
        sb.append("Result:\n").append(matrixToString(result));
        return sb.toString();
    }

    private String matrixToString(Matrix matrix) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix.getData()) {
            for (int value : row) {
                sb.append(value).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @FXML
    private void saveOutputToFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Output");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                Files.write(file.toPath(), outputArea.getText().getBytes());
                outputArea.appendText("\nOutput saved successfully to " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            outputArea.appendText("\nError saving output: " + e.getMessage());
        }
    }


    @FXML
    private void savePerformanceMetrics() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Performance Metrics");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                metricsRecorder.saveToFile(file.getAbsolutePath()); // Pass the selected file path
                outputArea.setText("Performance metrics saved successfully to " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            outputArea.setText("Failed to save performance metrics: " + e.getMessage());
        }
    }


    @FXML
    private void exitApplication() {
        // Confirm before exiting the application
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Any unsaved progress will be lost.");

        // Show confirmation dialog
        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            // Close the application
            System.exit(0);
        }
    }

}
