package ace.ucv.service.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for reading matrix dimensions from a text file.
 * <p>
 * Each line in the file should be in the format: key=value.
 * </p>
 */
public class MatrixParser {

    private static final Logger logger = LogManager.getLogger(MatrixParser.class);

    /**
     * Reads matrix dimensions from a text file.
     * <p>
     * Each line in the file must be in the format: key=value, where key represents
     * a parameter name and value represents the integer dimension associated with that key.
     * </p>
     *
     * @param filePath The path to the text file containing matrix dimensions
     * @return A map containing pairs of parameter names (as keys) and their associated values
     * @throws IOException If an error occurs while reading the file
     */
    public Map<String, Integer> readMatrixDimensionsFromFile(String filePath) throws IOException {
        // Initialize a map to store the dimensions
        Map<String, Integer> dimensions = new HashMap<>();

        try {
            // Read all lines from the specified file
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Process each line to extract key-value pairs
            for (String line : lines) {
                String[] parts = line.split("=");

                // Ensure the line is in the correct format: key=value
                if (parts.length == 2) {
                    String key = parts[0].trim();

                    try {
                        int value = Integer.parseInt(parts[1].trim());
                        dimensions.put(key, value);
                    } catch (NumberFormatException e) {
                        logger.error(String.format("Invalid number format for key: %s in line: %s", key, line));
                    }
                } else {
                   logger.error(String.format("Invalid line format (expected key=value): %s", line));
                }
            }
        } catch (IOException e) {
            logger.error(String.format("Error reading file at path: %s", filePath));
            throw e; // Re-throw the exception to inform the caller
        }

        // Return the map containing parsed dimensions
        return dimensions;
    }
}
