package ace.ucv.model;

/**
 * This class represents a matrix with integer values.
 * <p>
 * Provides methods to initialize the matrix, access and modify matrix data,
 * and retrieve the number of rows and columns.
 * </p>
 * Created by Andreea Draghici on 19/10/2024
 * Project name: ParallelMatrixMultiplication
 */
public class Matrix {

    // Matrix data stored in a two-dimensional array of integers
    private int[][] data;
    // Number of rows in the matrix
    private int rows;
    // Number of columns in the matrix
    private int cols;

    /**
     * Constructor that initializes an empty matrix with the specified dimensions.
     *
     * @param rows The number of rows in the matrix
     * @param cols The number of columns in the matrix
     */
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols]; // Initializes a 2D array with the specified dimensions
    }

    /**
     * Constructor that initializes a matrix with the given data and dimensions.
     *
     * @param data A 2D array representing the matrix data
     * @param rows The number of rows in the matrix
     * @param cols The number of columns in the matrix
     */
    public Matrix(int[][] data, int rows, int cols) {
        this.data = data;
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Returns the matrix data as a 2D array of integers.
     *
     * @return A 2D array of integers representing the matrix data
     */
    public int[][] getData() {
        return data;
    }

    /**
     * Sets the matrix data.
     *
     * @param data A 2D array of integers representing the new matrix data
     */
    public void setData(int[][] data) {
        this.data = data;
    }

    /**
     * Returns the number of rows in the matrix.
     *
     * @return The number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows in the matrix.
     *
     * @param rows The number of rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Returns the number of columns in the matrix.
     *
     * @return The number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets the number of columns in the matrix.
     *
     * @param cols The number of columns to set
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * Returns the value of a matrix element at the specified row and column.
     *
     * @param row The row index
     * @param col The column index
     * @return The value of the matrix element at the specified row and column
     */
    public double getValue(int row, int col) {
        return data[row][col]; // Access matrix element
    }

    /**
     * Sets the value of a matrix element at the specified row and column.
     *
     * @param row   The row index
     * @param col   The column index
     * @param value The new value to set
     */
    public void setValue(int row, int col, int value) {
        data[row][col] = value; // Modify matrix element
    }


}