package ace.ucv.sequential;

import ace.ucv.model.Matrix;

/**
 * Class responsible for the sequential multiplication of two matrices.
 * <p>
 * This class provides a method for multiplying two matrices sequentially.
 * The matrices must satisfy the condition that the number of columns in the first matrix
 * is equal to the number of rows in the second matrix.
 * </p>
 * Created by Andreea Draghici on 10/20/2024
 * Project name: ParallelMatrixMultiplication
 */
public class MatrixMultiplication {

    /**
     * Method for sequentially multiplying two matrices.
     * <p>
     * Multiplication is only possible if the number of columns in the first matrix (matrixA)
     * matches the number of rows in the second matrix (matrixB). The resulting matrix will have
     * dimensions (rowsA x colsB).
     * </p>
     *
     * @param matrixA The first matrix
     * @param matrixB The second matrix
     * @return A new matrix resulting from the multiplication of matrices A and B
     * @throws IllegalArgumentException If the matrices cannot be multiplied
     *                                  (number of columns in A != number of rows in B)
     */
    public Matrix multiply(Matrix matrixA, Matrix matrixB) {
        // Check if multiplication is possible
        if (matrixA.getCols() != matrixB.getRows()) {
            throw new IllegalArgumentException("The number of columns in the first matrix must be equal to the number of rows in the second.");
        }

        // Dimensions of the resulting matrix
        int rowsA = matrixA.getRows();
        int colsA = matrixA.getCols();
        int colsB = matrixB.getCols();

        // Create the result matrix with dimensions (rowsA x colsB)
        int[][] resultData = new int[rowsA][colsB];

        // Sequentially multiply the matrices
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                resultData[i][j] = 0;  // Initialize the result cell to 0
                for (int k = 0; k < colsA; k++) {
                    resultData[i][j] += matrixA.getData()[i][k] * matrixB.getData()[k][j];  // Sequential multiplication
                }
            }
        }

        // Return the new matrix
        return new Matrix(resultData, rowsA, colsB);
    }
}
