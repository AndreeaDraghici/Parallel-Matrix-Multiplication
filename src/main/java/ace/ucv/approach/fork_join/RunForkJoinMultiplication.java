package ace.ucv.approach.fork_join;

import ace.ucv.model.Matrix;
import ace.ucv.service.output.MatrixPrinter;
import ace.ucv.service.output.PerformanceMetricsRecorder;
import ace.ucv.utils.Constants;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Created by Andreea Draghici on 1/30/2025
 * Name of project: ParallelMatrixMultiplication
 */
public class RunForkJoinMultiplication {

    private final ForkJoinStreamMatrixMultiplication forkJoinMultiplication = new ForkJoinStreamMatrixMultiplication();
    private final MatrixPrinter printer = new MatrixPrinter();
    private final PerformanceMetricsRecorder metricsRecorder;

    /**
     * Constructor that initializes the metrics recorder with a path to save the Excel file.
     */
    public RunForkJoinMultiplication() {
        // Initialize the metrics recorder with a path to save the Excel file
        this.metricsRecorder = new PerformanceMetricsRecorder(Constants.FORK_JOIN_XLSX);
    }

    /**
     * Run the setup for the fork-join approach. Perform fork-join multiplication and write results to file.
     * @param matrixA - first matrix to multiply
     * @param matrixB - second matrix to multiply
     * @throws IOException If an I/O error occurs.
     */
    public void runSetup(Matrix matrixA, Matrix matrixB)  throws IOException {
        long startTime = System.nanoTime();

        // Perform fork-join multiplication
        Matrix result;

        result = forkJoinMultiplication.multiply(matrixA, matrixB);
        long endTime = System.nanoTime();
        metricsRecorder.recordMetric("Timings", "Fork-Join Multiplication Time", (endTime - startTime) / 1_000_000_000.0);

        // Write results to file
        final Path filePath = Paths.get(Constants.OUTPUT_FORK_JOIN);
        printer.writeMatrixToFile("Matrix A:", matrixA, filePath);
        printer.writeMatrixToFile("Matrix B:", matrixB, filePath);
        printer.writeMatrixToFile("Fork-Join Multiplication Result:", result, filePath);

        metricsRecorder.saveToFile();

    }
}
