package ace.ucv;

import ace.ucv.sequential.RunSequentialApproach;
import ace.ucv.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Main class that generates and saves random matrices to text files.
 * <p>
 * This class creates a file containing matrix A, matrix B, their multiplication result, and the execution times.
 * </p>
 * Created by Andreea Draghici on 10/19/2024
 * Project name: ParallelMatrixMultiplication
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * Application entry point.
     * <p>
     * This method generates two random matrices, multiplies them, and saves the results to a file.
     * Execution time for generating matrices, multiplying them, and writing to the file is measured and written to the file.
     * </p>
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        RunSequentialApproach approach = new RunSequentialApproach();
        try {
            approach.runSetup();
        } catch (IOException e) {
            logger.error("Failed to complete matrix operations due to an IO exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: " + e.getMessage());
        }
    }

}
