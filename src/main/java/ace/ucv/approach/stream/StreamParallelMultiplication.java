package ace.ucv.approach.stream;

import ace.ucv.model.Matrix;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Created by Andreea Draghici on 1/29/2025
 * Name of project: ParallelMatrixMultiplication
 */
public class StreamParallelMultiplication {

    /**
     * Multiply two matrices using parallel streams for improved performance.
     *
     * @param matrixA The first matrix
     * @param matrixB The second matrix
     * @return A new matrix resulting from the multiplication of matrices A and B
     */
    public Matrix multiply(Matrix matrixA, Matrix matrixB) {
        if (matrixA.getCols() != matrixB.getRows()) {
            throw new IllegalArgumentException("The number of columns in matrix A must equal the number of rows in matrix B.");
        }

        int rowsA = matrixA.getRows();
        int colsB = matrixB.getCols();
        int[][] resultData = new int[rowsA][colsB];

        /**
         *  Use parallel streams to improve performance  by dividing the work into smaller tasks
         *  that can be executed concurrently by multiple threads.
         *  The parallel() method is used to create a parallel stream that will process the elements concurrently.
         */
        IntStream.range(0, rowsA).parallel().forEach(i -> {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < matrixA.getCols(); k++) {
                    resultData[i][j] += matrixA.getData()[i][k] * matrixB.getData()[k][j];
                }
            }
        });

        return new Matrix(resultData, rowsA, colsB);
    }

}
