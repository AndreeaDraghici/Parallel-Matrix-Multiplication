package ace.ucv.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clasa responsabila pentru citirea dimensiunilor dintr-un fisier text.
 * Fiecare linie din fisier trebuie sa fie de forma: cheie=valoare.
 */
public class MatrixParser {

    /**
     * Citeste dimensiunile matricelor dintr-un fisier text.
     * <p>
     * Fiecare linie din fisier trebuie sa fie de forma: cheie=valoare
     * </p>
     *
     * @param filePath Calea catre fisierul de dimensiuni
     * @return Un map care contine perechi nume-parametru si valoare
     * @throws IOException In caz de eroare la citirea fisierului
     */
    public Map<String, Integer> readMatrixDimensionsFromFile(String filePath) throws IOException {
        Map<String, Integer> dimensions = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        for (String line : lines) {
            String[] parts = line.split("=");
            if (parts.length == 2) {
                String key = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                dimensions.put(key, value);
            }
        }
        return dimensions;
    }
}
