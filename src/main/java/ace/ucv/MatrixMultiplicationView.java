package ace.ucv;

import ace.ucv.generator.RandomMatrixGenerator;
import ace.ucv.model.Matrix;
import ace.ucv.sequential.MatrixMultiplication;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class MatrixMultiplicationView extends Application {

    private TextField rowsMinField = new TextField();
    private TextField rowsMaxField = new TextField();
    private TextField colsMinField = new TextField();
    private TextField colsMaxField = new TextField();
    private TextArea outputArea = new TextArea();
    private RadioButton sequentialButton = new RadioButton("Sequential");
    private RadioButton parallelButton = new RadioButton("Parallel");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Matrix Multiplication App");

        // Layout pentru inputuri
        GridPane inputGrid = createInputGrid();

        // Radio buttons pentru selectarea metodei
        ToggleGroup methodGroup = new ToggleGroup();
        sequentialButton.setToggleGroup(methodGroup);
        parallelButton.setToggleGroup(methodGroup);
        sequentialButton.setSelected(true); // Implicit, metoda secvențială

        // Buton de start pentru începerea calculului
        Button startButton = new Button("Start Multiplication");
        startButton.setOnAction(event -> startMultiplication());

        // Output TextArea
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(500);

        // Organizarea elementelor într-un VBox
        VBox root = new VBox(10, inputGrid, sequentialButton, parallelButton, startButton, outputArea);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createInputGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        // Label și TextField pentru dimensiuni minime/maxime
        grid.add(new Label("Rows Min:"), 0, 0);
        grid.add(rowsMinField, 1, 0);
        grid.add(new Label("Rows Max:"), 0, 1);
        grid.add(rowsMaxField, 1, 1);
        grid.add(new Label("Cols Min:"), 0, 2);
        grid.add(colsMinField, 1, 2);
        grid.add(new Label("Cols Max:"), 0, 3);
        grid.add(colsMaxField, 1, 3);

        return grid;
    }

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

    public static void main(String[] args) {
        launch(args);
    }
}
