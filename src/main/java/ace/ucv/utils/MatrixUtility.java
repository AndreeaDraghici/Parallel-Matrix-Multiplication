package ace.ucv.utils;

import ace.ucv.model.Matrix;

/***
 *  Created by Andreea Draghici on 10/20/2024
 * Utility class that provides methods for matrix operations.
 * Name of project: ParallelMatrixMultiplication
 *
 */
public class MatrixUtility {

    /**
     * Add two matrices element-wise .
     *
     * @param matrixA - the first matrix
     * @param matrixB - the second matrix
     * @return A new matrix resulting from the addition of matrices A and B
     */
    public static Matrix add(Matrix matrixA, Matrix matrixB) {
        int[][] result = new int[matrixA.getRows()][matrixA.getCols()];

        // Add the elements of the two matrices element-wise
        for (int i = 0; i < matrixA.getRows(); i++) {
            for (int j = 0; j < matrixA.getCols(); j++) {
                result[i][j] = matrixA.getData()[i][j] + matrixB.getData()[i][j];
            }
        }

        return new Matrix(result, matrixA.getRows(), matrixA.getCols());
    }

    /**
     * Subtract two matrices element-wise.
     *
     * @param matrixA - the first matrix
     * @param matrixB - the second matrix
     * @return A new matrix resulting from the subtraction of matrices A and B
     */
    public static Matrix subtract(Matrix matrixA, Matrix matrixB) {
        int[][] result = new int[matrixA.getRows()][matrixA.getCols()];

        for (int i = 0; i < matrixA.getRows(); i++) {
            for (int j = 0; j < matrixA.getCols(); j++) {
                result[i][j] = matrixA.getData()[i][j] - matrixB.getData()[i][j];
            }
        }

        return new Matrix(result, matrixA.getRows(), matrixA.getCols());
    }

    /**
     * Pad the matrix with zeros to have dimensions that are powers of 2.
     *
     * @param matrix - the matrix to be padded
     * @return A new matrix with dimensions that are powers of 2
     */
    public static Matrix padMatrix(Matrix matrix) {
        int n = matrix.getRows();
        int m = matrix.getCols();
        // Find the smallest power of 2 that is greater than the maximum of n and m
        int newSize = (int) Math.pow(2, Math.ceil(Math.log(Math.max(n, m)) / Math.log(2)));

        // If the matrix already has dimensions that are powers of 2, return the matrix as it is
        if (n == newSize && m == newSize) {
            return matrix;
        }

        int[][] paddedData = new int[newSize][newSize];
        // Copy the elements of the original matrix into the new matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                paddedData[i][j] = matrix.getData()[i][j];
            }
        }

        return new Matrix(paddedData, newSize, newSize);
    }

    /**
     * Split the matrix into four submatrices.
     *
     * @param matrix - the matrix to be split
     * @return An array containing the four submatrices
     */
    public static Matrix[] split(Matrix matrix) {
        int newSize = matrix.getRows() / 2;
        Matrix[] subMatrices = new Matrix[4];

        int[][] a11 = new int[newSize][newSize]; // top-left
        int[][] a12 = new int[newSize][newSize]; // top-right
        int[][] a21 = new int[newSize][newSize]; // bottom-left
        int[][] a22 = new int[newSize][newSize]; // bottom-right

        // Copy the elements of the original matrix into the submatrices
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                // Split the matrix into four submatrices
                a11[i][j] = matrix.getData()[i][j]; // top-left submatrix
                a12[i][j] = matrix.getData()[i][j + newSize]; // top-right submatrix
                a21[i][j] = matrix.getData()[i + newSize][j]; // bottom-left submatrix
                a22[i][j] = matrix.getData()[i + newSize][j + newSize]; // bottom-right submatrix
            }
        }

        subMatrices[0] = new Matrix(a11, newSize, newSize);
        subMatrices[1] = new Matrix(a12, newSize, newSize);
        subMatrices[2] = new Matrix(a21, newSize, newSize);
        subMatrices[3] = new Matrix(a22, newSize, newSize);

        return subMatrices;
    }

    /**
     * Combine four submatrices into a single matrix.
     *
     * @param C11  - the top-left submatrix
     * @param C12  - the top-right submatrix
     * @param C21  - the bottom-left submatrix
     * @param C22  - the bottom-right submatrix
     * @param size - the size of the resulting matrix
     * @return A new matrix resulting from the combination of the four submatrices
     */
    public static Matrix combine(Matrix C11, Matrix C12, Matrix C21, Matrix C22, int size) {
        int[][] result = new int[size][size];
        int newSize = size / 2;

        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                // Combine the submatrices into a single matrix
                result[i][j] = C11.getData()[i][j];
                result[i][j + newSize] = C12.getData()[i][j];
                result[i + newSize][j] = C21.getData()[i][j];
                result[i + newSize][j + newSize] = C22.getData()[i][j];
            }
        }

        return new Matrix(result, size, size);
    }

    /**
     *  Crop the matrix to remove the padding.
     * @param matrix - the matrix to be cropped
     * @param originalRows - the original number of rows
     * @param originalCols - the original number of columns
     * @return A new matrix with the original dimensions
     */
    public static Matrix cropMatrix(Matrix matrix, int originalRows, int originalCols) {
        int[][] croppedData = new int[originalRows][originalCols];
        for (int i = 0; i < originalRows; i++) {
            for (int j = 0; j < originalCols; j++) {
                // Crop the matrix to remove the padding
                croppedData[i][j] = matrix.getData()[i][j];
            }
        }
        return new Matrix(croppedData, originalRows, originalCols);
    }
}
