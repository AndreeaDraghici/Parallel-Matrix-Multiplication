package ace.ucv.generator;
import java.util.Random;
import ace.ucv.model.Matrix;

/**
 * Created by Andreea Draghici on 10/19/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class RandomMatrixGenerator {

    // Random matrix generator
    public Matrix generateRandomMatrix(int rows, int cols) {
        Matrix matrix = new Matrix(rows, cols);
        Random rand = new Random();

        // Populam matricea cu valori intre 0 si 99
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.getData()[i][j] = rand.nextInt(100);  // valori random intre 0 și 99
            }
        }
        return matrix;
    }
}
