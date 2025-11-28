package repository;

import model.*;
import util.CSVUtil;
import java.util.Arrays;

public class CSVTimetableRepository implements TimetableRepository{
    private final String sessionsFile;
    private final CSVUtil csvUtil = new CSVUtil();

    @Override
    public Timetable findAll() {
        Timetable timetable = new Timetable();
        String[][] rows = csvUtil.readCSV(sessionsFile);

        if (rows.length == 0) {
            return timetable;
        }

        for (int i = 1; i < rows.length; i++) {
            String[] row = rows[i];
            TimetableSlot slot = buildSlotFromRow(row);
            timetable.addSlot(slot);
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
                    slot.getLecturer().getUsername(),
                    slot.getLecturer().getEmail(),
                    slot.getLecturer().getPassword(),
                    slot.getStudentGroup().getId(),
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

}