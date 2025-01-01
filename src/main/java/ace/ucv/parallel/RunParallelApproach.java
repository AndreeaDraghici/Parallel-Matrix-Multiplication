package ace.ucv.parallel;

import ace.ucv.model.Matrix;
import ace.ucv.service.MatrixService;
import ace.ucv.service.output.MatrixPrinter;
import ace.ucv.service.output.PerformanceMetricsRecorder;
import ace.ucv.service.parser.DimensionManager;
import ace.ucv.utils.Constants;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static ace.ucv.utils.Constants.PARALLEL_XLSX;

/**
 * Created by Andreea Draghici on 12/25/2024
 * Name of project: ParallelMatrixMultiplication
 * Class that runs the parallel approach for matrix multiplication.
 */
public class RunParallelApproach {

    private final DimensionManager dimensionManager = new DimensionManager();
    private final MatrixService matrixService = new MatrixService();
    private final MatrixPrinter printer = new MatrixPrinter();
    private final PerformanceMetricsRecorder metricsRecorder;

    /**
     * Constructor that initializes the metrics recorder with a path to save the Excel file.
     */
    public RunParallelApproach() {
        // Initialize the metrics recorder with a path to save the Excel file
        this.metricsRecorder = new PerformanceMetricsRecorder(PARALLEL_XLSX);
    }

    /***
     * Run the setup for the parallel approach .
     * @throws IOException If an I/O error occurs.
     * @throws InterruptedException If the thread execution is interrupted.
     */
    public void runSetup() throws IOException, InterruptedException {
        Map<String, Integer> dimensions = dimensionManager.getDimensions();

        long startTime = System.nanoTime();
        Matrix[] matrices = matrixService.generateMatrices(
                dimensions.get("rowsMin"), dimensions.get("rowsMax"),
                dimensions.get("colsMin"), dimensions.get("colsMax")
        );
        long endTime = System.nanoTime();
        metricsRecorder.recordMetric("Timings", "Matrix Generation Time", (endTime - startTime) / 1_000_000_000.0);

        // Parallel multiplication
        ParallelMatrixMultiplication parallelMultiplier = new ParallelMatrixMultiplication();

        startTime = System.nanoTime();
        Matrix parallelResult = parallelMultiplier.multiply(matrices[0], matrices[1]);
        endTime = System.nanoTime();
        metricsRecorder.recordMetric("Timings", "Parallel Multiplication Time", (endTime - startTime) / 1_000_000_000.0);

        // Write results to file
        final Path filePath = Paths.get(Constants.OUTPUT_PARALLEL);
        printer.writeMatrixToFile("Matrix A:", matrices[0], filePath);
        printer.writeMatrixToFile("Matrix B:", matrices[1], filePath);
        printer.writeMatrixToFile("Parallel Multiplication Result:", parallelResult, filePath);

        // Save all recorded metrics to the Excel file
        metricsRecorder.saveToFile();
    }
}
