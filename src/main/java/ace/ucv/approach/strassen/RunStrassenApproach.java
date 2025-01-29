package ace.ucv.approach.strassen;

import ace.ucv.model.Matrix;
import ace.ucv.service.output.MatrixPrinter;
import ace.ucv.service.output.PerformanceMetricsRecorder;
import ace.ucv.utils.Constants;
import ace.ucv.utils.MatrixUtility;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ace.ucv.utils.Constants.STRASSEN_XLSX;
import static ace.ucv.utils.MatrixUtility.padMatrix;

/**
 * Created by Andreea Draghici on 10/28/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class RunStrassenApproach {

    private final MatrixPrinter printer = new MatrixPrinter();
    private final PerformanceMetricsRecorder metricsRecorder;
    private final StrassenMatrixMultiplication strassenMultiplication = new StrassenMatrixMultiplication();

    /**
     * Constructor that initializes the metrics recorder with a path to save the Excel file.
     */
    public RunStrassenApproach() {
        // Initialize the metrics recorder with a path to save the Excel file
        this.metricsRecorder = new PerformanceMetricsRecorder(STRASSEN_XLSX);
    }

    /**
     * Run the setup for the Strassen approach.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void runSetup(Matrix matrixA, Matrix matrixB) throws IOException {
        // Validate dimensions
        if (matrixA.getCols() != matrixB.getRows()) {
            throw new IllegalArgumentException("The number of columns in the first matrix must be equal to the number of rows in the second.");
        }

        // Save original dimensions
        int originalRowsA = matrixA.getRows();
        int originalColsB = matrixB.getCols();

        // Pad matrices for Strassen multiplication
        Matrix paddedA = padMatrix(matrixA);
        Matrix paddedB = padMatrix(matrixB);

        long startTime = System.nanoTime();

        // Perform Strassen multiplication
        Matrix paddedResult = strassenMultiplication.multiply(paddedA, paddedB);
        long endTime = System.nanoTime();
        metricsRecorder.recordMetric("Timings", "Strassen Multiplication Time", (endTime - startTime) / 1_000_000_000.0);

        // Crop the result to original dimensions
        Matrix result = MatrixUtility.cropMatrix(paddedResult, originalRowsA, originalColsB);

        // Write results to file
        final Path filePath = Paths.get(Constants.OUTPUT_STRASSEN);
        printer.writeMatrixToFile("Matrix A:", matrixA, filePath);
        printer.writeMatrixToFile("Matrix B:", matrixB, filePath);
        printer.writeMatrixToFile("Strassen Multiplication Result:", result, filePath);

        // Save all recorded metrics to the Excel file
        metricsRecorder.saveToFile();
    }
}
