package ace.ucv.approach.stream;

import ace.ucv.model.Matrix;
import ace.ucv.service.output.MatrixPrinter;
import ace.ucv.service.output.PerformanceMetricsRecorder;
import ace.ucv.utils.Constants;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ace.ucv.utils.Constants.STREAM_PARALLEL_XLSX;

/**
 * Created by Andreea Draghici on 1/29/2025
 * Name of project: ParallelMatrixMultiplication
 */
public class RunStreamParallelApproach {

    private final StreamParallelMultiplication streamMultiplication = new StreamParallelMultiplication();
    private final MatrixPrinter printer = new MatrixPrinter();
    private final PerformanceMetricsRecorder metricsRecorder;

    /**
     * Constructor that initializes the metrics recorder with a path to save the Excel file.
     */
    public RunStreamParallelApproach() {
        // Initialize the metrics recorder with a path to save the Excel file
        this.metricsRecorder = new PerformanceMetricsRecorder(STREAM_PARALLEL_XLSX);
    }

    /**
     * Run the setup for the stream parallel approach.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void runSetup(Matrix matrixA, Matrix matrixB) throws IOException {
        // Start metrics for multiplication
        long startTime = System.nanoTime();
        Matrix result = streamMultiplication.multiply(matrixA, matrixB);
        long endTime = System.nanoTime();
        metricsRecorder.recordMetric("Timings", "Stream Parallel Multiplication Time", (endTime - startTime) / 1_000_000_000.0);

        // Write results to distinct file
        Path filePath = Paths.get(Constants.OUTPUT_STREAM_PARALLEL);
        printer.writeMatrixToFile("Matrix A:", matrixA, filePath);
        printer.writeMatrixToFile("Matrix B:", matrixB, filePath);
        printer.writeMatrixToFile("Stream Parallel Multiplication Result:", result, filePath);

        // Save metrics
        metricsRecorder.saveToFile();
    }
}
