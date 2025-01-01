package ace.ucv.parallel;

import ace.ucv.model.Matrix;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

        // Get the number of available processors to determine the number of threads to use
        int numThreads = Runtime.getRuntime().availableProcessors();
        // Create a thread pool with a fixed number of threads equal to the number of available processors on the machine
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        /**
         * Divide the work into equal parts for each thread.
         * Each thread will compute the result for a range of rows in the result matrix.
         * The number of rows to process is determined by the total number of rows in matrix A divided by the number of threads.
         * The last thread will process the remaining rows.
         * The threads will update the result matrix concurrently.
         * The threads will wait for each other to finish before returning the result.
         * The threads will be shut down after all tasks are completed.
         */
        for (int i = 0; i < numThreads; i++) {
            // Calculate the range of rows to process for each thread
            final int fromRow = i * rowsA / numThreads;
            // The last thread will process the remaining rows in the matrix A if the number of rows is not divisible by the number of threads evenly (e.g., 5 rows divided by 2 threads).
            final int toRow = (i + 1) * rowsA / numThreads;

            /*
             * Submit a task to the executor service to compute the result for the specified range of rows.
             * The task will be executed concurrently by the threads in the thread pool.
             * The threads will update the result matrix concurrently. The threads will wait for each other to finish before returning the result.
             */
            executor.submit(() -> {
                for (int row = fromRow; row < toRow; row++) {
                    for (int col = 0; col < colsB; col++) {
                        for (int k = 0; k < matrixA.getCols(); k++) {
                            resultData[row][col] += matrixA.getData()[row][k] * matrixB.getData()[k][col];
                        }
                    }
                }
            });
        }

        // Shut down the executor service after all tasks are completed
        executor.shutdown();
        // Wait for all tasks to complete before returning the result
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

        return new Matrix(resultData, rowsA, colsB);
    }

}