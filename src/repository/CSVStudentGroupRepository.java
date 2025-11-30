package repository;

import model.StudentGroup;
import util.CSVUtil;

import java.util.ArrayList;
import java.util.List;

public class CSVStudentGroupRepository implements StudentGroupRepository {
    private final String file;
    private final CSVUtil csvUtil = new CSVUtil();

    public CSVStudentGroupRepository(String file) {
        this.file = file;
    }

    @Override
    public boolean save(StudentGroup group) {
        try {
            String[][] rows = csvUtil.readCSV(file);

            // Build new row
            String[] newRow = {group.getId()};

            // Append
            String[][] updated = new String[rows.length + 1][];
            System.arraycopy(rows, 0, updated, 0, rows.length);
            updated[rows.length] = newRow;

            csvUtil.writeCSV(file, updated);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving student group: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            String[][] rows = csvUtil.readCSV(file);
            List<String[]> updated = new ArrayList<>();
            boolean deleted = false;

            for (String[] row : rows) {
                if (row[0].equals(id)) {
                    deleted = true; // skip this row
                } else {
                    updated.add(row);
                }
            }

            if (deleted) {
                csvUtil.writeCSV(file, updated.toArray(new String[0][]));
            }
            return deleted;
        } catch (Exception e) {
            System.err.println("Error deleting student group: " + e.getMessage());
            return false;
        }
    }

    @Override
    public StudentGroup findByCode(String id) {
        try {
            String[][] rows = csvUtil.readCSV(file);

            for (int i = 1; i < rows.length; i++) {
                String[] row = rows[i];
                if (row[0].equals(id)) {
                    return new StudentGroup(row[0]);
                }
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error finding student group: " + e.getMessage());
            return null;
        }
    }
}
