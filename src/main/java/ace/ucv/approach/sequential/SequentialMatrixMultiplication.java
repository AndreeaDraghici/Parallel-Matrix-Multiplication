package ace.ucv.approach.sequential;

import ace.ucv.model.Matrix;

/**
 * Class responsible for the sequential multiplication of two matrices.
 */
public class SequentialMatrixMultiplication {

    /**
     * Method for sequentially multiplying two matrices.
     *
     * @param matrixA The first matrix
     * @param matrixB The second matrix
     * @return A new matrix resulting from the multiplication of matrices A and B
     * @throws IllegalArgumentException If the matrices cannot be multiplied
     */
    public Matrix multiply(Matrix matrixA, Matrix matrixB) {
        if (matrixA.getCols() != matrixB.getRows()) {
            throw new IllegalArgumentException("The number of columns in the first matrix must be equal to the number of rows in the second.");
        }

        int rowsA = matrixA.getRows();
        int colsA = matrixA.getCols();
        int colsB = matrixB.getCols();

        int[][] resultData = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                resultData[i][j] = 0;
                for (int k = 0; k < colsA; k++) {
                    resultData[i][j] += matrixA.getData()[i][k] * matrixB.getData()[k][j];
                }
            }
        }

        return new Matrix(resultData, rowsA, colsB);
    }
}
