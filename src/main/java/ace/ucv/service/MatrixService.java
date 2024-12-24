package ace.ucv.service;

import ace.ucv.model.Matrix;
import ace.ucv.sequential.MatrixMultiplication;
import ace.ucv.service.generator.RandomMatrixGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

/**
 * Created by Andreea Draghici on 12/24/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class MatrixService {
    private Random rand = new Random();
    private RandomMatrixGenerator matrixGenerator = new RandomMatrixGenerator();
    private MatrixMultiplication multiplication = new MatrixMultiplication();

    public Matrix[] generateMatrices(int rowsMin, int rowsMax, int colsMin, int colsMax) {
        int rowsA = rand.nextInt(rowsMax - rowsMin + 1) + rowsMin;
        int colsA = rand.nextInt(colsMax - colsMin + 1) + colsMin;
        int rowsB = colsA;
        int colsB = rand.nextInt(colsMax - colsMin + 1) + colsMin;

        Matrix matrixA = matrixGenerator.generateRandomMatrix(rowsA, colsA);
        Matrix matrixB = matrixGenerator.generateRandomMatrix(rowsB, colsB);
        return new Matrix[]{matrixA, matrixB};
    }

    public Matrix multiplyMatrices(Matrix a, Matrix b, boolean useStrassen) {
        return useStrassen ? multiplication.strassenMultiply(a, b) : multiplication.multiply(a, b);
    }
}