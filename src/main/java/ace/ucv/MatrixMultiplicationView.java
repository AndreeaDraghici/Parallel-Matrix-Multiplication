package ace.ucv;

import ace.ucv.parallel.ParallelMatrixMultiplication;
import ace.ucv.sequential.StrassenMatrixMultiplication;
import ace.ucv.service.generator.RandomMatrixGenerator;
import ace.ucv.model.Matrix;
import ace.ucv.sequential.SequentialMatrixMultiplication;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Random;

public class MatrixMultiplicationView {

    @FXML
    public RadioButton strassenButton;
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
    private RadioButton classicSequentialButton;
    @FXML
    private RadioButton parallelButton;

    @FXML
    public void initialize() {
        ToggleGroup methodGroup = new ToggleGroup();
        classicSequentialButton.setToggleGroup(methodGroup);
        parallelButton.setToggleGroup(methodGroup);
        strassenButton.setToggleGroup(methodGroup);
        classicSequentialButton.setSelected(true); // Implicit, metoda secvențială
    }

    @FXML
    private void startMultiplication() {
        try {
            // Obțineți valorile introduse de utilizator
            int rowsMin = Integer.parseInt(rowsMinField.getText());
            int rowsMax = Integer.parseInt(rowsMaxField.getText());
            int colsMin = Integer.parseInt(colsMinField.getText());
            int colsMax = Integer.parseInt(colsMaxField.getText());

            // Verifică ce metodă a fost selectată
            String method;
            if (classicSequentialButton.isSelected()) {
                method = "Sequential";
            } else if (parallelButton.isSelected()) {
                method = "Parallel";
            } else if (strassenButton.isSelected()) {
                method = "Strassen";
            } else {
                throw new IllegalStateException("No method selected.");
            }

            // Creează un task pentru a evita blocarea UI
            Task<String> multiplicationTask = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    return multiplyMatrices(rowsMin, rowsMax, colsMin, colsMax, method);
                }
            };

            // Când task-ul este finalizat, actualizează UI-ul
            multiplicationTask.setOnSucceeded(event -> outputArea.setText("Method: " + method + "\n" + multiplicationTask.getValue()));

            // În cazul unei erori, afișează mesajul
            multiplicationTask.setOnFailed(event -> outputArea.setText("An error occurred: " + multiplicationTask.getException().getMessage()));

            // Rulează task-ul pe un thread separat
            new Thread(multiplicationTask).start();

        } catch (NumberFormatException e) {
            outputArea.setText("Please enter valid integer values for rows and columns.");
        } catch (Exception e) {
            outputArea.setText("An error occurred: " + e.getMessage());
        }
    }


    /**
     * Multiply two matrices using the specified method.
     *
     * @param rowsMin The minimum number of rows for the matrices
     * @param rowsMax The maximum number of rows for the matrices
     * @param colsMin The minimum number of columns for the matrices
     * @param colsMax The maximum number of columns for the matrices
     * @param method  The method to use for multiplication
     * @return The result of the multiplication as a string
     */
    private String multiplyMatrices(int rowsMin, int rowsMax, int colsMin, int colsMax, String method) {
        // Generarea dimensiunilor random pentru matrici, folosind valorile min/max
        Random rand = new Random();
        int rowsA = rand.nextInt(rowsMax - rowsMin + 1) + rowsMin;
        int colsA = rand.nextInt(colsMax - colsMin + 1) + colsMin;
        int rowsB = colsA;  // nr de randuri B = nr de coloane A pentru înmulțire
        int colsB = rand.nextInt(colsMax - colsMin + 1) + colsMin;

        // Generarea matricelor A și B
        RandomMatrixGenerator generator = new RandomMatrixGenerator();
        Matrix matrixA = generator.generateRandomMatrix(rowsA, colsA);
        Matrix matrixB = generator.generateRandomMatrix(rowsB, colsB);

        // Calcularea produsului
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
                    throw new RuntimeException(e);
                }
                break;

            case "Strassen":
                StrassenMatrixMultiplication strassenMultiplication = new StrassenMatrixMultiplication();
                result = strassenMultiplication.multiply(matrixA, matrixB);
                break;

            default:
                throw new IllegalStateException("Unknown method: " + method);
        }

        StringBuilder sb = getTextAreaOutput(rowsA, colsA, matrixA, rowsB, colsB, matrixB, result);

        return sb.toString();
    }

    /**
     * Compute the result as a text area output for the UI.
     *
     * @param rowsA   The number of rows in matrix A
     * @param colsA   The number of columns in matrix A
     * @param matrixA The first matrix
     * @param rowsB   The number of rows in matrix B
     * @param colsB   The number of columns in matrix B
     * @param matrixB The second matrix
     * @param result  The result matrix
     * @return The result as a string builder for the text area
     */
    private StringBuilder getTextAreaOutput(int rowsA, int colsA, Matrix matrixA, int rowsB, int colsB, Matrix matrixB, Matrix result) {

        StringBuilder sb = new StringBuilder();
        sb.append("Matrix A (").append(rowsA).append("x").append(colsA).append("):\n");
        sb.append(matrixToString(matrixA)).append("\n");

        sb.append("Matrix B (").append(rowsB).append("x").append(colsB).append("):\n");
        sb.append(matrixToString(matrixB)).append("\n");

        sb.append("Result (").append(rowsA).append("x").append(colsB).append("):\n");
        sb.append(matrixToString(result));
        return sb;
    }

    /**
     * Convert a matrix to a string representation.
     *
     * @param matrix The matrix to convert
     * @return The string representation of the matrix data in tabular form (rows separated by new lines)
     */
    private String matrixToString(Matrix matrix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                sb.append(matrix.getData()[i][j]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
