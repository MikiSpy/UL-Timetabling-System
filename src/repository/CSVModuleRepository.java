package repository;

import model.Module;
import util.CSVUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing modules in a CSV file.
 */
public class CSVModuleRepository implements ModuleRepository {
    private final String file;
    private final CSVUtil csvUtil = new CSVUtil();

    /**
     * Create a module repository.
     * @param file path to CSV file
     */
    public CSVModuleRepository(String file) {
        this.file = file;
    }

    /**
     * Save a new module.
     * @param module module object
     * @return true if saved
     */
    @Override
    public boolean save(Module module) {
        try {
            String[][] rows = csvUtil.readCSV(file);

            String[] newRow = {
                    module.getCode(),
                    module.getTitle(),
                    String.valueOf(module.getLectureHours()),
                    String.valueOf(module.getLabHours()),
                    String.valueOf(module.getTutorialHours()),
            };

            String[][] updated = new String[rows.length + 1][];
            System.arraycopy(rows, 0, updated, 0, rows.length);
            updated[rows.length] = newRow;

            csvUtil.writeCSV(file, updated);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving module: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a module by code.
     * @param code module code
     * @return true if deleted
     */
    @Override
    public boolean delete(String code) {
        try {
            String[][] rows = csvUtil.readCSV(file);
            List<String[]> updated = new ArrayList<>();
            boolean deleted = false;

            for (String[] row : rows) {
                if (row[0].equals(code)) {
                    deleted = true;
                } else {
                    updated.add(row);
                }
            }

            if (deleted) {
                csvUtil.writeCSV(file, updated.toArray(new String[0][]));
            }
            return deleted;
        } catch (Exception e) {
            System.err.println("Error deleting module: " + e.getMessage());
            return false;
        }
    }

    /**
     * Find a module by code.
     * @param code module code
     * @return module or null
     */
    @Override
    public Module findByCode(String code) {
        try {
            String[][] rows = csvUtil.readCSV(file);

            for (int i = 1; i < rows.length; i++) { // skip header
                String[] row = rows[i];
                if (row[0].equals(code)) {
                    return new Module(
                            row[0], // code
                            row[1], // title
                            Integer.parseInt(row[2]), // lectureHours
                            Integer.parseInt(row[3]), // labHours
                            Integer.parseInt(row[4]), // tutorialHours
                            0, // year (default)
                            0, // semester (default)
                            "", // weeks (default)
                            ""  // programmeCode (default)
                    );
                }
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error finding module: " + e.getMessage());
            return null;
        }
    }
}
