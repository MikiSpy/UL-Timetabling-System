package repository;

import model.*;
import util.CSVUtil;

import java.util.Arrays;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository implementation for managing timetable slots stored in a CSV file.
 * Provides methods to load, save, update, and delete timetable slots.
 */
public class CSVTimetableRepository implements TimetableRepository {
    private final String sessionsFile;
    private final CSVUtil csvUtil = new CSVUtil();
    private final LecturerRepository lecturerRepository = new CSVLecturerRepository("data/lecturers.csv");

    /**
     * Constructs a repository for timetable slots using the given CSV file.
     *
     * @param sessionsFile path to the CSV file containing timetable slots
     */
    public CSVTimetableRepository(String sessionsFile) {
        this.sessionsFile = sessionsFile;
    }

    /**
     * Loads all timetable slots from the CSV file.
     *
     * @return a Timetable object containing all slots
     */
    @Override
    public Timetable findAll() {
        Timetable timetable = new Timetable();
        String[][] rows = csvUtil.readCSV(sessionsFile);

        if (rows.length == 0) return timetable;

        // skip header
        for (int i = 1; i < rows.length; i++) {
            TimetableSlot slot = buildSlotFromRow(rows[i]);
            if (slot != null) timetable.addSlot(slot);
        }

        return timetable;
    }

    /**
     * Saves a new timetable slot to the CSV file.
     *
     * @param slot the timetable slot to save
     * @return true if saved successfully, false otherwise
     */
    @Override
    public boolean saveSlot(TimetableSlot slot) {
        try {
            String[][] rows = csvUtil.readCSV(sessionsFile);

            String[] newRow = {
                    slot.getDay(),
                    slot.getStartTime().toString(),
                    slot.getEndTime().toString(),
                    String.valueOf(slot.getSemester()),
                    slot.getWeeks(),
                    slot.getRoom().getNumber(),
                    slot.getRoom().getType(),
                    String.valueOf(slot.getRoom().getCapacity()),
                    slot.getLecturer() != null ? slot.getLecturer().getId() : "",
                    slot.getStudentGroupId() != null ? slot.getStudentGroupId() : "",
                    slot.getSubgroup() != null ? slot.getSubgroup().getId() : "",
                    slot.getModule().getCode(),
                    slot.getModule().getTitle(),
                    String.valueOf(slot.getModule().getLectureHours()),
                    String.valueOf(slot.getModule().getLabHours()),
                    String.valueOf(slot.getModule().getTutorialHours()),
                    slot.getType().name(),
                    slot.getModule().getProgrammeCode() != null ? slot.getModule().getProgrammeCode() : ""
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

    /**
     * Deletes a timetable slot from the CSV file.
     *
     * @param slot the timetable slot to delete
     * @return true if deleted successfully, false otherwise
     */
    @Override
    public boolean deleteSlot(TimetableSlot slot) {
        try {
            String[][] rows = csvUtil.readCSV(sessionsFile);

            List<String[]> updated = new ArrayList<>();
            boolean found = false;

            for (String[] row : rows) {
                // keep header
                if (isHeader(row)) {
                    updated.add(row);
                    continue;
                }

                boolean same =
                        row[0].equals(slot.getDay()) &&
                                row[1].equals(slot.getStartTime().toString()) &&
                                row[2].equals(slot.getEndTime().toString()) &&
                                row[11].equals(slot.getModule().getCode());

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

    /**
     * Checks if a row is the header row.
     *
     * @param row the row to check
     * @return true if header, false otherwise
     */
    private boolean isHeader(String[] row) {
        return row.length > 0 && "Day".equalsIgnoreCase(row[0]);
    }

    /**
     * Safely parses an integer from a string.
     *
     * @param value the string value
     * @return parsed integer, or 0 if invalid
     */
    private int safeParseInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Finds a lecturer by ID, returning a placeholder if not found.
     *
     * @param lecturerId the lecturer ID
     * @return Lecturer object
     */
    private Lecturer safeFindLecturer(String lecturerId) {
        try {
            Lecturer l = lecturerRepository.findById(lecturerId);
            if (l != null) return l;
        } catch (Exception e) {
            System.err.println("Warning: lecturer lookup failed for '" + lecturerId + "': " + e.getMessage());
        }
        return new Lecturer(
                lecturerId,
                "Unknown Lecturer",
                lecturerId + "@example.com",
                "default"
        );
    }

    /**
     * Builds a timetable slot from a CSV row.
     *
     * @param row the CSV row
     * @return TimetableSlot object, or null if parsing fails
     */
    private TimetableSlot buildSlotFromRow(String[] row) {
        try {
            String day = row[0];
            LocalTime startTime = LocalTime.parse(row[1]);
            LocalTime endTime = LocalTime.parse(row[2]);
            int semester = Integer.parseInt(row[3]);
            String weeks = row[4];

            Room room = new Room(row[5], row[6], safeParseInt(row[7]));
            Lecturer lecturer = safeFindLecturer(row[8]);
            String studentGroupId = row[9];

            Subgroup subgroup = null;
            if (row.length > 10 && !row[10].isEmpty()) {
                subgroup = new Subgroup(row[10], null);
            }

            String moduleCode = row[11];
            String moduleTitle = row[12];
            int lectureHours = safeParseInt(row[13]);
            int labHours = safeParseInt(row[14]);
            int tutorialHours = safeParseInt(row[15]);
            SessionType type = SessionType.valueOf(row[16].toUpperCase());
            String programmeCode = row.length > 17 ? row[17] : "";

            model.Module module = new model.Module(moduleCode, moduleTitle, lectureHours, labHours, tutorialHours,
                    1, semester, weeks, programmeCode);

            return new TimetableSlot(day, startTime, endTime, semester, weeks,
                    room, lecturer, studentGroupId, subgroup, module, type);

        } catch (Exception e) {
            System.err.println("Error parsing CSV row: " + Arrays.toString(row));
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Overwrites the CSV file with all slots from the given timetable.
     *
     * @param timetable the timetable containing slots
     * @return true if successful, false otherwise
     */
    public boolean overwriteAllSlots(Timetable timetable) {
        try {
            TimetableSlot[] slots = timetable.getSlots().toArray(new TimetableSlot[0]);
            String[][] data = new String[slots.length + 1][];

            // header row
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
                        slot.getLecturer() != null ? slot.getLecturer().getId() : "",
                        slot.getStudentGroupId() != null ? slot.getStudentGroupId() : "",
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

    /**
     * Updates a specific field of a timetable slot and rewrites the CSV file.
     *
     * @param slot        the timetable slot to update
     * @param fieldChoice the field number to update (1=Day, 2=StartTime, etc.)
     * @param newValue    the new value for the field
     * @return true if updated successfully, false otherwise
     */
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
                        case 6 -> s.setStudentGroupId(newValue);
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
