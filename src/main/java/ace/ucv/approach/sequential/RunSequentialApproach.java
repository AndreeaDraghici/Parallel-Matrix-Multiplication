package ace.ucv.approach.sequential;

import ace.ucv.model.Matrix;
import ace.ucv.service.output.MatrixPrinter;
import ace.ucv.service.output.PerformanceMetricsRecorder;
import ace.ucv.utils.Constants;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ace.ucv.utils.Constants.SEQUENTIAL_XLSX;

/**
 * Created by Andreea Draghici on 10/28/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class RunSequentialApproach {

    private final SequentialMatrixMultiplication sequentialMultiplication = new SequentialMatrixMultiplication();
    private final MatrixPrinter printer = new MatrixPrinter();
    private final PerformanceMetricsRecorder metricsRecorder;

    /**
     * Constructor that initializes the metrics recorder with a path to save the Excel file.
     */
    public RunSequentialApproach() {
        // Initialize the metrics recorder with a path to save the Excel file
        this.metricsRecorder = new PerformanceMetricsRecorder(SEQUENTIAL_XLSX);
    }

    /**
     * Run the setup for the sequential approach.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void runSetup(Matrix matrixA, Matrix matrixB) throws IOException {
        // Start metrics for multiplication
        long startTime = System.nanoTime();
        Matrix result = sequentialMultiplication.multiply(matrixA, matrixB);
        long endTime = System.nanoTime();
        metricsRecorder.recordMetric("Timings", "Classic Multiplication Time", (endTime - startTime) / 1_000_000_000.0);

        // Write results to distinct file
        Path filePath = Paths.get(Constants.OUTPUT_SEQUENTIAL);
        printer.writeMatrixToFile("Matrix A:", matrixA, filePath);
        printer.writeMatrixToFile("Matrix B:", matrixB, filePath);
        printer.writeMatrixToFile("Classic Multiplication Result:", result, filePath);

        // Save metrics
        metricsRecorder.saveToFile();
    }

}
