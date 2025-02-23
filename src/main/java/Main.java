import ace.ucv.approach.fork_join.ForkJoinStreamMatrixMultiplication;
import ace.ucv.approach.fork_join.RunForkJoinMultiplication;
import ace.ucv.approach.stream.RunStreamParallelApproach;
import ace.ucv.model.Matrix;
import ace.ucv.approach.parallel.RunParallelApproach;
import ace.ucv.approach.sequential.RunSequentialApproach;
import ace.ucv.service.MatrixService;
import ace.ucv.service.parser.DimensionManager;
import ace.ucv.approach.strassen.RunStrassenApproach;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Andreea Draghici on 1/2/2025
 * Name of project: ParallelMatrixMultiplication
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static final int NUM_THREADS = 4;

    public static void main(String[] args) {
        DimensionManager dimensionManager = new DimensionManager();
        MatrixService matrixService = new MatrixService();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Select a matrix multiplication method:");
                System.out.println("1. Sequential");
                System.out.println("2. Parallel");
                System.out.println("3. Strassen");
                System.out.println("4. Stream Parallel");
                System.out.println("5. Fork-Join");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                String choice = reader.readLine().trim();
                // Exit the program if the user chooses to do so (option 4)
                if (choice.equals("6")) {
                    System.out.println("Exiting the program.");
                    break;
                }

                try {
                    // Generate matrices based on user input and dimensions from file (DIMENSIONS_FILE)
                    final Matrix[] matrices = getMatrices(matrixService, dimensionManager);

                    /*
                     *Perform matrix multiplication based on the user's choice
                     */
                    switch (choice) {
                        case "1":
                            runSequentialApproach(matrices);
                            break;

                        case "2":
                            runParallelApproach(matrices);
                            break;

                        case "3":
                            runStrassenApproach(matrices);
                            break;

                        case "4":
                            runStreamParallelApproach(matrices);
                            break;
                        case "5":
                            runForkJoinApproach(matrices);
                            break;

                        default:
                            throw new RuntimeException("Invalid choice. Please enter a number between 1 and 4.");
                    }
                } catch (Exception e) {
                    logger.error("An error occurred during matrix multiplication: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.error("An error occurred while reading input: " + e.getMessage());
        }
    }

    private static Matrix[] getMatrices(MatrixService matrixService, DimensionManager dimensionManager) throws IOException {
        return matrixService.generateMatrices(
                dimensionManager.getDimensions().get("rowsMin"),
                dimensionManager.getDimensions().get("rowsMax"),
                dimensionManager.getDimensions().get("colsMin"),
                dimensionManager.getDimensions().get("colsMax")
        );
    }

    private static void runStrassenApproach(Matrix[] matrices) throws IOException {
        logger.info("Starting Strassen matrix multiplication...");
        RunStrassenApproach strassenApproach = new RunStrassenApproach();
        strassenApproach.runSetup(matrices[0], matrices[1]);
        logger.info("Strassen matrix multiplication completed.");
    }

    private static void runParallelApproach(Matrix[] matrices) throws IOException {
        logger.info("Starting parallel matrix multiplication...");
        RunParallelApproach parallelApproach = new RunParallelApproach();
        parallelApproach.runSetup(matrices[0], matrices[1]);
        logger.info("Parallel matrix multiplication completed.");
    }

    private static void runSequentialApproach(Matrix[] matrices) throws IOException {
        logger.info("Starting sequential matrix multiplication...");
        RunSequentialApproach sequentialApproach = new RunSequentialApproach();
        sequentialApproach.runSetup(matrices[0], matrices[1]);
        logger.info("Sequential matrix multiplication completed.");
    }

    private static void runStreamParallelApproach(Matrix[] matrices) throws IOException {
        logger.info("Starting stream parallel matrix multiplication...");
        RunStreamParallelApproach streamParallelApproach = new RunStreamParallelApproach();
        streamParallelApproach.runSetup(matrices[0], matrices[1]);
        logger.info("Stream parallel matrix multiplication completed.");
    }

    private static void runForkJoinApproach(Matrix[] matrices) throws IOException {
        logger.info("Setting the number of threads for the Fork-Join approach...");
        ForkJoinStreamMatrixMultiplication.setThreadPoolSize(NUM_THREADS);

        logger.info("Starting fork-join matrix multiplication...");
        RunForkJoinMultiplication forkJoinMultiplication = new RunForkJoinMultiplication();
        forkJoinMultiplication.runSetup(matrices[0], matrices[1]);
        logger.info("Fork-join matrix multiplication completed.");
    }
}
