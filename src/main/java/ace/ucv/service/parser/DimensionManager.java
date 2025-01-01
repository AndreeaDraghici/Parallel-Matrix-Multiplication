package ace.ucv.service.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

import static ace.ucv.utils.Constants.DIMENSIONS_FILE;

/**
 * Created by Andreea Draghici on 12/24/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class DimensionManager {
    private static final Logger logger = LogManager.getLogger(DimensionManager.class);
    private MatrixParser parser = new MatrixParser();

    public Map<String, Integer> getDimensions() throws IOException {
        try {
            Map<String, Integer> dimensions = parser.readMatrixDimensionsFromFile(DIMENSIONS_FILE);
            logger.info("Dimensions read successfully from file.");
            return dimensions;
        } catch (IOException e) {
            logger.error("Error: Could not read dimensions from file " + DIMENSIONS_FILE, e);
            throw e;
        }
    }
}