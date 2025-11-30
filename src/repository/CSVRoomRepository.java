package repository;

import model.Room;
import util.CSVUtil;

import java.util.ArrayList;
import java.util.List;

public class CSVRoomRepository implements RoomRepository {
    private final String file;
    private final CSVUtil csvUtil = new CSVUtil();

    public CSVRoomRepository(String file) {
        this.file = file;
    }

    @Override
    public boolean save(Room room) {
        try {
            String[][] rows = csvUtil.readCSV(file);

            // Build new row
            String[] newRow = {
                    room.getNumber(),
                    room.getType(),
                    String.valueOf(room.getCapacity())
            };

            // Append
            String[][] updated = new String[rows.length + 1][];
            System.arraycopy(rows, 0, updated, 0, rows.length);
            updated[rows.length] = newRow;

            csvUtil.writeCSV(file, updated);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving room: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String number) {
        try {
            String[][] rows = csvUtil.readCSV(file);
            List<String[]> updated = new ArrayList<>();
            boolean deleted = false;

            for (String[] row : rows) {
                if (row[0].equals(number)) {
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
            System.err.println("Error deleting room: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Room findByNumber(String number) {
        try {
            String[][] rows = csvUtil.readCSV(file);

            for (int i = 1; i < rows.length; i++) { // skip header
                String[] row = rows[i];
                if (row[0].equals(number)) {
                    return new Room(
                            row[0], // number
                            row[1], // type
                            Integer.parseInt(row[2]) // capacity
                    );
                }
            }
            return null;
        } catch (Exception e) {
            System.err.println("Error finding room: " + e.getMessage());
            return null;
        }
    }
}
