package ace.ucv.service.generator;

import java.util.Random;

import ace.ucv.model.Matrix;

/**
 * This class is responsible for generating a matrix with random integer values.
 * <p>
 * The generated matrix will have specified dimensions (rows x cols),
 * with values randomly chosen between 0 and 99.
 * </p>
 * Created by Andreea Draghici on 10/19/2024
 * Project name: ParallelMatrixMultiplication
 */
public class RandomMatrixGenerator {

    /**
     * Generates a matrix with random integer values.
     * <p>
     * Each element in the matrix is assigned a random value between 0 and 99.
     * </p>
     *
     * @param rows The number of rows in the generated matrix
     * @param cols The number of columns in the generated matrix
     * @return A Matrix object filled with random integer values
     */
    public Matrix generateRandomMatrix(int rows, int cols) {
        Matrix matrix = new Matrix(rows, cols);
        Random rand = new Random();

        // Populate the matrix with random values between 0 and 99
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.getData()[i][j] = rand.nextInt(100);  // random values between 0 and 99
            }
        }
        return matrix;
    }
}
