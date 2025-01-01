package ace.ucv;

import ace.ucv.model.Matrix;
import ace.ucv.parallel.RunParallelApproach;
import ace.ucv.sequential.RunSequentialApproach;
import ace.ucv.sequential.RunStrassenApproach;
import ace.ucv.service.MatrixService;
import ace.ucv.service.parser.DimensionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        DimensionManager dimensionManager = new DimensionManager();
        MatrixService matrixService = new MatrixService();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Select a matrix multiplication method:");
                System.out.println("1. Sequential");
                System.out.println("2. Parallel");
                System.out.println("3. Strassen");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                String choice = reader.readLine().trim();
                // Exit the program if the user chooses to do so (option 4)
                if (choice.equals("4")) {
                    System.out.println("Exiting the program.");
                    break;
                }

                try {
                    // Generate matrices based on the dimensions from the dimension manager
                    Matrix[] matrices = matrixService.generateMatrices(
                            dimensionManager.getDimensions().get("rowsMin"),
                            dimensionManager.getDimensions().get("rowsMax"),
                            dimensionManager.getDimensions().get("colsMin"),
                            dimensionManager.getDimensions().get("colsMax")
                    );

                    /* Perform matrix multiplication based on the user's choice */
                    switch (choice) {
                        case "1":
                            logger.info("Starting sequential matrix multiplication...");
                            RunSequentialApproach sequentialApproach = new RunSequentialApproach();
                            sequentialApproach.runSetup(matrices[0], matrices[1]);
                            logger.info("Sequential matrix multiplication completed.");
                            break;

                        case "2":
                            logger.info("Starting parallel matrix multiplication...");
                            RunParallelApproach parallelApproach = new RunParallelApproach();
                            parallelApproach.runSetup(matrices[0], matrices[1]);
                            logger.info("Parallel matrix multiplication completed.");
                            break;

                        case "3":
                            logger.info("Starting Strassen matrix multiplication...");
                            RunStrassenApproach strassenApproach = new RunStrassenApproach();
                            strassenApproach.runSetup(matrices[0], matrices[1]);
                            logger.info("Strassen matrix multiplication completed.");
                            break;

                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                            break;
                    }
                } catch (Exception e) {
                    logger.error("An error occurred during matrix multiplication: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.error("An error occurred while reading input: " + e.getMessage());
        }
    }
}
