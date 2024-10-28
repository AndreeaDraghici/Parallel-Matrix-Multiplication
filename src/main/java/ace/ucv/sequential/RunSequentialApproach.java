package ace.ucv.sequential;

import ace.ucv.generator.RandomMatrixGenerator;
import ace.ucv.model.Matrix;
import ace.ucv.utils.Constants;
import ace.ucv.utils.MatrixParser;
import ace.ucv.utils.MatrixPrinter;
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

    private static final Logger logger = LogManager.getLogger(RunSequentialApproach.class);

    /**
     * Main method for reading dimensions, generating matrices, and multiplying them.
     * <p>
     * This method handles each part of the setup process: reading dimensions from the file,
     * generating two random matrices, multiplying them, and then writing all matrices and timing information to an output file.
     * </p>
     *
     * @throws IOException If there is an issue reading the file
     */
    public  void runSetup() throws IOException {
        MatrixParser parser = new MatrixParser();

        // Attempt to read dimensions from the specified file
        Map<String, Integer> dimensions;
        try {
            dimensions = parser.readMatrixDimensionsFromFile(DIMENSIONS_FILE);
            logger.info("Dimensions read successfully from file.");
        } catch (IOException e) {
            logger.error(String.format("Error: Could not read dimensions from file %s", DIMENSIONS_FILE));
            throw e;
        }

        // Extract minimum and maximum values for rows and columns
        int rowsMin = dimensions.get("rowsMin");
        int rowsMax = dimensions.get("rowsMax");
        int colsMin = dimensions.get("colsMin");
        int colsMax = dimensions.get("colsMax");

        Random rand = new Random();

        // Measure time for matrix generation
        long startTime = System.nanoTime();

        // Generate random dimensions within specified ranges
        int rowsA = rand.nextInt(rowsMax - rowsMin + 1) + rowsMin;
        int colsA = rand.nextInt(colsMax - colsMin + 1) + colsMin;
        int rowsB = colsA;
        int colsB = rand.nextInt(colsMax - colsMin + 1) + colsMin;

        // Create a random matrix generator
        RandomMatrixGenerator randomMatrixGenerator = new RandomMatrixGenerator();

        // Generate two random matrices
        logger.info("Generating random matrices...");
        Matrix matrixA = randomMatrixGenerator.generateRandomMatrix(rowsA, colsA);
        Matrix matrixB = randomMatrixGenerator.generateRandomMatrix(rowsB, colsB);

        long endTime = System.nanoTime();
        long generationTime = endTime - startTime;
        logger.info("Random matrices generated successfully.");

        // Measure time for matrix multiplication
        startTime = System.nanoTime();
        MatrixMultiplication multiplication = new MatrixMultiplication();
        Matrix result;

        try {
            result = multiplication.multiply(matrixA, matrixB);
            logger.info("Matrix multiplication completed successfully.");
        } catch (IllegalArgumentException e) {
            logger.error(String.format("Error during matrix multiplication: %s", e.getMessage()));
            return; // Exit if multiplication is not possible
        }

        endTime = System.nanoTime();
        long multiplicationTime = endTime - startTime;

        // Measure time for writing matrices to the file
        MatrixPrinter printer = new MatrixPrinter();
        startTime = System.nanoTime();

        logger.info("Writing Matrix A to file...");
        printer.writeMatrixToFile("Matrix A:", matrixA, Paths.get(Constants.OUTPUT));
        logger.info("Writing Matrix B to file...");
        printer.writeMatrixToFile("Matrix B:", matrixB, Paths.get(Constants.OUTPUT));
        logger.info("Writing multiplication result to file...");
        printer.writeMatrixToFile("Matrix Multiplication Result:", result, Paths.get(Constants.OUTPUT));

        endTime = System.nanoTime();
        long writeTime = endTime - startTime;
        logger.info("All matrices written to file successfully.");

        // Write execution times to file
        StringBuilder timeInfo = new StringBuilder();
        timeInfo.append(String.format("Matrix generation time: %.6f seconds\n", generationTime / 1_000_000_000.0));
        timeInfo.append(String.format("Matrix multiplication time: %.6f seconds\n", multiplicationTime / 1_000_000_000.0));
        timeInfo.append(String.format("File write time: %.6f seconds\n", writeTime / 1_000_000_000.0));

        try {
            Files.write(Paths.get(Constants.OUTPUT), timeInfo.toString().getBytes(), StandardOpenOption.APPEND);
            logger.info("Execution times written to file successfully.");
        } catch (IOException e) {
            logger.error(String.format("Error writing execution times to file: %s", e.getMessage()));
            throw e;
        }
    }
}
