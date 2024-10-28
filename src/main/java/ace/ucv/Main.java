package ace.ucv;

import ace.ucv.generator.RandomMatrixGenerator;
import ace.ucv.model.Matrix;
import ace.ucv.sequential.MatrixMultiplication;
import ace.ucv.utils.Constants;
import ace.ucv.utils.MatrixParser;
import ace.ucv.utils.MatrixPrinter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Random;

import static ace.ucv.utils.Constants.DIMENSIONS_FILE;

/**
 * Clasa principala care genereaza si salveaza matrici aleatoare in fisiere text.
 * <p>
 * Aceasta clasa creeaza un fisier care contine matricea A, matricea B, rezultatul inmultirii lor si timpii de executie.
 * </p>
 * Created by Andreea Draghici on 10/19/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class Main {

    /**
     * Punctul de intrare in aplicatie.
     * <p>
     * Aceasta metoda genereaza doua matrici aleatoare, le inmulteste si salveaza rezultatele intr-un fisier.
     * Timpul de executie pentru generarea matricelor, inmultire si scrierea in fisier este masurat si scris in fisier.
     * </p>
     *
     * @param args Argumente din linia de comanda (nu sunt utilizate)
     */
    public static void main(String[] args) {
        try {
            // Golim fisierul de output inainte de fiecare rulare
            Files.write(Paths.get(Constants.OUTPUT), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);

            runSetup();
        } catch (IOException e) {
            System.err.println("Eroare la citirea fisierului de dimensiuni: " + e.getMessage());
        }
    }

    /**
     * Metoda principala pentru citirea dimensiunilor, generarea matricelor si inmultirea lor.
     *
     * @throws IOException Daca fisierul nu poate fi citit
     */
    private static void runSetup() throws IOException {
        MatrixParser parser = new MatrixParser();
        // Citim dimensiunile din fisierul text
        Map<String, Integer> dimensions = parser.readMatrixDimensionsFromFile(DIMENSIONS_FILE);

        // Extragem valorile minime si maxime pentru randuri si coloane
        int rowsMin = dimensions.get("rowsMin");
        int rowsMax = dimensions.get("rowsMax");
        int colsMin = dimensions.get("colsMin");
        int colsMax = dimensions.get("colsMax");

        Random rand = new Random();  // Generator de numere aleatoare

        // Masuram timpul pentru generarea matricelor
        long startTime = System.nanoTime();

        // Generam dimensiuni aleatoare intre intervalele specificate
        int rowsA = rand.nextInt(rowsMax - rowsMin + 1) + rowsMin;  // Dimensiuni aleatoare intre rowsMin si rowsMax
        int colsA = rand.nextInt(colsMax - colsMin + 1) + colsMin;  // Dimensiuni aleatoare intre colsMin si colsMax
        int rowsB = colsA;  // Pentru ca inmultirea sa fie valida, nr de randuri al lui B = nr de coloane al lui A
        int colsB = rand.nextInt(colsMax - colsMin + 1) + colsMin;  // Dimensiuni aleatoare intre colsMin si colsMax

        // Cream generatorul de matrice random
        RandomMatrixGenerator randomMatrixGenerator = new RandomMatrixGenerator();

        // Generam doua matrici random folosind dimensiunile citite
        Matrix matrixA = randomMatrixGenerator.generateRandomMatrix(rowsA, colsA);
        Matrix matrixB = randomMatrixGenerator.generateRandomMatrix(rowsB, colsB);

        long endTime = System.nanoTime();
        long generationTime = endTime - startTime;

        // Masuram timpul pentru inmultirea matricelor
        startTime = System.nanoTime();
        MatrixMultiplication multiplication = new MatrixMultiplication();
        Matrix result = multiplication.multiply(matrixA, matrixB);
        endTime = System.nanoTime();
        long multiplicationTime = endTime - startTime;

        // Masuram timpul pentru scrierea matricelor in fisier
        MatrixPrinter printer = new MatrixPrinter();

        startTime = System.nanoTime();
        // Scriem Matricea A in fisier
        printer.writeMatrixToFile("Matrix A:", matrixA, Paths.get(Constants.OUTPUT));
        // Scriem Matricea B in fisier
        printer.writeMatrixToFile("Matrix B:", matrixB, Paths.get(Constants.OUTPUT));
        // Scriem rezultatul inmultirii in fisier
        printer.writeMatrixToFile("Matrix Multiplication Result:", result, Paths.get(Constants.OUTPUT));
        endTime = System.nanoTime();
        long writeTime = endTime - startTime;

        // Scriem timpul de executie in fisier
        StringBuilder timeInfo = new StringBuilder();
        timeInfo.append(String.format("Timp generare matrici: %.6f secunde\n", generationTime / 1_000_000_000.0));
        timeInfo.append(String.format("Timp inmultire matrici: %.6f secunde\n", multiplicationTime / 1_000_000_000.0));
        timeInfo.append(String.format("Timp scriere in fisier: %.6f secunde\n", writeTime / 1_000_000_000.0));

        // Scriem informatiile despre timpi in fisier
        Files.write(Paths.get(Constants.OUTPUT), timeInfo.toString().getBytes(), StandardOpenOption.APPEND);
    }
}
