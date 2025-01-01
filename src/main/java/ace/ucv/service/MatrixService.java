package ace.ucv.service;

import ace.ucv.model.Matrix;
import ace.ucv.sequential.SequentialMatrixMultiplication;
import ace.ucv.sequential.StrassenMatrixMultiplication;
import ace.ucv.service.generator.RandomMatrixGenerator;

import java.util.Random;

/**
 * Created by Andreea Draghici on 12/24/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class MatrixService {
    private final Random rand = new Random();
    private final RandomMatrixGenerator matrixGenerator = new RandomMatrixGenerator();
    private final SequentialMatrixMultiplication sequentialMultiplication = new SequentialMatrixMultiplication();
    private final StrassenMatrixMultiplication strassenMultiplication = new StrassenMatrixMultiplication();

    /**
     * Generate two random matrices with dimensions within the specified range.
     * @param rowsMin Minimum number of rows
     * @param rowsMax Maximum number of rows
     * @param colsMin Minimum number of columns
     * @param colsMax Maximum number of columns
     * @return An array containing the two generated matrices
     */
    public Matrix[] generateMatrices(int rowsMin, int rowsMax, int colsMin, int colsMax) {
        int rowsA = rand.nextInt(rowsMax - rowsMin + 1) + rowsMin;
        int colsA = rand.nextInt(colsMax - colsMin + 1) + colsMin;
        int colsB = rand.nextInt(colsMax - colsMin + 1) + colsMin;

        Matrix matrixA = matrixGenerator.generateRandomMatrix(rowsA, colsA);
        Matrix matrixB = matrixGenerator.generateRandomMatrix(colsA, colsB);
        return new Matrix[]{matrixA, matrixB};
    }

    public Matrix multiplySequentialMatrices(Matrix a, Matrix b) {
        return sequentialMultiplication.multiply(a, b);
    }
    public Matrix multiplyStrassenMatrices(Matrix a, Matrix b) {
        return strassenMultiplication.multiply(a, b);
    }
}
