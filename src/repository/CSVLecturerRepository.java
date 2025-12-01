package repository;

import model.Lecturer;
import util.CSVUtil;

/**
 * Repository for managing lecturers in a CSV file.
 */
public class CSVLecturerRepository implements LecturerRepository {
    private final String lecturersFile;
    private final CSVUtil csvUtil = new CSVUtil();

    /**
     * Create a lecturer repository.
     * @param lecturersFile path to CSV file
     */
    public CSVLecturerRepository(String lecturersFile) {
        this.lecturersFile = lecturersFile;
    }

    /**
     * Find a lecturer by ID.
     * @param id lecturer ID
     * @return lecturer or null
     */
    @Override
    public Lecturer findById(String id) {
        String[][] rows = csvUtil.readCSV(lecturersFile);

        for (int i = 1; i < rows.length; i++) {
            String lecturerId = rows[i][0];
            if (lecturerId.equalsIgnoreCase(id)) {
                String name = rows[i][1];
                String email = rows[i][2];
                String password = rows[i][3];
                return new Lecturer(lecturerId, name, email, password);
            }
        }
        return null;
    }

    /**
     * Save a new lecturer.
     * @param lecturer lecturer object
     * @return true if saved
     */
    public boolean save(Lecturer lecturer) {
        try {
            String[][] rows = csvUtil.readCSV(lecturersFile);

            String[] newRow = {
                    lecturer.getId(),
                    lecturer.getName(),
                    lecturer.getEmail(),
                    lecturer.getPassword()
            };

            String[][] updated = new String[rows.length + 1][];
            System.arraycopy(rows, 0, updated, 0, rows.length);
            updated[rows.length] = newRow;

            csvUtil.writeCSV(lecturersFile, updated);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving lecturer: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a lecturer by ID.
     * @param id lecturer ID
     * @return true if deleted
     */
    public boolean delete(String id) {
        try {
            String[][] rows = csvUtil.readCSV(lecturersFile);

            String[][] updated = new String[rows.length - 1][];
            int index = 0;
            boolean found = false;

            for (int i = 0; i < rows.length; i++) {
                if (i == 0) {
                    updated[index++] = rows[i];
                    continue;
                }
                if (!rows[i][0].equalsIgnoreCase(id)) {
                    updated[index++] = rows[i];
                } else {
                    found = true;
                }
            }

            if (!found) {
                System.err.println("Lecturer not found: " + id);
                return false;
            }

            csvUtil.writeCSV(lecturersFile, updated);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting lecturer: " + e.getMessage());
            return false;
        }
    }
}
