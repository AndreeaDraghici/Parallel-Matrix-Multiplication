package ace.ucv.sequential;

import ace.ucv.service.MatrixService;
import ace.ucv.service.generator.RandomMatrixGenerator;
import ace.ucv.model.Matrix;
import ace.ucv.service.metrics.MetricsRecorder;
import ace.ucv.service.output.PerformanceMetricsRecorder;
import ace.ucv.service.parser.DimensionManager;
import ace.ucv.utils.Constants;
import ace.ucv.service.parser.MatrixParser;
import ace.ucv.service.output.MatrixPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Random;

import static ace.ucv.utils.Constants.DIMENSIONS_FILE;

/**
 * Created by Andreea Draghici on 10/28/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class RunSequentialApproach {

    private DimensionManager dimensionManager = new DimensionManager();
    private MatrixService matrixService = new MatrixService();
    private MatrixPrinter printer = new MatrixPrinter();
    private PerformanceMetricsRecorder metricsRecorder;

    public RunSequentialApproach() {
        // Initialize the metrics recorder with a path to save the Excel file
        this.metricsRecorder = new PerformanceMetricsRecorder("performance_metrics.xlsx");
    }

    public void runSetup() throws IOException {
        Map<String, Integer> dimensions = dimensionManager.getDimensions();

        long startTime = System.nanoTime();
        Matrix[] matrices = matrixService.generateMatrices(
                dimensions.get("rowsMin"), dimensions.get("rowsMax"),
                dimensions.get("colsMin"), dimensions.get("colsMax")
        );
        long endTime = System.nanoTime();
        metricsRecorder.recordMetric("Timings", "Matrix Generation Time", (endTime - startTime) / 1_000_000_000.0);

        // Classic multiplication
        startTime = System.nanoTime();
        Matrix result = matrixService.multiplyMatrices(matrices[0], matrices[1], false);
        endTime = System.nanoTime();
        metricsRecorder.recordMetric("Timings", "Classic Multiplication Time", (endTime - startTime) / 1_000_000_000.0);

        // Strassen multiplication
        startTime = System.nanoTime();
        Matrix strassenResult = matrixService.multiplyMatrices(matrices[0], matrices[1], true);
        endTime = System.nanoTime();
        metricsRecorder.recordMetric("Timings", "Strassen Multiplication Time", (endTime - startTime) / 1_000_000_000.0);

        // Write results to file
        printer.writeMatrixToFile("Matrix A:", matrices[0], Paths.get(Constants.OUTPUT));
        printer.writeMatrixToFile("Matrix B:", matrices[1], Paths.get(Constants.OUTPUT));
        printer.writeMatrixToFile("Classic Multiplication Result:", result, Paths.get(Constants.OUTPUT));
        printer.writeMatrixToFile("Strassen Multiplication Result:", strassenResult, Paths.get(Constants.OUTPUT));

        // Save all recorded metrics to the Excel file
        metricsRecorder.saveToFile();
    }
}
