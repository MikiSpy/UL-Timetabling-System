package repository;

import model.Lecturer;
import util.CSVUtil;

public class CSVLecturerRepository implements LecturerRepository{

    private final String file;
    private final CSVUtil csvUtil = new CSVUtil();

    public CSVLecturerRepository(String file) {
        this.file = file;
    }

    @Override
    public Lecturer findById(String id){
        try {
            String[][] rows = csvUtil.readCSV(file);

            for (int i = 1; i < rows.length; i++) { // skip header
                String[] row = rows[i];
                if (row[0].equals(id)) {
                    return new Lecturer(
                            row[0], // id
                            row[1], // name
                            row[2], // email
                            row[3] // password
                    );
                }
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error finding lecturer: " + e.getMessage());
            return null;
        }
    }
}
