package ace.ucv.utils;

import ace.ucv.model.Matrix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * The MatrixPrinter class is responsible for displaying a matrix in the console
 * and writing a matrix to a file.
 * <p>
 * The provided methods allow either direct console output of the matrix
 * or saving it to a text file. Files are written line by line, with values separated by tabs.
 * </p>
 * Created by Andreea Draghici on 10/19/2024
 * Project name: ParallelMatrixMultiplication
 */
public class MatrixPrinter {
    private static final Logger logger = LogManager.getLogger(MatrixPrinter.class);

    /**
     * Displays the matrix in the console.
     * <p>
     * This method iterates through all elements of the matrix, printing them in the console,
     * with tab characters separating the values in each row.
     * </p>
     *
     * @param matrix The matrix to be displayed in the console
     */
    public void printMatrix(Matrix matrix) {
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                // Print each matrix element, separated by a tab
                System.out.printf("%d\t", matrix.getData()[i][j]);
            }
            // Add a new line after each row
            System.out.println();
        }
    }

    /**
     * Writes the matrix and a message to the specified file.
     * <p>
     * This method writes a descriptive message followed by the matrix data to a file.
     * Each row is separated by a new line, and each element within a row is separated by a tab.
     * The file is created if it does not exist, or appended if it already exists.
     * </p>
     *
     * @param message  A description of the matrix (e.g., "Matrix A", "Matrix B", "Multiplication Result")
     * @param matrix   The matrix to be written to the file
     * @param filePath The path to the file where the matrix will be written
     * @throws IOException If an error occurs while writing to the file
     */
    public void writeMatrixToFile(String message, Matrix matrix, Path filePath) {
        // Prepare list of lines to write to the file
        List<String> lines = new ArrayList<>();
        lines.add(message);  // Add the message at the beginning

        // Iterate through each row of the matrix
        for (int i = 0; i < matrix.getRows(); i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < matrix.getCols(); j++) {
                line.append(matrix.getData()[i][j]).append("\t");
            }
            lines.add(line.toString());
        }
        lines.add(""); // Add an empty line after the matrix

        // Write the matrix and message to the file
        try {
            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error(String.format("Error writing to file at path: %s", filePath));
        }
    }
}
