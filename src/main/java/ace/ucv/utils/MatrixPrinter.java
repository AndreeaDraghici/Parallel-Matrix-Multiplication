package ace.ucv.utils;

import ace.ucv.model.Matrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa MatrixPrinter este responsabila pentru afisarea unei matrici in consola
 * si pentru scrierea unei matrici intr-un fisier.
 * <p>
 * Metodele oferite permit fie afisarea matricii direct in consola, fie salvarea acesteia
 * intr-un fisier text. Fisierele sunt scrise linie cu linie, cu valori separate prin tab.
 * </p>
 * Created by Andreea Draghici on 10/19/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class MatrixPrinter {

    /**
     * Metoda care afiseaza matricea in consola.
     * <p>
     * Aceasta metoda itereaza prin toate elementele matricii, afisandu-le in consola,
     * folosind tabul pentru separarea valorilor de pe fiecare rand.
     * </p>
     *
     * @param matrix Matricea care va fi afisata in consola
     */
    public void printMatrix(Matrix matrix) {
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                // Afisam fiecare element din matrice, separat de un tab
                System.out.printf("%d\t", matrix.getData()[i][j]);
            }
            // Dupa fiecare rand, adaugam o linie noua
            System.out.println();
        }
    }

    /**
     * Metoda care scrie matricea si un mesaj in fisierul specificat.
     *
     * @param message Mesajul care descrie ce este matricea (ex. "Matrix A", "Matrix B", "Rezultatul inmultirii")
     * @param matrix  Matricea care va fi scrisa in fisier
     * @param filePath Calea catre fisierul in care matricea va fi scrisa
     * @throws IOException Daca apare o eroare la scrierea fisierului
     */
    public void writeMatrixToFile(String message, Matrix matrix, Path filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(message);  // Adaugam mesajul la inceput

        // Iteram prin fiecare rand al matricii
        for (int i = 0; i < matrix.getRows(); i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < matrix.getCols(); j++) {
                line.append(matrix.getData()[i][j]).append("\t");
            }
            lines.add(line.toString());
        }
        lines.add(""); // Linie goala dupa matrice

        // Scriem matricea si mesajul in fisier (se adauga in fisierul deja existent)
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
