package ace.ucv.model;

/**
 * Aceasta clasa reprezinta o matrice cu valori intregi.
 * <p>
 * Ofera metode pentru a initializa matricea, a obtine si a seta datele matricii,
 * precum si pentru a returna numarul de randuri si coloane.
 * </p>
 * Creat de Andreea Draghici pe 19/10/2024
 * Nume proiect: ParallelMatrixMultiplication
 */
public class Matrix {

    // Datele matricii stocate intr-un tablou bidimensional de intregi
    private int[][] data;
    // Numarul de randuri din matrice
    private int rows;
    // Numarul de coloane din matrice
    private int cols;

    /**
     * Constructor care initializeaza o matrice goala cu dimensiunile specificate.
     *
     * @param rows numarul de randuri ale matricei
     * @param cols numarul de coloane ale matricei
     */
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols]; // Initializeaza un tablou bidimensional cu dimensiunile specificate
    }

    /**
     * Constructor care initializeaza o matrice cu datele si dimensiunile date.
     *
     * @param data tabloul bidimensional care contine datele matricii
     * @param rows numarul de randuri ale matricei
     * @param cols numarul de coloane ale matricei
     */
    public Matrix(int[][] data, int rows, int cols) {
        this.data = data;
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Returneaza datele matricii ca un tablou bidimensional de intregi.
     *
     * @return un tablou bidimensional de intregi care reprezinta datele matricii
     */
    public int[][] getData() {
        return data;
    }

    /**
     * Seteaza datele matricii.
     *
     * @param data un tablou bidimensional de intregi care reprezinta datele matricii
     */
    public void setData(int[][] data) {
        this.data = data;
    }

    /**
     * Returneaza numarul de randuri din matrice.
     *
     * @return numarul de randuri
     */
    public int getRows() {
        return rows;
    }

    /**
     * Seteaza numarul de randuri din matrice.
     *
     * @param rows numarul de randuri care trebuie setat
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Returneaza numarul de coloane din matrice.
     *
     * @return numarul de coloane
     */
    public int getCols() {
        return cols;
    }

    /**
     * Seteaza numarul de coloane din matrice.
     *
     * @param cols numarul de coloane care trebuie setat
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

}
