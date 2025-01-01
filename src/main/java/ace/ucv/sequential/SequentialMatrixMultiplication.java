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
public class SequentialMatrixMultiplication {

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


    public Matrix strassenMultiply(Matrix matrixA, Matrix matrixB) {
        if (matrixA.getCols() != matrixB.getRows()) {
            throw new IllegalArgumentException("The number of columns in the first matrix must be equal to the number of rows in the second.");
        }

        matrixA = padMatrix(matrixA);
        matrixB = padMatrix(matrixB);

        int n = matrixA.getRows();

        if (n == 1) {
            return new Matrix(new int[][]{
                    {matrixA.getData()[0][0] * matrixB.getData()[0][0]}
            }, 1, 1);
        }

        int newSize = n / 2;
        Matrix[] subMatricesA = split(matrixA);
        Matrix[] subMatricesB = split(matrixB);

        Matrix M1 = strassenMultiply(add(subMatricesA[0], subMatricesA[3]), add(subMatricesB[0], subMatricesB[3]));
        Matrix M2 = strassenMultiply(add(subMatricesA[2], subMatricesA[3]), subMatricesB[0]);
        Matrix M3 = strassenMultiply(subMatricesA[0], subtract(subMatricesB[1], subMatricesB[3]));
        Matrix M4 = strassenMultiply(subMatricesA[3], subtract(subMatricesB[2], subMatricesB[0]));
        Matrix M5 = strassenMultiply(add(subMatricesA[0], subMatricesA[1]), subMatricesB[3]);
        Matrix M6 = strassenMultiply(subtract(subMatricesA[2], subMatricesA[0]), add(subMatricesB[0], subMatricesB[1]));
        Matrix M7 = strassenMultiply(subtract(subMatricesA[1], subMatricesA[3]), add(subMatricesB[2], subMatricesB[3]));

        Matrix C11 = add(subtract(add(M1, M4), M5), M7);
        Matrix C12 = add(M3, M5);
        Matrix C21 = add(M2, M4);
        Matrix C22 = add(subtract(add(M1, M3), M2), M6);

        Matrix result = combine(C11, C12, C21, C22, n);

        // Returăm doar partea relevantă a rezultatului (dimensiunile inițiale)
        return cropMatrix(result, matrixA.getRows(), matrixB.getCols());
    }

    private Matrix cropMatrix(Matrix matrix, int originalRows, int originalCols) {
        int[][] croppedData = new int[originalRows][originalCols];
        for (int i = 0; i < originalRows; i++) {
            for (int j = 0; j < originalCols; j++) {
                croppedData[i][j] = matrix.getData()[i][j];
            }
        }
        return new Matrix(croppedData, originalRows, originalCols);
    }


    private Matrix[] split(Matrix matrix) {
        int newSize = matrix.getRows() / 2;
        Matrix[] subMatrices = new Matrix[4];

        int[][] a11 = new int[newSize][newSize];
        int[][] a12 = new int[newSize][newSize];
        int[][] a21 = new int[newSize][newSize];
        int[][] a22 = new int[newSize][newSize];

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                a11[i][j] = matrix.getData()[i][j];
                a12[i][j] = matrix.getData()[i][j + newSize];
                a21[i][j] = matrix.getData()[i + newSize][j];
                a22[i][j] = matrix.getData()[i + newSize][j + newSize];
            }
        }

        subMatrices[0] = new Matrix(a11, newSize, newSize);
        subMatrices[1] = new Matrix(a12, newSize, newSize);
        subMatrices[2] = new Matrix(a21, newSize, newSize);
        subMatrices[3] = new Matrix(a22, newSize, newSize);

        return subMatrices;
    }

    private Matrix combine(Matrix C11, Matrix C12, Matrix C21, Matrix C22, int size) {
        int[][] result = new int[size][size];
        int newSize = size / 2;

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                result[i][j] = C11.getData()[i][j];
                result[i][j + newSize] = C12.getData()[i][j];
                result[i + newSize][j] = C21.getData()[i][j];
                result[i + newSize][j + newSize] = C22.getData()[i][j];
            }
        }

        return new Matrix(result, size, size);
    }

    private Matrix add(Matrix matrixA, Matrix matrixB) {
        int[][] result = new int[matrixA.getRows()][matrixA.getCols()];

        for (int i = 0; i < matrixA.getRows(); i++) {
            for (int j = 0; j < matrixA.getCols(); j++) {
                result[i][j] = matrixA.getData()[i][j] + matrixB.getData()[i][j];
            }
        }

        return new Matrix(result, matrixA.getRows(), matrixA.getCols());
    }

    private Matrix subtract(Matrix matrixA, Matrix matrixB) {
        int[][] result = new int[matrixA.getRows()][matrixA.getCols()];

        for (int i = 0; i < matrixA.getRows(); i++) {
            for (int j = 0; j < matrixA.getCols(); j++) {
                result[i][j] = matrixA.getData()[i][j] - matrixB.getData()[i][j];
            }
        }

        return new Matrix(result, matrixA.getRows(), matrixA.getCols());
    }

    private Matrix padMatrix(Matrix matrix) {
        int n = matrix.getRows();
        int m = matrix.getCols();
        int newSize = (int) Math.pow(2, Math.ceil(Math.log(Math.max(n, m)) / Math.log(2)));

        if (n == newSize && m == newSize) {
            return matrix; // Matricea are deja dimensiunea corectă
        }

        int[][] paddedData = new int[newSize][newSize];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                paddedData[i][j] = matrix.getData()[i][j];
            }
        }

        return new Matrix(paddedData, newSize, newSize);
    }

}
