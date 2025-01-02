package ace.ucv.service.output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Andreea Draghici on 12/24/2024
 * Name of project: ParallelMatrixMultiplication
 */
public class PerformanceMetricsRecorder {
    private static final Logger logger = LogManager.getLogger(PerformanceMetricsRecorder.class);
    private XSSFWorkbook workbook;
    private String filePath;

    /**
     * Creates a new instance of PerformanceMetricsRecorder.
     * @param filePath the path to the Excel file where the metrics will be recorded.
     */
    public PerformanceMetricsRecorder(String filePath) {
        this.filePath = filePath;
        this.workbook = new XSSFWorkbook();
    }

    /**
     * Records a metric in the Excel file.
     * @param sheetName the name of the sheet where the metric will be recorded.
     * @param metricDescription the description of the metric.
     * @param value the value of the metric.
     */
    public void recordMetric(String sheetName, String metricDescription, double value) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }

        int lastRow = sheet.getLastRowNum();
        Row row = sheet.createRow(lastRow + 1);
        Cell cellDesc = row.createCell(0);
        cellDesc.setCellValue(metricDescription);

        Cell cellValue = row.createCell(1);
        cellValue.setCellValue(value);
    }

    /**
     * Saves the metrics to the Excel file.
     */
    public void saveToFile() {
        try (FileOutputStream out = new FileOutputStream(this.filePath)) {
            workbook.write(out);
            logger.info("Metrics written to Excel file successfully.");
        } catch (IOException e) {
            logger.error("Failed to write metrics to Excel file", e);
        }
    }
    /**
     * Saves the metrics to the Excel file.
     * @param path the path to the Excel file where the metrics will be recorded.
     */
    public void saveToFile(String path) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }

}