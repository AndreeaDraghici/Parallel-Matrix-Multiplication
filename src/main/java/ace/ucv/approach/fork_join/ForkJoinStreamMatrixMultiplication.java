package ace.ucv.approach.fork_join;

import ace.ucv.model.Matrix;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * Created by Andreea Draghici on 1/30/2025
 * Name of project: ParallelMatrixMultiplication
 * Fork-Join approach for matrix multiplication using parallel streams.
 * This approach uses the ForkJoinPool and RecursiveTask to divide the task into smaller subtasks.
 * The subtasks are executed concurrently by multiple threads.
 * The threshold value is used to determine when to stop dividing the task into smaller subtasks.
 * If the number of rows and columns is less than or equal to the threshold value, the task is executed sequentially.
 * Otherwise, the task is divided into four subtasks that are executed concurrently.
 * The result is computed using parallel streams to improve performance.
 */
public class ForkJoinStreamMatrixMultiplication {
    private static final int THRESHOLD = 64; // Threshold for splitting tasks
    private static ForkJoinPool pool; // Custom ForkJoinPool

    // Method to set the number of threads in the pool (default is the number of available processors)
    public static void setThreadPoolSize(int numThreads) {
        pool = new ForkJoinPool(numThreads);
    }

    /**
     * Multiply two matrices using parallel streams for improved performance.
     *
     * @param A The first matrix
     * @param B The second matrix
     * @return A new matrix resulting from the multiplication of matrices A and B using the Fork-Join approach.
     */
    public static Matrix multiply(Matrix A, Matrix B) {
        if (A.getCols() != B.getRows()) {
            throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
        }

        int rows = A.getRows();
        int cols = B.getCols();
        int common = A.getCols();

        // Create a new matrix to store the result of the multiplication
        Matrix result = new Matrix(rows, cols);

        // Create a ForkJoinPool if not set by the user
        if (pool == null) {
            pool = new ForkJoinPool(); // Default pool if not set
        }

        // Perform matrix multiplication using the Fork-Join approach, invoke the task and wait for the result
        pool.invoke(new MatrixMultiplyTask(A, B, result, 0, rows, 0, cols, common));

        return result;
    }

    /**
     * RecursiveTask to multiply two matrices using the Fork-Join approach.
     * The task is divided into smaller subtasks that are executed concurrently by multiple threads.
     * The threshold value is used to determine when to stop dividing the task into smaller subtasks.
     * If the number of rows and columns is less than or equal to the threshold value, the task is executed sequentially.
     * Otherwise, the task is divided into four subtasks that are executed concurrently.
     * The result is computed using parallel streams to improve performance.
     *
     */
    static class MatrixMultiplyTask extends RecursiveTask<Void> {
        private final Matrix A, B, result;
        private final int rowStart, rowEnd, colStart, colEnd, common;

        // Constructor to initialize the task with the given matrices and dimensions
        public MatrixMultiplyTask(Matrix matrixA, Matrix matrixB, Matrix result, int rowStart, int rowEnd, int colStart, int colEnd, int common) {
            this.A = matrixA;
            this.B = matrixB;
            this.result = result;
            this.rowStart = rowStart;
            this.rowEnd = rowEnd;
            this.colStart = colStart;
            this.colEnd = colEnd;
            this.common = common;
        }

        @Override
        protected Void compute() {
            int rowCount = rowEnd - rowStart;
            int colCount = colEnd - colStart;

            if (rowCount * colCount <= THRESHOLD) {
                // Parallel processing using Java Streams
                IntStream.range(rowStart, rowEnd).parallel().forEach(i -> {
                    IntStream.range(colStart, colEnd).parallel().forEach(j -> {
                        int sum = 0;
                        for (int k = 0; k < common; k++) {
                            sum += A.getValue(i, k) * B.getValue(k, j);
                        }
                        result.setValue(i, j, sum);
                    });
                });
            } else {
                // Divide the task into four parallel tasks
                int midRow = (rowStart + rowEnd) / 2;
                int midCol = (colStart + colEnd) / 2;

                MatrixMultiplyTask topLeft = new MatrixMultiplyTask(A, B, result, rowStart, midRow, colStart, midCol, common);
                MatrixMultiplyTask topRight = new MatrixMultiplyTask(A, B, result, rowStart, midRow, midCol, colEnd, common);
                MatrixMultiplyTask bottomLeft = new MatrixMultiplyTask(A, B, result, midRow, rowEnd, colStart, midCol, common);
                MatrixMultiplyTask bottomRight = new MatrixMultiplyTask(A, B, result, midRow, rowEnd, midCol, colEnd, common);

                // Fork the tasks and wait for the results to be computed by
                // the threads in the pool (concurrently) using join() method to synchronize the tasks and get the results
                invokeAll(topLeft, topRight, bottomLeft, bottomRight);
            }
            return null;
        }
    }
}
