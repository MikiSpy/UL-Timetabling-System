package repository;

import model.*;
import util.CSVUtil;
import java.util.Arrays;
import java.time.LocalTime;

public class CSVTimetableRepository implements TimetableRepository {
    private final String sessionsFile;
    private final CSVUtil csvUtil = new CSVUtil();

    public CSVTimetableRepository(String sessionsFile) {
        this.sessionsFile = sessionsFile;
    }

    @Override
    public Timetable findAll() {
        Timetable timetable = new Timetable();
        String[][] rows = csvUtil.readCSV(sessionsFile);

        if (rows.length == 0) return timetable;

        for (int i = 1; i < rows.length; i++) {
            TimetableSlot slot = buildSlotFromRow(rows[i]);
            if (slot != null) timetable.addSlot(slot);
        }

        return timetable;
    }

    @Override
    public boolean saveSlot(TimetableSlot slot) {
        try {
            String[][] rows = csvUtil.readCSV(sessionsFile);

            String[] newRow = {
                    slot.getDay(),
                    String.valueOf(slot.getStartTime()),
                    String.valueOf(slot.getEndTime()),
                    String.valueOf(slot.getSemester()),
                    slot.getWeeks(),
                    slot.getRoom().getNumber(),
                    slot.getRoom().getType(),
                    String.valueOf(slot.getRoom().getCapacity()),
                    slot.getLecturer().getId(),
                    "", // subgroup optional
                    "", // not storing subgroup ID for simplicity
                    slot.getModule().getCode(),
                    slot.getModule().getTitle(),
                    String.valueOf(slot.getModule().getLectureHours()),
                    String.valueOf(slot.getModule().getLabHours()),
                    String.valueOf(slot.getModule().getTutorialHours()),
                    slot.getType().name()
            };

            String[][] updated = Arrays.copyOf(rows, rows.length + 1);
            updated[rows.length] = newRow;

            csvUtil.writeCSV(sessionsFile, updated);
            return true;
        } catch (Exception e) {
            System.err.println("Error saving timetable slot: " + e.getMessage());
            return false;
        }
    }

    private TimetableSlot buildSlotFromRow(String[] row) {
        try {
            // Basic info
            String day = row[0];
            LocalTime startTime = LocalTime.parse(row[1]);
            LocalTime endTime = LocalTime.parse(row[2]);
            int semester = Integer.parseInt(row[3]);
            String weeks = row[4];

            // Room
            String roomNumber = row[5];
            String roomType = row[6];
            int roomCapacity = Integer.parseInt(row[7]);
            Room room = new Room(roomType, roomNumber, roomCapacity);

            // Lecturer (minimal info for now)
            String lecturerId = row[8];
            Lecturer lecturer = new Lecturer("TBD", "tbd", row[8], "tbd@example.com");

            // Student group ID (store ID only)
            String studentGroupId = row[9];

            // Subgroup (optional)
            Subgroup subgroup = null;
            if (row.length > 10 && !row[10].isEmpty()) {
                subgroup = new Subgroup(row[10], null); // You can attach a StudentGroup object if you want
            }

            // Module
            String moduleCode = row[11];
            String moduleTitle = row[12];
            int lectureHours = Integer.parseInt(row[13]);
            int labHours = Integer.parseInt(row[14]);
            int tutorialHours = Integer.parseInt(row[15]);
            String programmeCode = row.length > 17 ? row[17] : "";
            model.Module module = new model.Module(moduleCode, moduleTitle, lectureHours, labHours, tutorialHours,
                    1, semester, weeks, programmeCode);

            // Session type
            SessionType type = SessionType.valueOf(row[16].toUpperCase());

            // Build the slot using the studentGroupId string
            return new TimetableSlot(day, startTime, endTime, semester, weeks,
                    room, lecturer, studentGroupId, subgroup, module, type);

        } catch (Exception e) {
            System.err.println("Error parsing CSV row: " + Arrays.toString(row));
            e.printStackTrace();
            return null;
        }
    }

}
