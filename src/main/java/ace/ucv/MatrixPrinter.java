package ace.ucv;

import ace.ucv.model.Matrix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
     * Metoda care scrie matricea intr-un fisier specificat.
     * <p>
     * Aceasta metoda creeaza o lista de linii care reprezinta continutul matricii,
     * apoi scrie fiecare linie in fisierul specificat. Elementele din fiecare rand
     * al matricii sunt separate prin tab.
     * </p>
     *
     * @param matrix   Matricea care va fi scrisa in fisier
     * @param filePath Calea catre fisierul in care matricea va fi scrisa
     * @throws IOException Daca apare o eroare la scrierea fisierului
     */
    public void writeMatrixToFile(Matrix matrix, Path filePath) throws IOException {
        List<String> lines = new ArrayList<>();  // Lista in care vom stoca fiecare rand al matricii ca string

        // Iteram prin fiecare rand al matricii
        for (int i = 0; i < matrix.getRows(); i++) {
            StringBuilder line = new StringBuilder();  // StringBuilder pentru a construi randul curent
            for (int j = 0; j < matrix.getCols(); j++) {
                // Adaugam fiecare element din rand in StringBuilder, urmat de un tab
                line.append(matrix.getData()[i][j]).append("\t");
            }
            // Adaugam randul complet in lista de linii
            lines.add(line.toString());
        }

        // Scriem toate liniile in fisierul specificat
        Files.write(filePath, lines);
    }
}
