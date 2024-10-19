package ace.ucv.model;

/**
 * Created by Andreea Draghici on 10/19/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class Matrix {

    private int[][] data;
    private int rows;
    private int cols;

    // Constructor pentru initializarea matricii
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols];
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

}
