package repository;

import model.*;
import util.CSVUtil;
import java.util.Arrays;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CSVTimetableRepository implements TimetableRepository {
    private final String sessionsFile;
    private final CSVUtil csvUtil = new CSVUtil();
    private final LecturerRepository lecturerRepository = new CSVLecturerRepository("data/lecturers.csv");

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

    @Override
    public boolean deleteSlot(TimetableSlot slot) {
        try {
            String[][] rows = csvUtil.readCSV(sessionsFile);

            List<String[]> updated = new ArrayList<>();
            boolean found = false;

            for (String[] row : rows) {
                boolean same =
                        row[0].equals(slot.getDay()) &&
                                row[1].equals(slot.getStartTime().toString()) &&
                                row[2].equals(slot.getEndTime().toString());

                if (!same) {
                    updated.add(row);
                } else {
                    found = true;
                }
            }

            if (!found) {
                System.err.println("Slot not found, nothing deleted.");
                return false;
            }

            csvUtil.writeCSV(sessionsFile, updated.toArray(new String[0][]));
            return true;

        } catch (Exception e) {
            System.err.println("Error deleting timetable slot: " + e.getMessage());
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
            Room room = new Room(roomNumber, roomType, roomCapacity);

            // Lecturer (minimal info for now)
            String lecturerId = row[8];
            Lecturer lecturer = lecturerRepository.findById(lecturerId);

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

    public boolean overwriteAllSlots(Timetable timetable) {
        try {
            TimetableSlot[] slots = timetable.getSlots().toArray(new TimetableSlot[0]);
            String[][] data = new String[slots.length + 1][];

            // optional: add header as first row
            data[0] = new String[]{
                    "Day", "StartTime", "EndTime", "Semester", "Weeks",
                    "RoomNumber", "RoomType", "RoomCapacity",
                    "LecturerId", "StudentGroupId", "SubgroupId",
                    "ModuleCode", "ModuleTitle", "LectureHours", "LabHours", "TutorialHours",
                    "SessionType", "ProgrammeCode"
            };

            for (int i = 0; i < slots.length; i++) {
                TimetableSlot slot = slots[i];
                data[i + 1] = new String[]{
                        slot.getDay(),
                        slot.getStartTime().toString(),
                        slot.getEndTime().toString(),
                        String.valueOf(slot.getSemester()),
                        slot.getWeeks(),
                        slot.getRoom().getNumber(),
                        slot.getRoom().getType(),
                        String.valueOf(slot.getRoom().getCapacity()),
                        slot.getLecturer().getId(),
                        slot.getStudentGroupId(),
                        slot.getSubgroup() != null ? slot.getSubgroup().getId() : "",
                        slot.getModule().getCode(),
                        slot.getModule().getTitle(),
                        String.valueOf(slot.getModule().getLectureHours()),
                        String.valueOf(slot.getModule().getLabHours()),
                        String.valueOf(slot.getModule().getTutorialHours()),
                        slot.getType().name(),
                        slot.getModule().getProgrammeCode() != null ? slot.getModule().getProgrammeCode() : ""
                };
            }

            csvUtil.writeCSV(sessionsFile, data);
            return true;
        } catch (Exception e) {
            System.err.println("Error overwriting timetable CSV: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateSlot(TimetableSlot slot, int fieldChoice, String newValue) {
        try {
            Timetable timetable = findAll();
            boolean updated = false;

            for (TimetableSlot s : timetable.getSlots()) {
                if (s.equals(slot)) {
                    switch (fieldChoice) {
                        case 1 -> s.setDay(newValue);
                        case 2 -> s.setStartTime(LocalTime.parse(newValue));
                        case 3 -> s.setEndTime(LocalTime.parse(newValue));
                        case 4 -> s.getRoom().setNumber(newValue);
                        case 5 -> s.getLecturer().setId(newValue);
                        case 6 -> s.getStudentGroup().setId(newValue);
                        case 7 -> s.setType(SessionType.valueOf(newValue.toUpperCase()));
                        default -> { return false; }
                    }
                    updated = true;
                    break;
                }
            }

            if (updated) {
                overwriteAllSlots(timetable);
            }
            return updated;
        } catch (Exception e) {
            System.err.println("Error updating slot: " + e.getMessage());
            return false;
        }
    }



}
