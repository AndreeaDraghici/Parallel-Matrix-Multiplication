package ace.ucv.service.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;

import static ace.ucv.utils.Constants.DIMENSIONS_FILE;

/**
 * Created by Andreea Draghici on 1/2/2025
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
            logger.error(new StringBuilder().append("Error: Could not read dimensions from file ").append(DIMENSIONS_FILE).toString(), e);
            throw e;
        }
    }
}
