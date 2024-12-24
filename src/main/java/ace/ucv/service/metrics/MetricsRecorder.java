package ace.ucv.service.metrics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Andreea Draghici on 12/24/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class MetricsRecorder {
    private static final Logger logger = LogManager.getLogger(MetricsRecorder.class);

    private long startTime;

    public void startTiming() {
        startTime = System.nanoTime();
    }

    public double stopAndLogTime(String message) {
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000_000.0;
        logger.info(message + ": " + duration + " seconds");
        return duration;
    }
}
