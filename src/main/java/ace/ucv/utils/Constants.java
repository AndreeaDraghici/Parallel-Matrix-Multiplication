package ace.ucv.utils;

/**
 * Created by Andreea Draghici on 10/20/2024
 * Name of project: ParallelMatrixMultiplication
 * Constants class that holds the paths to the input and output files.
 */
public class Constants {

    public static final String DIMENSIONS_FILE = "src/main/resources/in/matrix_dimensions.txt";

    public static final String OUTPUT_SEQUENTIAL = "src/main/resources/out/output_sequential.txt";
    public static final String SEQUENTIAL_XLSX = "./src/main/resources/out/performance_metrics_sequential.xlsx";

    public static final String OUTPUT_PARALLEL = "src/main/resources/out/output_parallel.txt";
    public static final String PARALLEL_XLSX = "./src/main/resources/out/performance_metrics_parallel.xlsx";

    public static final String OUTPUT_STRASSEN = "src/main/resources/out/output_strassen.txt";
    public static final String STRASSEN_XLSX = "./src/main/resources/out/performance_metrics_strassen.xlsx";

    public static final String OUTPUT_STREAM_PARALLEL = "src/main/resources/out/output_stream_parallel.txt";
    public static final String STREAM_PARALLEL_XLSX = "./src/main/resources/out/performance_metrics_stream_parallel.xlsx";

    public static final String OUTPUT_FORK_JOIN = "src/main/resources/out/output_fork_join.txt";
    public static final String FORK_JOIN_XLSX = "./src/main/resources/out/performance_metrics_fork_join.xlsx";
}
