package util;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides utility methods for loading and saving CSV files.
 */
public class CSVUtil {

    /**
     * Loads data from a CSV file.
     * @param filePath path to the file
     * @return array of string arrays representing rows
     */
    public String[][] readCSV(String filePath) {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].trim();
                }
                rows.add(values);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return rows.toArray(new String[0][]);
    }

    /**
     * Writes data to a CSV file.
     * @param filePath path to the file
     * @param rows data to write
     */
    public void writeCSV(String filePath, String[][] rows) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : rows) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }
}
