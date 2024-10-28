package ace.ucv.sequential;

import ace.ucv.model.Matrix;

/**
 * Clasa responsabila pentru multiplicarea secventiala a doua matrici.
 * <p>
 * Aceasta clasa implementeaza metoda pentru multiplicarea a doua matrici in mod secvential.
 * </p>
 * Created by Andreea Draghici on 10/20/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class MatrixMultiplication {

    /**
     * Metoda pentru inmultirea a doua matrici.
     * <p>
     * Matricea A trebuie sa aiba numarul de coloane egal cu numarul de randuri al matricei B.
     * Rezultatul va fi o noua matrice cu dimensiunea (rowsA x colsB).
     * </p>
     *
     * @param matrixA Prima matrice
     * @param matrixB A doua matrice
     * @return O noua matrice rezultata din inmultirea matricilor A si B
     * @throws IllegalArgumentException Daca matricile nu pot fi inmultite (numarul de coloane al lui A != numarul de randuri al lui B)
     */
    public Matrix multiply(Matrix matrixA, Matrix matrixB) {
        // Verificam daca inmultirea este posibila
        if (matrixA.getCols() != matrixB.getRows()) {
            throw new IllegalArgumentException("Numarul de coloane al primei matrici trebuie sa fie egal cu numarul de randuri al celei de-a doua.");
        }

        // Dimensiunile matricii rezultat
        int rowsA = matrixA.getRows();
        int colsA = matrixA.getCols();
        int colsB = matrixB.getCols();

        // Cream matricea rezultat cu dimensiunea (rowsA x colsB)
        int[][] resultData = new int[rowsA][colsB];

        // Inmultim matricile secvential
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                resultData[i][j] = 0;  // Initializam rezultatul la 0
                for (int k = 0; k < colsA; k++) {
                    resultData[i][j] += matrixA.getData()[i][k] * matrixB.getData()[k][j];  // Inmultire secventiala
                }
            }
        }

        // Returnam noua matrice
        return new Matrix(resultData, rowsA, colsB);
    }
}
