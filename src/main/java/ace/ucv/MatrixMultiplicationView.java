package ace.ucv;

import ace.ucv.service.generator.RandomMatrixGenerator;
import ace.ucv.model.Matrix;
import ace.ucv.sequential.MatrixMultiplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Random;

public class MatrixMultiplicationView {

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
    private RadioButton sequentialButton;
    @FXML
    private RadioButton parallelButton;

    @FXML
    public void initialize() {
        ToggleGroup methodGroup = new ToggleGroup();
        sequentialButton.setToggleGroup(methodGroup);
        parallelButton.setToggleGroup(methodGroup);
        sequentialButton.setSelected(true); // Implicit, metoda secvențială
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
            boolean isSequential = sequentialButton.isSelected();
            String method = isSequential ? "Sequential" : "Parallel";

            // Rulare logică de multiplicare
            String result = multiplyMatrices(rowsMin, rowsMax, colsMin, colsMax, isSequential);

            // Afișare rezultat în outputArea
            outputArea.setText("Method: " + method + "\n" + result);

        } catch (NumberFormatException e) {
            outputArea.setText("Please enter valid integer values for rows and columns.");
        }
    }

    private String multiplyMatrices(int rowsMin, int rowsMax, int colsMin, int colsMax, boolean isSequential) {
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
        if (isSequential) {
            MatrixMultiplication multiplication = new MatrixMultiplication();
            result = multiplication.multiply(matrixA, matrixB);
        } else {
            // Logica paralelă ar trebui să fie aici (momentan ne concentrăm pe cea secvențială ca exemplu)
            MatrixMultiplication multiplication = new MatrixMultiplication(); // Sau o clasă paralelă similară
            result = multiplication.multiply(matrixA, matrixB); // Aceasta ar trebui să fie o metodă paralelă
        }

        // Construim rezultatul ca text
        StringBuilder sb = new StringBuilder();
        sb.append("Matrix A (").append(rowsA).append("x").append(colsA).append("):\n");
        sb.append(matrixToString(matrixA)).append("\n");

        sb.append("Matrix B (").append(rowsB).append("x").append(colsB).append("):\n");
        sb.append(matrixToString(matrixB)).append("\n");

        sb.append("Result (").append(rowsA).append("x").append(colsB).append("):\n");
        sb.append(matrixToString(result));

        return sb.toString();
    }

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
