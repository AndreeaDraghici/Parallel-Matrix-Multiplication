package ace.ucv;

import ace.ucv.sequential.RunSequentialApproach;
import ace.ucv.parallel.RunParallelApproach;
import ace.ucv.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Main class that generates and saves random matrices to text files.
 * <p>
 * This class creates a file containing matrix A, matrix B, their multiplication results, and the execution times.
 * </p>
 * Created by Andreea Draghici on 10/19/2024
 * Project name: ParallelMatrixMultiplication
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * Application entry point.
     * <p>
     * This method runs both the sequential and parallel approaches for matrix multiplication.
     * Execution times and results are saved for each approach.
     * </p>
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Run the sequential approach
        RunSequentialApproach sequentialApproach = new RunSequentialApproach();
        try {
            logger.info("Starting sequential matrix multiplication...");
            sequentialApproach.runSetup();
            logger.info("Sequential matrix multiplication completed.");
        } catch (IOException e) {
            logger.error("Failed to complete sequential matrix operations due to an IO exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred during sequential approach: " + e.getMessage());
        }

        // Run the parallel approach
        RunParallelApproach parallelApproach = new RunParallelApproach();
        try {
            logger.info("Starting parallel matrix multiplication...");
            parallelApproach.runSetup();
            logger.info("Parallel matrix multiplication completed.");
        } catch (IOException | InterruptedException e) {
            logger.error(new StringBuilder().append("Failed to complete parallel matrix operations due to an exception: ").append(e.getMessage()).toString());
        } catch (Exception e) {
            logger.error(new StringBuilder().append("An unexpected error occurred during parallel approach: ").append(e.getMessage()).toString());
        }
    }
}
