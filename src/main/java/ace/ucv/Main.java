package ace.ucv;

import ace.ucv.generator.RandomMatrixGenerator;
import ace.ucv.model.Matrix;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

/**
 * Clasa principala care genereaza si salveaza matrici aleatoare in fisiere text.
 * <p>
 * Aceasta clasa creeaza 10 fisiere, fiecare continand o matrice generata aleator. Dimensiunile matricelor sunt
 * generate aleator (intre 2 si 10 randuri si coloane). Fisierele sunt salvate in directorul "resources".
 * </p>
 * Created by Andreea Draghici on 10/19/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class Main {

    /**
     * Punctul de intrare in aplicatie.
     * <p>
     * Aceasta metoda itereaza de 10 ori, generand 10 fisiere separate in care sunt scrise matrici aleatoare.
     * Dimensiunile matricelor sunt alese aleator pentru fiecare matrice in parte. Fiecare matrice este scrisa
     * intr-un fisier text in directorul "resources".
     * </p>
     *
     * @param args Argumente din linia de comanda (nu sunt utilizate)
     */
    public static void main(String[] args) {
        runSetup();
    }

    private static void runSetup() {
        Random rand = new Random();  // Generator de numere aleatoare
        RandomMatrixGenerator randomMatrixGenerator = new RandomMatrixGenerator();  // Obiect pentru generarea matricelor random
        MatrixPrinter printer = new MatrixPrinter();  // Obiect pentru a scrie matricea in fisier


        // Generare numar aleator de randuri si coloane pentru prima matrice (intre 2 si 10)
        int rowsA = rand.nextInt(9) + 2;  // Dimensiuni random intre 2 si 10 pentru randuri
        int colsA = rand.nextInt(9) + 2;  // Dimensiuni random intre 2 si 10 pentru coloane

        int rowsB = rand.nextInt(9) + 2;  // Dimensiuni random intre 2 si 10 pentru randuri
        int colsB = rand.nextInt(9) + 2;  // Dimensiuni random intre 2 si 10 pentru coloane

        // Generam o matrice random folosind dimensiunile generate
        Matrix matrixA = randomMatrixGenerator.generateRandomMatrix(rowsA, colsA);
        Matrix matrixB = randomMatrixGenerator.generateRandomMatrix(rowsB, colsB);

        printer.printMatrix(matrixA);
        System.out.printf("\n");
        printer.printMatrix(matrixB);


    }
}

