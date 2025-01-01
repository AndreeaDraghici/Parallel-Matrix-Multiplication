package ace.ucv.parallel;

import ace.ucv.model.Matrix;
import ace.ucv.service.output.MatrixPrinter;
import ace.ucv.service.output.PerformanceMetricsRecorder;
import ace.ucv.utils.Constants;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ace.ucv.utils.Constants.PARALLEL_XLSX;

/**
 * Created by Andreea Draghici on 10/28/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class RunParallelApproach {

    private final MatrixPrinter printer = new MatrixPrinter();
    private final PerformanceMetricsRecorder metricsRecorder;
    private final   ParallelMatrixMultiplication parallelMultiplication = new ParallelMatrixMultiplication();

    /**
     * Constructor that initializes the metrics recorder with a path to save the Excel file.
     */
    public RunParallelApproach() {
        // Initialize the metrics recorder with a path to save the Excel file
        this.metricsRecorder = new PerformanceMetricsRecorder(PARALLEL_XLSX);
    }

    /**
     * Run the setup for the parallel approach.
     *
     * @param matrixA The first matrix
     * @param matrixB The second matrix
     * @throws IOException If an I/O error occurs.
     */
    public void runSetup(Matrix matrixA, Matrix matrixB) throws IOException {
        long startTime = System.nanoTime();

        // Perform parallel multiplication
        Matrix result;
        try {
            result = parallelMultiplication.multiply(matrixA, matrixB);
        } catch (InterruptedException e) {
            throw new RuntimeException("Parallel matrix multiplication was interrupted", e);
        }
        long endTime = System.nanoTime();
        metricsRecorder.recordMetric("Timings", "Parallel Multiplication Time", (endTime - startTime) / 1_000_000_000.0);

        // Write results to file
        final Path filePath = Paths.get(Constants.OUTPUT_PARALLEL);
        printer.writeMatrixToFile("Matrix A:", matrixA, filePath);
        printer.writeMatrixToFile("Matrix B:", matrixB, filePath);
        printer.writeMatrixToFile("Parallel Multiplication Result:", result, filePath);

        // Save all recorded metrics to the Excel file
        metricsRecorder.saveToFile();
    }
}
