package ace.ucv.sequential;

import ace.ucv.model.Matrix;
import ace.ucv.utils.MatrixUtility;

/**
 * Created by Andreea Draghici on 12/24/2024
 * Class responsible for the multiplication of two matrices using the Strassen algorithm.
 * The Strassen algorithm is a divide-and-conquer algorithm that divides the matrices into smaller submatrices and performs the multiplication using a set of recursive formulas.
 */
public class StrassenMatrixMultiplication {

    /**
     * Method for multiplying two matrices using the Strassen algorithm.
     *
     * @param matrixA The first matrix
     * @param matrixB The second matrix
     * @return A new matrix resulting from the multiplication of matrices A and B
     */
    public Matrix multiply(Matrix matrixA, Matrix matrixB) {
        // Validate dimensions
        if (matrixA.getCols() != matrixB.getRows()) {
            throw new IllegalArgumentException("The number of columns in the first matrix must be equal to the number of rows in the second.");
        }

        // Pad the matrices to have dimensions that are powers of 2
        matrixA = MatrixUtility.padMatrix(matrixA);
        matrixB = MatrixUtility.padMatrix(matrixB);

        int n = matrixA.getRows();

        // Base case: if the matrices are 1x1, perform the multiplication directly
        if (n == 1) {
            return new Matrix(new int[][]{
                    {matrixA.getData()[0][0] * matrixB.getData()[0][0]}
            }, 1, 1);
        }

        // Split the matrices into smaller submatrices to perform the multiplication recursively
        Matrix[] subMatricesA = MatrixUtility.split(matrixA);
        Matrix[] subMatricesB = MatrixUtility.split(matrixB);

        /*
          Strassen's algorithm:
            - Divide the matrices into smaller sub-matrices
            - Compute the seven products recursively
            - Combine the results to form the final matrix
            - Crop the matrix to remove the padding
            - Return the final matrix
         M1 = (A11 + A22) * (B11 + B22)
         M2 = (A21 + A22) * (B11)
         M3 = (A11) * (B12 - B22)
         M4 = (A22) * (B21 - B11)
         M5 = (A11 + A12) * (B22)
         M6 = (A21 - A11) * (B11 + B12)
         M7 = (A12 - A22) * (B21 + B22)

         */
        Matrix M1 = multiply(MatrixUtility.add(subMatricesA[0], subMatricesA[3]), MatrixUtility.add(subMatricesB[0], subMatricesB[3]));
        Matrix M2 = multiply(MatrixUtility.add(subMatricesA[2], subMatricesA[3]), subMatricesB[0]);
        Matrix M3 = multiply(subMatricesA[0], MatrixUtility.subtract(subMatricesB[1], subMatricesB[3]));
        Matrix M4 = multiply(subMatricesA[3], MatrixUtility.subtract(subMatricesB[2], subMatricesB[0]));
        Matrix M5 = multiply(MatrixUtility.add(subMatricesA[0], subMatricesA[1]), subMatricesB[3]);
        Matrix M6 = multiply(MatrixUtility.subtract(subMatricesA[2], subMatricesA[0]), MatrixUtility.add(subMatricesB[0], subMatricesB[1]));
        Matrix M7 = multiply(MatrixUtility.subtract(subMatricesA[1], subMatricesA[3]), MatrixUtility.add(subMatricesB[2], subMatricesB[3]));

        /*
         * Combine the results to form the final matrix
         * C11 = M1 + M4 - M5 + M7
         * C12 = M3 + M5
         * C21 = M2 + M4
         * C22 = M1 - M2 + M3 + M6
         */
        Matrix C11 = MatrixUtility.add(MatrixUtility.subtract(MatrixUtility.add(M1, M4), M5), M7);
        Matrix C12 = MatrixUtility.add(M3, M5);
        Matrix C21 = MatrixUtility.add(M2, M4);
        Matrix C22 = MatrixUtility.add(MatrixUtility.subtract(MatrixUtility.add(M1, M3), M2), M6);

        // Combine the sub-matrices to form the final matrix
        Matrix result = MatrixUtility.combine(C11, C12, C21, C22, n);

        // Crop the matrix to remove the padding
        return MatrixUtility.cropMatrix(result, matrixA.getRows(), matrixB.getCols());
    }
}
