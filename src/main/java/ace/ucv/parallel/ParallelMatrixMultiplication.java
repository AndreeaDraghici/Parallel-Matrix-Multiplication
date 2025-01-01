package ace.ucv.parallel;

import ace.ucv.model.Matrix;

/**
 * Created by Andreea Draghici on 12/25/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class ParallelMatrixMultiplication {

    /**
     * Parallel matrix multiplication using Java Threads.
     *
     * @param matrixA The first matrix.
     * @param matrixB The second matrix.
     * @return A new matrix resulting from the multiplication of matrix A and matrix B.
     * @throws InterruptedException If the thread execution is interrupted.
     */
    public Matrix multiply(Matrix matrixA, Matrix matrixB) throws InterruptedException {
        if (matrixA.getCols() != matrixB.getRows()) {
            throw new IllegalArgumentException("The number of columns in matrix A must equal the number of rows in matrix B.");
        }

        int rowsA = matrixA.getRows();
        int colsB = matrixB.getCols();
        int[][] resultData = new int[rowsA][colsB];

        // Number of threads could be tuned based on the hardware capabilities, or dynamically adjusted
        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];

        // Divide work among threads based on the number of rows in matrix A
        for (int i = 0; i < numThreads; i++) {
            final int fromRow = i * rowsA / numThreads; // Calculate the starting row for the thread to process
            final int toRow = (i + 1) * rowsA / numThreads; // Calculate the ending row for the thread to process

            /**
             * Each thread will compute a subset of the rows in the result matrix.
             * The subset is determined by the 'fromRow' and 'toRow' values.
             * The thread will iterate over the columns of the result matrix and compute the corresponding cell value.
             * The cell value is computed by multiplying the corresponding row in matrix A with the corresponding column in matrix B.
             */
            threads[i] = new Thread(() -> {
                // Compute the result matrix subset for the current thread range of rows and columns (fromRow, toRow) x (0, colsB)
                for (int row = fromRow; row < toRow; row++) {
                    // Iterate over the columns of the result matrix (colsB) and compute the cell value for the current row and column pair
                    for (int col = 0; col < colsB; col++) {
                        // Initialize the cell value to 0
                        resultData[row][col] = 0;
                        for (int k = 0; k < matrixA.getCols(); k++) {
                            resultData[row][col] += matrixA.getData()[row][k] * matrixB.getData()[k][col];
                        }
                    }
                }
            });
            threads[i].start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }

        return new Matrix(resultData, rowsA, colsB);
    }
}