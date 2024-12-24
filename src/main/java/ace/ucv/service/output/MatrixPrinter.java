package ace.ucv.service.output;

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

    public void printMatrix(Matrix matrix) {
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                System.out.printf("%d\t", matrix.getData()[i][j]);
            }
            System.out.println();
        }
    }

    public void writeMatrixToFile(String message, Matrix matrix, Path filePath) throws IOException {
        List<String> lines = prepareMatrixData(message, matrix);
        try {
            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error(String.format("Error writing to file at path: %s", filePath));
            throw e;
        }
    }

    private List<String> prepareMatrixData(String message, Matrix matrix) {
        List<String> lines = new ArrayList<>();
        lines.add(message);
        for (int i = 0; i < matrix.getRows(); i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < matrix.getCols(); j++) {
                line.append(matrix.getData()[i][j]).append("\t");
            }
            lines.add(line.toString());
        }
        lines.add("");
        return lines;
    }
}